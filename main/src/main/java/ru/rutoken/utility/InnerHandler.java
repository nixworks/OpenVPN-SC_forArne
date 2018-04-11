/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.utility;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Helper class to communicate with UI thread, protects from memory leaks
 * @param <Parent> Outer class
 */
public abstract class InnerHandler<Parent> extends Handler {
    public InnerHandler(@NonNull Parent parent) {
        mParent = new WeakReference<>(parent);
    }

    @Override
    public void handleMessage(Message msg) {
        Parent parent = mParent.get();
        if (null == parent)
            return;
        onHandleMessage(parent, msg);
    }

    protected abstract void onHandleMessage(@NonNull Parent parent, Message msg);

    private final WeakReference<Parent> mParent;// protect from memory leak
}
