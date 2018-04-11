/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.Nullable;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public class Pkcs11DefaultInitializeParameters implements Pkcs11InitializeParameters {
    public Pkcs11DefaultInitializeParameters() {
    }

    public Pkcs11DefaultInitializeParameters(@Nullable Pkcs11MutexHandler mutexHandler, long flags) {
        mMutexHandler = mutexHandler;
        mFlags = flags;
    }

    @Override
    public Pkcs11MutexHandler getMutexHandler() {
        return mMutexHandler;
    }

    @Override
    public boolean isLibraryCantCreateOsThreads() {
        return checkFlag(Pkcs11Constants.CKF_LIBRARY_CANT_CREATE_OS_THREADS);
    }

    @Override
    public boolean isOsLockingOk() {
        return checkFlag(Pkcs11Constants.CKF_OS_LOCKING_OK);
    }

    @Override
    public long getFlags() {
        return mFlags;
    }

    public void setMutexHandler(@Nullable Pkcs11MutexHandler mutexHandler) {
        mMutexHandler = mutexHandler;
    }

    public void setLibraryCantCreateOsThreads(boolean value) {
        setFlag(Pkcs11Constants.CKF_LIBRARY_CANT_CREATE_OS_THREADS, value);
    }

    public void setOsLockingOk(boolean value) {
        setFlag(Pkcs11Constants.CKF_OS_LOCKING_OK, value);
    }

    public void setFlags(long flags) {
        mFlags = flags;
    }

    private boolean checkFlag(NativeLong flag) {
        return (mFlags & flag.longValue()) != 0L;
    }

    private void setFlag(NativeLong flag, boolean value) {
        if (value) {
            mFlags |= flag.longValue();
        } else {
            mFlags &= ~flag.longValue();
        }
    }

    @Nullable
    private Pkcs11MutexHandler mMutexHandler;
    private long mFlags;
}
