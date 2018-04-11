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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;

public class Pkcs11X509PublicKeyCertificateObject extends Pkcs11CertificateObject {
    Pkcs11X509PublicKeyCertificateObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getSubjectAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSubjectAttribute);
    }

    public Pkcs11ByteArrayAttribute getIdAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mIdAttribute);
    }

    public Pkcs11ByteArrayAttribute getIssuerAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mIssuerAttribute);
    }

    public Pkcs11ByteArrayAttribute getSerialNumberAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSerialNumberAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mValueAttribute);
    }

    public Pkcs11StringAttribute getUrlAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mUrlAttribute);
    }

    public Pkcs11ByteArrayAttribute getHashOfSubjectPublicKeyAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mHashOfSubjectPublicKeyAttribute);
    }

    public Pkcs11ByteArrayAttribute getHashOfIssuerPublicKeyAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mHashOfIssuerPublicKeyAttribute);
    }

    public Pkcs11LongAttribute getJavaMidpSecuritySomainAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mJavaMidpSecuritySomainAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSubjectAttribute);
        registerAttribute(mIdAttribute);
        registerAttribute(mIssuerAttribute);
        registerAttribute(mSerialNumberAttribute);
        registerAttribute(mValueAttribute);
        registerAttribute(mUrlAttribute);
        registerAttribute(mHashOfSubjectPublicKeyAttribute);
        registerAttribute(mHashOfIssuerPublicKeyAttribute);
        registerAttribute(mJavaMidpSecuritySomainAttribute);
    }

    private final Pkcs11ByteArrayAttribute mSubjectAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_SUBJECT);
    private final Pkcs11ByteArrayAttribute mIdAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_ID);
    private final Pkcs11ByteArrayAttribute mIssuerAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_ISSUER);
    private final Pkcs11ByteArrayAttribute mSerialNumberAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_SERIAL_NUMBER);
    private final Pkcs11ByteArrayAttribute mValueAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_VALUE);
    private final Pkcs11StringAttribute mUrlAttribute = makeAttribute(Pkcs11StringAttribute.class, Pkcs11AttributeType.CKA_URL);
    private final Pkcs11ByteArrayAttribute mHashOfSubjectPublicKeyAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_HASH_OF_SUBJECT_PUBLIC_KEY);
    private final Pkcs11ByteArrayAttribute mHashOfIssuerPublicKeyAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_HASH_OF_ISSUER_PUBLIC_KEY);
    private final Pkcs11LongAttribute mJavaMidpSecuritySomainAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_JAVA_MIDP_SECURITY_DOMAIN);
    //private final Pkcs11ByteArrayAttribute mNameHashAlgorithmAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_NAME_HASH_ALGORITHM);
}
