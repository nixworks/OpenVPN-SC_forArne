/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

public enum Pkcs11CertificateCategory implements Supplier<Long> {
    CK_CERTIFICATE_CATEGORY_UNSPECIFIED(0L),
    CK_CERTIFICATE_CATEGORY_TOKEN_USER(1L),
    CK_CERTIFICATE_CATEGORY_AUTHORITY(2L),
    CK_CERTIFICATE_CATEGORY_OTHER_ENTITY(3L);

    Pkcs11CertificateCategory(long value) {
        mValue = value;
    }

    public static Pkcs11CertificateCategory fromValue(long value) {
        for (Pkcs11CertificateCategory val : values()) {
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

    private final long mValue;
}