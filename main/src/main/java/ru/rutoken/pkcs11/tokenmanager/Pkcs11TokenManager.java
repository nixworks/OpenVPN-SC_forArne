/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.tokenmanager;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.pkcs11.exception.TokenException;
import ru.rutoken.pkcs11.slotevent.Pkcs11SlotEvent;
import ru.rutoken.pkcs11.slotevent.Pkcs11SlotEventProviderThread;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Module;
import ru.rutoken.pkcs11wrapper.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;

/**
 * Provides access and notifications for tokens
 */
public class Pkcs11TokenManager implements Pkcs11SlotEventProviderThread.SlotEventListener {
    public interface TokenListener {
        default void initializeTokens(@NonNull List<Pkcs11Token> tokens) {
            for (Pkcs11Token token : tokens) {
                onTokenAdded(token);
            }
        }

        void onTokenAdded(@NonNull Pkcs11Token token);

        void onTokenRemoved(@NonNull Pkcs11Token token);
    }

    public void initialize(@NonNull Pkcs11Module module) throws Pkcs11Exception {
        final List<Pkcs11Slot> slots = module.getSlotList(true);
        for (Pkcs11Slot slot : slots) {
            addSlot(slot);
        }
    }

    @WorkerThread
    @Override
    public void onPkcs11SlotEvent(@NonNull Pkcs11SlotEvent event) {
        if (event.getEventSlotInfo().isTokenPresent()) {
            addSlot(event.getSlot());
        } else {
            removeSlot(event.getSlot());
        }
    }

    public void addTokenListener(@NonNull TokenListener listener) {
        synchronized (mTokenListeners) {
            if (!mTokenListeners.contains(listener)) {
                synchronized (mTokens) {
                    mTokenListeners.add(listener);
                    listener.initializeTokens(getTokenList());
                }
            }
        }
    }

    @NonNull
    public Pkcs11Token getToken(long slotId) throws TokenException {
        Pkcs11Token token = mTokens.get(slotId);
        if (null == token)
            throw new TokenException("Cannot get token");
        return token;
    }

    @NonNull
    public List<Pkcs11Token> getTokenList() {
        synchronized (mTokens) {
            return new ArrayList<>(mTokens.values());
        }
    }

    private void addSlot(@NonNull Pkcs11Slot slot) {
        final Pkcs11Token token = slot.getToken();
        mTokens.put(token.getSlotId(), token);
        synchronized (mTokenListeners) {
            for (TokenListener listener : mTokenListeners) {
                listener.onTokenAdded(token);
            }
        }
    }

    private void removeSlot(@NonNull Pkcs11Slot slot) {
        final Pkcs11Token token = slot.getToken();
        mTokens.remove(token.getSlotId());
        synchronized (mTokenListeners) {
            for (TokenListener listener : mTokenListeners) {
                listener.onTokenRemoved(token);
            }
        }
    }

    private final Map<Long/*slot id*/, Pkcs11Token> mTokens = Collections.synchronizedMap(new HashMap<>());
    private final List<TokenListener> mTokenListeners = new ArrayList<>();
}
