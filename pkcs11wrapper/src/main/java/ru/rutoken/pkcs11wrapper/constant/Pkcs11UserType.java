/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11UserType {
    CKU_SO(Pkcs11Constants.CKU_SO.longValue()),
    CKU_USER(Pkcs11Constants.CKU_USER.longValue()),
    CKU_CONTEXT_SPECIFIC(Pkcs11Constants.CKU_CONTEXT_SPECIFIC.longValue());

    Pkcs11UserType(long value) {
        mValue = value;
    }

    public static Pkcs11UserType fromValue(long value) {
        for (Pkcs11UserType val : values()) {
            if (val.getValue() == value)
                return val;
        }
        throw new IllegalArgumentException();
    }

    public long getValue() {
        return mValue;
    }

    private final long mValue;
}
