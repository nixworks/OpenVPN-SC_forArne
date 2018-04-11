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

import ru.rutoken.pkcs11jna.CK_MECHANISM_INFO;
import ru.rutoken.pkcs11jna.Pkcs11Constants;

public class Pkcs11MechanismInfo {
    public Pkcs11MechanismInfo(@NonNull CK_MECHANISM_INFO mechanismInfo) {
        mMinKeySize = mechanismInfo.ulMinKeySize.longValue();
        mMaxKeySize = mechanismInfo.ulMaxKeySize.longValue();
        mFlags = mechanismInfo.flags.longValue();
    }

    public long getMinKeySize() {
        return mMinKeySize;
    }

    public long getMaxKeySize() {
        return mMaxKeySize;
    }

    public boolean isHw() {
        return checkFlag(Pkcs11Constants.CKF_HW);
    }

    public boolean isEncrypt() {
        return checkFlag(Pkcs11Constants.CKF_ENCRYPT);
    }

    public boolean isDecrypt() {
        return checkFlag(Pkcs11Constants.CKF_DECRYPT);
    }

    public boolean isDigest() {
        return checkFlag(Pkcs11Constants.CKF_DIGEST);
    }

    public boolean isSign() {
        return checkFlag(Pkcs11Constants.CKF_SIGN);
    }

    public boolean isSignRecover() {
        return checkFlag(Pkcs11Constants.CKF_SIGN_RECOVER);
    }

    public boolean isVerify() {
        return checkFlag(Pkcs11Constants.CKF_VERIFY);
    }

    public boolean isVerifyRecover() {
        return checkFlag(Pkcs11Constants.CKF_VERIFY_RECOVER);
    }

    public boolean isGenerate() {
        return checkFlag(Pkcs11Constants.CKF_GENERATE);
    }

    public boolean isGenerateKeyPair() {
        return checkFlag(Pkcs11Constants.CKF_GENERATE_KEY_PAIR);
    }

    public boolean isWrap() {
        return checkFlag(Pkcs11Constants.CKF_WRAP);
    }

    public boolean isUnwrap() {
        return checkFlag(Pkcs11Constants.CKF_UNWRAP);
    }

    public boolean isDerive() {
        return checkFlag(Pkcs11Constants.CKF_DERIVE);
    }

    public boolean isExtension() {
        return checkFlag(Pkcs11Constants.CKF_EXTENSION);
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

    private final long mMinKeySize;
    private final long mMaxKeySize;
    private final long mFlags;
}
