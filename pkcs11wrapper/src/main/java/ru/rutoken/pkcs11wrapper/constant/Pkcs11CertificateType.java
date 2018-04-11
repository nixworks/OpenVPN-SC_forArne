/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11CertificateType implements Supplier<Long> {
    CKC_X_509(Pkcs11Constants.CKC_X_509),
    CKC_WTLS(Pkcs11Constants.CKC_WTLS),
    CKC_X_509_ATTR_CERT(Pkcs11Constants.CKC_X_509_ATTR_CERT),
    CKC_VENDOR_DEFINED(Pkcs11Constants.CKC_VENDOR_DEFINED);

    public static Pkcs11CertificateType fromValue(long value) {
        for (Pkcs11CertificateType val : values()) {
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

    Pkcs11CertificateType(NativeLong value) {
        mValue = value.longValue();
    }

    private final long mValue;
}
