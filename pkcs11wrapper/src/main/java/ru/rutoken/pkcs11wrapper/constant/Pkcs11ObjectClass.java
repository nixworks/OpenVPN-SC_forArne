/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11ObjectClass implements Supplier<Long> {
    CKO_DATA(Pkcs11Constants.CKO_DATA),
    CKO_CERTIFICATE(Pkcs11Constants.CKO_CERTIFICATE),
    CKO_PUBLIC_KEY(Pkcs11Constants.CKO_PUBLIC_KEY),
    CKO_PRIVATE_KEY(Pkcs11Constants.CKO_PRIVATE_KEY),
    CKO_SECRET_KEY(Pkcs11Constants.CKO_SECRET_KEY),
    CKO_HW_FEATURE(Pkcs11Constants.CKO_HW_FEATURE),
    CKO_DOMAIN_PARAMETERS(Pkcs11Constants.CKO_DOMAIN_PARAMETERS),
    CKO_MECHANISM(Pkcs11Constants.CKO_MECHANISM),
    CKO_VENDOR_DEFINED(Pkcs11Constants.CKO_VENDOR_DEFINED);

    public static Pkcs11ObjectClass fromValue(long value) {
        for (Pkcs11ObjectClass val : values()) {
            if (val.getValue() == value)
                return val;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Long get() { return getValue(); }

    public long getValue() {
        return mValue;
    }

    Pkcs11ObjectClass(NativeLong value) {
        mValue = value.longValue();
    }

    private final long mValue;
}
