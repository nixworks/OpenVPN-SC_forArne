/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;

public class Pkcs11UserInterfaceObject extends Pkcs11HardwareFeatureObject {
    Pkcs11UserInterfaceObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11LongAttribute getPixelYAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPixelYAttribute);
    }

    public Pkcs11LongAttribute getResolutionAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mResolutionAttribute);
    }

    public Pkcs11LongAttribute getCharRowsAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCharRowsAttribute);
    }

    public Pkcs11LongAttribute getCharColumnsAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCharColumnsAttribute);
    }

    public Pkcs11BooleanAttribute getColorAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mColorAttribute);
    }

    public Pkcs11LongAttribute getBitsPerPixelAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mBitsPerPixelAttribute);
    }

    public Pkcs11StringAttribute getCharSetsAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCharSetsAttribute);
    }

    public Pkcs11StringAttribute getEncodingMethodsAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mEncodingMethodsAttribute);
    }

    public Pkcs11StringAttribute getMimeTypesAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mMimeTypesAttribute);
    }

    public Pkcs11LongAttribute getPixelXAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPixelXAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mPixelXAttribute);
        registerAttribute(mPixelYAttribute);
        registerAttribute(mResolutionAttribute);
        registerAttribute(mCharRowsAttribute);
        registerAttribute(mCharColumnsAttribute);
        registerAttribute(mColorAttribute);
        registerAttribute(mBitsPerPixelAttribute);
        registerAttribute(mCharSetsAttribute);
        registerAttribute(mEncodingMethodsAttribute);
        registerAttribute(mMimeTypesAttribute);
    }

    private final Pkcs11LongAttribute mPixelXAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_PIXEL_X);
    private final Pkcs11LongAttribute mPixelYAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_PIXEL_Y);
    private final Pkcs11LongAttribute mResolutionAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_RESOLUTION);
    private final Pkcs11LongAttribute mCharRowsAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_CHAR_ROWS);
    private final Pkcs11LongAttribute mCharColumnsAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_CHAR_COLUMNS);
    private final Pkcs11BooleanAttribute mColorAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_COLOR);
    private final Pkcs11LongAttribute mBitsPerPixelAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_BITS_PER_PIXEL);
    private final Pkcs11StringAttribute mCharSetsAttribute = makeAttribute(Pkcs11StringAttribute.class, Pkcs11AttributeType.CKA_CHAR_SETS);
    private final Pkcs11StringAttribute mEncodingMethodsAttribute = makeAttribute(Pkcs11StringAttribute.class, Pkcs11AttributeType.CKA_ENCODING_METHODS);
    private final Pkcs11StringAttribute mMimeTypesAttribute = makeAttribute(Pkcs11StringAttribute.class, Pkcs11AttributeType.CKA_MIME_TYPES);
}


