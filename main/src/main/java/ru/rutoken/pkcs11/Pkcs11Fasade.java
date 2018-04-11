/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.pkcs11.slotevent.Pkcs11SlotEvent;
import ru.rutoken.pkcs11.slotevent.Pkcs11SlotEventProducerThread;
import ru.rutoken.pkcs11.slotevent.Pkcs11SlotEventProviderThread;
import ru.rutoken.pkcs11.tokenmanager.Pkcs11TokenManager;
import ru.rutoken.pkcs11wrapper.Pkcs11DefaultInitializeParameters;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;
import ru.rutoken.utility.keyexecutor.KeyThreadPool;

public class Pkcs11Fasade {
    public static Pkcs11Fasade getInstance() throws ExceptionInInitializerError {
        if (null == sInstance.get()) {
            synchronized (Pkcs11Fasade.class) {
                if (null == sInstance.get()) {
                    sInstance.set(new Pkcs11Fasade());
                }
            }
        }
        return sInstance.get();
    }

    private void initialize() throws Pkcs11Exception {
        if (!mInitialized) {
            Pkcs11DefaultInitializeParameters parameters = new Pkcs11DefaultInitializeParameters();
            // use internal library synchronization
            parameters.setOsLockingOk(true);
            try {
                mModule.initialize(parameters);

                addSlotEventListener(mTokenManager);

                mTokenManager.initialize(mModule);

                mEventProducerThread.start();
                mEventProviderThread.start();

                mInitialized = true;
            } catch (Pkcs11Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @NonNull
    public Pkcs11TokenManager getTokenManager() {
        return mTokenManager;
    }

    @NonNull
    public KeyThreadPool<Pkcs11Token> getTokenThreadPool() {
        return mTokenThreadPool;
    }

    public void addSlotEventListener(@NonNull Pkcs11SlotEventProviderThread.SlotEventListener listener) {
        mEventProviderThread.addSlotEventListener(listener);
    }

    private Pkcs11Fasade() throws ExceptionInInitializerError {
        try {
            initialize();
        } catch (Pkcs11Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(getClass().getName() + " initialization failed");
        }
    }

    private static final AtomicReference<Pkcs11Fasade> sInstance = new AtomicReference<>();
    private final RtPkcs11Module mModule = new RtPkcs11Module();
    private final BlockingQueue<Pkcs11SlotEvent> mQueue = new LinkedBlockingQueue<>();
    private final Pkcs11SlotEventProducerThread mEventProducerThread = new Pkcs11SlotEventProducerThread(mModule, mQueue);
    private final Pkcs11SlotEventProviderThread mEventProviderThread = new Pkcs11SlotEventProviderThread(mQueue);
    private boolean mInitialized = false;
    private final KeyThreadPool<Pkcs11Token> mTokenThreadPool = new KeyThreadPool<>();
    private final Pkcs11TokenManager mTokenManager = new Pkcs11TokenManager();
}
