/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11HardwareFeatureType implements Supplier<Long> {
    CKH_MONOTONIC_COUNTER(Pkcs11Constants.CKH_MONOTONIC_COUNTER),
    CKH_CLOCK(Pkcs11Constants.CKH_CLOCK),
    CKH_USER_INTERFACE(Pkcs11Constants.CKH_USER_INTERFACE),
    CKH_VENDOR_DEFINED(Pkcs11Constants.CKH_VENDOR_DEFINED);

    public static Pkcs11HardwareFeatureType fromValue(long value) {
        for (Pkcs11HardwareFeatureType val : values()) {
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

    Pkcs11HardwareFeatureType(NativeLong value) {
        mValue = value.longValue();
    }

    private final long mValue;
}
