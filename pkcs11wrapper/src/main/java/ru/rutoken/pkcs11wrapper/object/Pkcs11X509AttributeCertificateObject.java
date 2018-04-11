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

public class Pkcs11X509AttributeCertificateObject extends Pkcs11CertificateObject {
    Pkcs11X509AttributeCertificateObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getOwnerAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mOwnerAttribute);
    }

    public Pkcs11ByteArrayAttribute getAcIssuerAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mAcIssuerAttribute);
    }

    public Pkcs11ByteArrayAttribute getSerialNumberAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSerialNumberAttribute);
    }

    public Pkcs11ByteArrayAttribute getAttrTypesAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mAttrTypesAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mValueAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mOwnerAttribute);
        registerAttribute(mAcIssuerAttribute);
        registerAttribute(mSerialNumberAttribute);
        registerAttribute(mAttrTypesAttribute);
        registerAttribute(mValueAttribute);
    }

    private final Pkcs11ByteArrayAttribute mOwnerAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_OWNER);
    private final Pkcs11ByteArrayAttribute mAcIssuerAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_AC_ISSUER);
    private final Pkcs11ByteArrayAttribute mSerialNumberAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_SERIAL_NUMBER);
    private final Pkcs11ByteArrayAttribute mAttrTypesAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_ATTR_TYPES);
    private final Pkcs11ByteArrayAttribute mValueAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_VALUE);
}
