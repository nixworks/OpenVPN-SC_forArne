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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11LongAttribute;

public class Pkcs11RsaPublicKeyObject extends Pkcs11PublicKeyObject {
    Pkcs11RsaPublicKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getModulusAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mModulusAttribute);
    }

    public Pkcs11LongAttribute getMmodulusBitsAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mMmodulusBitsAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicExponentAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPublicExponentAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mModulusAttribute);
        registerAttribute(mMmodulusBitsAttribute);
        registerAttribute(mPublicExponentAttribute);
    }

    private final Pkcs11ByteArrayAttribute mModulusAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_MODULUS);
    private final Pkcs11LongAttribute mMmodulusBitsAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_MODULUS_BITS);
    private final Pkcs11ByteArrayAttribute mPublicExponentAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PUBLIC_EXPONENT);


}
