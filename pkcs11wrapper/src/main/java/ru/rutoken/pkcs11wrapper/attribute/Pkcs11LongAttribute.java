/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;

public class Pkcs11LongAttribute extends Pkcs11Attribute {
    Pkcs11LongAttribute(@NonNull Pkcs11AttributeType type) {
        super(type);
    }

    Pkcs11LongAttribute(@NonNull Pkcs11AttributeType type, @NonNull Object value) {
        super(type);
        setValue(value);
    }

    @NonNull
    @Override
    public Object getValue() {
        return getLongValue();
    }

    @Override
    public void setValue(@NonNull Object value) {
        if (value instanceof Integer)
            setLongValue(((Integer) value).longValue());
        else
            setLongValue((Long) value);
    }

    public long getLongValue() {
        if (null == getAttribute().pValue) {
            throw new RuntimeException("Attribute not set");
        }
        return getAttribute().pValue.getNativeLong(0).longValue();
    }

    public void setLongValue(long value) {
        mAttribute.setAttr(new NativeLong(getType().getValue()), new NativeLong(value));
    }
}
