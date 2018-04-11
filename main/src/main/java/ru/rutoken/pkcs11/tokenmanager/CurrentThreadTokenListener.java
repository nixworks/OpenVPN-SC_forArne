/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.tokenmanager;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.rutoken.pkcs11wrapper.Pkcs11Token;

/**
 * Listens for token manager events and transfers them to current instance thread.
 * It is useful to receive token events from Gui or any other thread.
 */
public class CurrentThreadTokenListener implements Pkcs11TokenManager.TokenListener {
    /**
     * @param listener object that will receive token events in current instance thread
     */
    public CurrentThreadTokenListener(@NonNull Pkcs11TokenManager.TokenListener listener) {
        mListener = listener;
    }

    @Override
    public void initializeTokens(@NonNull List<Pkcs11Token> tokens) {
        mListener.initializeTokens(tokens);
    }

    @WorkerThread
    @Override
    public void onTokenAdded(@NonNull Pkcs11Token token) {
        mEventHandler.sendMessage(mEventHandler.obtainMessage(TOKEN_ADDED, token));
    }

    @WorkerThread
    @Override
    public void onTokenRemoved(@NonNull Pkcs11Token token) {
        mEventHandler.sendMessage(mEventHandler.obtainMessage(TOKEN_REMOVED, token));
    }

    /**
     * Transfers notifications to current instance thread
     */
    private static class EventHandler extends Handler {
        EventHandler(@NonNull CurrentThreadTokenListener outer) {
            mOuter = new WeakReference<>(outer);
        }

        @Override
        public void handleMessage(Message msg) {
            CurrentThreadTokenListener outer = mOuter.get();
            if (null != outer) {
                if (TOKEN_ADDED == msg.what)
                    outer.mListener.onTokenAdded((Pkcs11Token) msg.obj);
                else if (TOKEN_REMOVED == msg.what)
                    outer.mListener.onTokenRemoved((Pkcs11Token) msg.obj);
                else
                    throw new IllegalArgumentException();
            }
        }

        private final WeakReference<CurrentThreadTokenListener> mOuter;// protect from memory leak
    }

    private static final int TOKEN_ADDED = 0;
    private static final int TOKEN_REMOVED = 1;
    private final EventHandler mEventHandler = new EventHandler(this);
    @NonNull
    private final Pkcs11TokenManager.TokenListener mListener;
}
