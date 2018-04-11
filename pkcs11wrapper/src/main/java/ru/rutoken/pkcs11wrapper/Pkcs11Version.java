/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import ru.rutoken.pkcs11jna.CK_VERSION;

public class Pkcs11Version {
    public Pkcs11Version(@NonNull CK_VERSION version) {
        mMajor = version.major;
        mMinor = version.minor;
    }

    public byte getMajor() {
        return mMajor;
    }

    public byte getMinor() {
        return mMinor;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private final byte mMajor;
    private final byte mMinor;
}
