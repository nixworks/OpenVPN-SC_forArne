/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.session;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.pkcs11.tokenmanager.Pkcs11TokenManager;
import ru.rutoken.pkcs11.exception.TokenException;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;

/* This singleton is instantiated in OpenVPNService (in it's process); it helps to open
 * first session on token before we actually need to sign data (for bluetooth tokens
 * first session sets SM channel with token, which saves from 3 to 4 seconds on the actual
 * connection)
 */
public class SessionWrapperManager implements Pkcs11TokenManager.TokenListener {

    public static SessionWrapperManager getInstance() {
        if (null == sInstance.get()) {
            synchronized (SessionWrapperManager.class) {
                if (null == sInstance.get()) {
                    sInstance.set(new SessionWrapperManager());
                }
            }
        }
        return sInstance.get();
    }

    public void prepareSessionWrapper(String tokenSerial, SessionWrapper.Builder builder)
                throws Pkcs11Exception, TokenException {
        SessionWrapper sw = builder.build();
        if (hasSessionWrapper(tokenSerial))
            getSessionWrapper(tokenSerial).close();
        mSessionWrappers.put(tokenSerial, sw);
    }

    public boolean hasSessionWrapper(String tokenSerial) {
        return mSessionWrappers.containsKey(tokenSerial);
    }

    public SessionWrapper getSessionWrapper(String tokenSerial) throws TokenException {
        SessionWrapper sw = mSessionWrappers.get(tokenSerial);
        if (sw == null)
            throw new TokenException("No session in Session Manager for token serial: " + tokenSerial);
        return sw;
    }

    @WorkerThread
    @Override
    public void onTokenAdded(@NonNull Pkcs11Token token) {}

    @WorkerThread
    @Override
    public void onTokenRemoved(@NonNull Pkcs11Token token) {
        List<String> forRemoval = new ArrayList<>();
        synchronized (mSessionWrappers) {
            for (Map.Entry<String, SessionWrapper> entry : mSessionWrappers.entrySet()) {
                if (entry.getValue().getSession().getToken().equals(token))
                    forRemoval.add(entry.getKey());
            }
            for (String serial: forRemoval) {
                mSessionWrappers.remove(serial);
            }
        }
    }

    private static final AtomicReference<SessionWrapperManager> sInstance = new AtomicReference<>();
    private final Map<String /*token serial*/, SessionWrapper> mSessionWrappers = Collections.synchronizedMap(new HashMap<>());
}
