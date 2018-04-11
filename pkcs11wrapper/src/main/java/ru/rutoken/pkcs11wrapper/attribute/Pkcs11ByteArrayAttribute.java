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

public class Pkcs11ByteArrayAttribute extends Pkcs11Attribute {
    Pkcs11ByteArrayAttribute(@NonNull Pkcs11AttributeType type) {
        super(type);
    }

    Pkcs11ByteArrayAttribute(@NonNull Pkcs11AttributeType type, @NonNull Object value) {
        super(type);
        setValue(value);
    }

    @NonNull
    @Override
    public Object getValue() {
        return getByteArrayValue();
    }

    @Override
    public void setValue(@NonNull Object value) {
        setByteArrayValue((byte[]) value);
    }

    public byte[] getByteArrayValue() {
        if (null == getAttribute().pValue) {
            throw new RuntimeException("Attribute not set");
        }
        return getAttribute().pValue.getByteArray(0,  getAttribute().ulValueLen.intValue());
    }

    public void setByteArrayValue(byte[] value) {
        mAttribute.setAttr(new NativeLong(getType().getValue()), value);
    }
}
