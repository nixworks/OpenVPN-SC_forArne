/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ObjectClass;

public class Pkcs11ObjectClassAttribute extends Pkcs11SpecialLongAttribute<Pkcs11ObjectClass> {
    Pkcs11ObjectClassAttribute(@NonNull Pkcs11AttributeType type) {
        super(Pkcs11ObjectClass.class, type);
    }

    Pkcs11ObjectClassAttribute(@NonNull Pkcs11AttributeType type, @NonNull Object value) {
        super(Pkcs11ObjectClass.class, type, value);
    }
}


