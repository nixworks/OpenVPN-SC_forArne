/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;

import org.apache.commons.lang3.builder.ToStringBuilder;

import ru.rutoken.pkcs11jna.CK_SESSION_INFO;
import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11SessionState;

public class Pkcs11SessionInfo {
    public Pkcs11SessionInfo(@NonNull CK_SESSION_INFO sessionInfo) {
        mSlotId = sessionInfo.slotID.longValue();
        mState = Pkcs11SessionState.fromValue(sessionInfo.state.longValue());
        mDeviceError = sessionInfo.ulDeviceError.longValue();
        mFlags = sessionInfo.flags.longValue();
    }

    public long getSlotId() {
        return mSlotId;
    }

    @NonNull
    public Pkcs11SessionState getState() {
        return mState;
    }

    public long getDeviceError() {
        return mDeviceError;
    }

    public boolean isRwSession() {
        return checkFlag(Pkcs11Constants.CKF_RW_SESSION);
    }

    public boolean isSerialSession() {
        return checkFlag(Pkcs11Constants.CKF_SERIAL_SESSION);
    }

    public long getFlags() {
        return mFlags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private boolean checkFlag(NativeLong flag) {
        return (mFlags & flag.longValue()) != 0L;
    }

    private final long mSlotId;
    private final Pkcs11SessionState mState;
    private final long mDeviceError;
    private final long mFlags;
}
