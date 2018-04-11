/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11SessionState {
    CKS_RO_PUBLIC_SESSION(Pkcs11Constants.CKS_RO_PUBLIC_SESSION.longValue()),
    CKS_RO_USER_FUNCTIONS(Pkcs11Constants.CKS_RO_USER_FUNCTIONS.longValue()),
    CKS_RW_PUBLIC_SESSION(Pkcs11Constants.CKS_RW_PUBLIC_SESSION.longValue()),
    CKS_RW_USER_FUNCTIONS(Pkcs11Constants.CKS_RW_USER_FUNCTIONS.longValue()),
    CKS_RW_SO_FUNCTIONS(Pkcs11Constants.CKS_RW_SO_FUNCTIONS.longValue());

    public static Pkcs11SessionState fromValue(long value) {
        for (Pkcs11SessionState val : values()) {
            if (val.getValue() == value)
                return val;
        }
        throw new IllegalArgumentException();
    }

    public long getValue() {
        return mValue;
    }

    Pkcs11SessionState(long value) {
        mValue = value;
    }

    private final long mValue;
}
