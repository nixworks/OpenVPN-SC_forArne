/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;

//TODO implement
public class Pkcs11MechanismArrayAttribute extends Pkcs11Attribute {
    Pkcs11MechanismArrayAttribute(@NonNull Pkcs11AttributeType type) {
        super(type);
    }

    Pkcs11MechanismArrayAttribute(@NonNull Pkcs11AttributeType type, @NonNull Object value) {
        super(type);
        setValue(value);
    }

    @NonNull
    @Override
    public Object getValue() {
        return new Object();
    }

    @Override
    public void setValue(@NonNull Object value) {

    }
}
