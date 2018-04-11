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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11CertificateTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11LongAttribute;

public class Pkcs11CertificateObject extends Pkcs11StorageObject {
    Pkcs11CertificateObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11CertificateTypeAttribute getCertificateTypeAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCertificateTypeAttribute);
    }

    public Pkcs11BooleanAttribute getTrustedAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mTrustedAttribute);
    }

    public Pkcs11LongAttribute getCertificateCategoryAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCertificateCategoryAttribute);
    }

    public Pkcs11ByteArrayAttribute getCheckValueAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mCheckValueAttribute);
    }

    public Pkcs11DateAttribute getStartDateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mStartDateAttribute);
    }

    public Pkcs11DateAttribute getEndDateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mEndDateAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mCertificateTypeAttribute);
        registerAttribute(mTrustedAttribute);
        registerAttribute(mCertificateCategoryAttribute);
        registerAttribute(mCheckValueAttribute);
        registerAttribute(mStartDateAttribute);
        registerAttribute(mEndDateAttribute);
    }

    private final Pkcs11CertificateTypeAttribute mCertificateTypeAttribute =
            makeAttribute(Pkcs11CertificateTypeAttribute.class, Pkcs11AttributeType.CKA_CERTIFICATE_TYPE);
    private final Pkcs11BooleanAttribute mTrustedAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_TRUSTED);
    private final Pkcs11LongAttribute mCertificateCategoryAttribute = makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY);
    private final Pkcs11ByteArrayAttribute mCheckValueAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_CHECK_VALUE);
    private final Pkcs11DateAttribute mStartDateAttribute = makeAttribute(Pkcs11DateAttribute.class, Pkcs11AttributeType.CKA_START_DATE);
    private final Pkcs11DateAttribute mEndDateAttribute = makeAttribute(Pkcs11DateAttribute.class, Pkcs11AttributeType.CKA_END_DATE);
    //private final Pkcs11ByteArrayAttribute mPublicKeyInfoAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PUBLIC_KEY_INFO);
}
