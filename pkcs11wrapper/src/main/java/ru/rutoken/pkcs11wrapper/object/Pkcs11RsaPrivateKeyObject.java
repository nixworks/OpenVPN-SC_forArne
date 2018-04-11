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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;

public class Pkcs11RsaPrivateKeyObject extends Pkcs11PrivateKeyObject {
    Pkcs11RsaPrivateKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getModulusAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mModulusAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicExponentAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPublicExponentAttribute);
    }

    public Pkcs11ByteArrayAttribute getPrivateExponentAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPrivateExponentAttribute);
    }

    public Pkcs11ByteArrayAttribute getPrime1AttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPrime1Attribute);
    }

    public Pkcs11ByteArrayAttribute getPrime2AttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPrime2Attribute);
    }

    public Pkcs11ByteArrayAttribute getExponent1AttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mExponent1Attribute);
    }

    public Pkcs11ByteArrayAttribute getExponent2AttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mExponent2Attribute);
    }

    public Pkcs11ByteArrayAttribute getCoefficientAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCoefficientAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mModulusAttribute);
        registerAttribute(mPublicExponentAttribute);
        registerAttribute(mPrivateExponentAttribute);
        registerAttribute(mPrime1Attribute);
        registerAttribute(mPrime2Attribute);
        registerAttribute(mExponent1Attribute);
        registerAttribute(mExponent2Attribute);
        registerAttribute(mCoefficientAttribute);
    }

    private final Pkcs11ByteArrayAttribute mModulusAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_MODULUS);
    private final Pkcs11ByteArrayAttribute mPublicExponentAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PUBLIC_EXPONENT);
    private final Pkcs11ByteArrayAttribute mPrivateExponentAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PRIVATE_EXPONENT);
    private final Pkcs11ByteArrayAttribute mPrime1Attribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PRIME_1);
    private final Pkcs11ByteArrayAttribute mPrime2Attribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PRIME_2);
    private final Pkcs11ByteArrayAttribute mExponent1Attribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_EXPONENT_1);
    private final Pkcs11ByteArrayAttribute mExponent2Attribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_EXPONENT_2);
    private final Pkcs11ByteArrayAttribute mCoefficientAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_COEFFICIENT);
}
