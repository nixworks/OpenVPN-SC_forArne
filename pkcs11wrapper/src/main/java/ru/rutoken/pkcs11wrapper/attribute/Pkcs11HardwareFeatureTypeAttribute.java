/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11HardwareFeatureType;

public class Pkcs11HardwareFeatureTypeAttribute extends Pkcs11SpecialLongAttribute<Pkcs11HardwareFeatureType> {
    Pkcs11HardwareFeatureTypeAttribute(@NonNull Pkcs11AttributeType type) {
        super(Pkcs11HardwareFeatureType.class, type);
    }

    Pkcs11HardwareFeatureTypeAttribute(@NonNull Pkcs11AttributeType type, @NonNull Object value) {
        super(Pkcs11HardwareFeatureType.class, type, value);
    }
}
