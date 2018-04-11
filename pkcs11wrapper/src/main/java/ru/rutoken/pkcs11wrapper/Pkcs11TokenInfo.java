/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sun.jna.NativeLong;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.ParseException;
import java.util.Date;

import ru.rutoken.pkcs11jna.CK_TOKEN_INFO;
import ru.rutoken.pkcs11jna.Pkcs11;
import ru.rutoken.pkcs11jna.Pkcs11Constants;

public class Pkcs11TokenInfo {
    public Pkcs11TokenInfo(@NonNull CK_TOKEN_INFO tokenInfo) throws ParseException {
        mLabel = new String(tokenInfo.label);
        mManufacturerID = new String(tokenInfo.manufacturerID);
        mModel = new String(tokenInfo.model);
        mSerialNumber = new String(tokenInfo.serialNumber);
        mMaxSessionCount = tokenInfo.ulMaxSessionCount.longValue();
        mSessionCount = tokenInfo.ulSessionCount.longValue();
        mMaxRwSessionCount = tokenInfo.ulMaxRwSessionCount.longValue();
        mRwSessionCount = tokenInfo.ulRwSessionCount.longValue();
        mMaxPinLen = tokenInfo.ulMaxPinLen.longValue();
        mMinPinLen = tokenInfo.ulMinPinLen.longValue();
        mTotalPublicMemory = tokenInfo.ulTotalPublicMemory.longValue();
        mFreePublicMemory = tokenInfo.ulFreePublicMemory.longValue();
        mTotalPrivateMemory = tokenInfo.ulTotalPrivateMemory.longValue();
        mFreePrivateMemory = tokenInfo.ulFreePrivateMemory.longValue();
        mHardwareVersion = new Pkcs11Version(tokenInfo.hardwareVersion);
        mFirmwareVersion = new Pkcs11Version(tokenInfo.firmwareVersion);
        mFlags = tokenInfo.flags.longValue();
        if (isClockOnToken() && null != tokenInfo.utcTime) {
            try {
                mTime = Pkcs11Utility.parseTime(tokenInfo.utcTime);
            } catch (ParseException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    @NonNull
    public String getLabel() {
        return mLabel;
    }

    @NonNull
    public String getManufacturerID() {
        return mManufacturerID;
    }

    @NonNull
    public String getModel() {
        return mModel;
    }

    @NonNull
    public String getSerialNumber() {
        return mSerialNumber;
    }

    public long getMaxSessionCount() {
        return mMaxSessionCount;
    }

    public long getSessionCount() {
        return mSessionCount;
    }

    public long getMaxRwSessionCount() {
        return mMaxRwSessionCount;
    }

    public long getRwSessionCount() {
        return mRwSessionCount;
    }

    public long getMaxPinLen() {
        return mMaxPinLen;
    }

    public long getMinPinLen() {
        return mMinPinLen;
    }

    public long getTotalPublicMemory() {
        return mTotalPublicMemory;
    }

    public long getFreePublicMemory() {
        return mFreePublicMemory;
    }

    public long getTotalPrivateMemory() {
        return mTotalPrivateMemory;
    }

    public long getFreePrivateMemory() {
        return mFreePrivateMemory;
    }

    @NonNull
    public Pkcs11Version getHardwareVersion() {
        return mHardwareVersion;
    }

    @NonNull
    public Pkcs11Version getFirmwareVersion() {
        return mFirmwareVersion;
    }

    @Nullable
    public Date getTime() {
        return mTime;
    }

    public boolean isRng() {
        return checkFlag(Pkcs11Constants.CKF_RNG);
    }

    public boolean isWriteProtected() {
        return checkFlag(Pkcs11Constants.CKF_WRITE_PROTECTED);
    }

    public boolean isLoginRequired() {
        return checkFlag(Pkcs11Constants.CKF_LOGIN_REQUIRED);
    }

    public boolean isUserPinInitialized() {
        return checkFlag(Pkcs11Constants.CKF_USER_PIN_INITIALIZED);
    }

    public boolean isRestoreKeyNotNeeded() {
        return checkFlag(Pkcs11Constants.CKF_RESTORE_KEY_NOT_NEEDED);
    }

    public boolean isClockOnToken() {
        return checkFlag(Pkcs11Constants.CKF_CLOCK_ON_TOKEN);
    }

    public boolean isProtectedAuthenticationPath() {
        return checkFlag(Pkcs11Constants.CKF_PROTECTED_AUTHENTICATION_PATH);
    }

    public boolean isDualCryptoOperations() {
        return checkFlag(Pkcs11Constants.CKF_DUAL_CRYPTO_OPERATIONS);
    }

    public boolean isTokenInitialized() {
        return checkFlag(Pkcs11Constants.CKF_TOKEN_INITIALIZED);
    }

    public boolean isSecondaryAuthentication() {
        return checkFlag(Pkcs11Constants.CKF_SECONDARY_AUTHENTICATION);
    }

    public boolean isUserPinCountLow() {
        return checkFlag(Pkcs11Constants.CKF_USER_PIN_COUNT_LOW);
    }

    public boolean isUserPinFinalTry() {
        return checkFlag(Pkcs11Constants.CKF_USER_PIN_FINAL_TRY);
    }

    public boolean isUserPinLocked() {
        return checkFlag(Pkcs11Constants.CKF_USER_PIN_LOCKED);
    }

    public boolean isUserPinToBeChanged() {
        return checkFlag(Pkcs11Constants.CKF_USER_PIN_TO_BE_CHANGED);
    }

    public boolean isSoPinCountLow() {
        return checkFlag(Pkcs11Constants.CKF_SO_PIN_COUNT_LOW);
    }

    public boolean isSoPinFinalTry() {
        return checkFlag(Pkcs11Constants.CKF_SO_PIN_FINAL_TRY);
    }

    public boolean isSoPinLocked() {
        return checkFlag(Pkcs11Constants.CKF_SO_PIN_LOCKED);
    }

    public boolean isSoPinToBeChanged() {
        return checkFlag(Pkcs11Constants.CKF_SO_PIN_TO_BE_CHANGED);
    }

    public boolean isErrorState() {
        //TODO add flag CKF_ERROR_STATE
        return false;//checkFlag(Pkcs11Constants.CKF_ERROR_STATE);
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

    private final String mLabel;
    private final String mManufacturerID;
    private final String mModel;
    private final String mSerialNumber;
    private final long mMaxSessionCount;
    private final long mSessionCount;
    private final long mMaxRwSessionCount;
    private final long mRwSessionCount;
    private final long mMaxPinLen;
    private final long mMinPinLen;
    private final long mTotalPublicMemory;
    private final long mFreePublicMemory;
    private final long mTotalPrivateMemory;
    private final long mFreePrivateMemory;
    private final Pkcs11Version mHardwareVersion;
    private final Pkcs11Version mFirmwareVersion;
    @Nullable
    private Date mTime;
    private final long mFlags;
}
