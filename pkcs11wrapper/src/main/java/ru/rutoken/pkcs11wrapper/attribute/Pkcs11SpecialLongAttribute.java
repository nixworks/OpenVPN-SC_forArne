/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Supplier;

/**
 * Designed to be used as base class for attributes which values represented with enums
 * @param <V> enum type
 */
public abstract class Pkcs11SpecialLongAttribute<V extends Supplier<Long>> extends Pkcs11LongAttribute {
    Pkcs11SpecialLongAttribute(@NonNull Class<V> valueType, @NonNull Pkcs11AttributeType type) {
        super(type);
        mValueType = Objects.requireNonNull(valueType);
    }

    Pkcs11SpecialLongAttribute(@NonNull Class<V> valueType, @NonNull Pkcs11AttributeType type, @NonNull Object value) {
        super(type);
        mValueType = Objects.requireNonNull(valueType);
        setValue(value);
    }

    /**
     * Handles enum values correctly
     */
    @Override
    public void setValue(@NonNull Object value) {
        if (value.getClass().equals(mValueType)) {
            @SuppressWarnings("unchecked")
            V typedValue = (V) value;
            super.setValue(typedValue.get());
        } else {
            super.setValue(value);
        }
    }

    private final Class<V> mValueType;
}
