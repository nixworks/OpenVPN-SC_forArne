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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ArrayAttribute;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;

public class Pkcs11PrivateKeyObject extends Pkcs11KeyObject {
    Pkcs11PrivateKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getSubjectAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSubjectAttribute);
    }

    public Pkcs11BooleanAttribute getSensitiveAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSensitiveAttribute);
    }

    public Pkcs11BooleanAttribute getDecryptAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mDecryptAttribute);
    }

    public Pkcs11BooleanAttribute getSignAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSignAttribute);
    }

    public Pkcs11BooleanAttribute getSignRecoverAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSignRecoverAttribute);
    }

    public Pkcs11BooleanAttribute getUnwrapAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mUnwrapAttribute);
    }

    public Pkcs11BooleanAttribute getExtractableAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mExtractableAttribute);
    }

    public Pkcs11BooleanAttribute getAlwaysSensitiveAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mAlwaysSensitiveAttribute);
    }

    public Pkcs11BooleanAttribute getNeverExtractableAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mNeverExtractableAttribute);
    }

    public Pkcs11BooleanAttribute getWrapWithTrustedAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mWrapWithTrustedAttribute);
    }

    public Pkcs11ArrayAttribute getUnwrapTemplateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mUnwrapTemplateAttribute);
    }

    public Pkcs11BooleanAttribute getAlwaysAuthenticateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mAlwaysAuthenticateAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSubjectAttribute);
        registerAttribute(mSensitiveAttribute);
        registerAttribute(mDecryptAttribute);
        registerAttribute(mSignAttribute);
        registerAttribute(mSignRecoverAttribute);
        registerAttribute(mUnwrapAttribute);
        registerAttribute(mExtractableAttribute);
        registerAttribute(mAlwaysSensitiveAttribute);
        registerAttribute(mNeverExtractableAttribute);
        registerAttribute(mWrapWithTrustedAttribute);
        registerAttribute(mUnwrapTemplateAttribute);
        registerAttribute(mAlwaysAuthenticateAttribute);
    }

    private final Pkcs11ByteArrayAttribute mSubjectAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_SUBJECT);
    private final Pkcs11BooleanAttribute mSensitiveAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_SENSITIVE);
    private final Pkcs11BooleanAttribute mDecryptAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_DECRYPT);
    private final Pkcs11BooleanAttribute mSignAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_SIGN);
    private final Pkcs11BooleanAttribute mSignRecoverAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_SIGN_RECOVER);
    private final Pkcs11BooleanAttribute mUnwrapAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_UNWRAP);
    private final Pkcs11BooleanAttribute mExtractableAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_EXTRACTABLE);
    private final Pkcs11BooleanAttribute mAlwaysSensitiveAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_ALWAYS_SENSITIVE);
    private final Pkcs11BooleanAttribute mNeverExtractableAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_NEVER_EXTRACTABLE);
    private final Pkcs11BooleanAttribute mWrapWithTrustedAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_WRAP_WITH_TRUSTED);
    private final Pkcs11ArrayAttribute mUnwrapTemplateAttribute = makeAttribute(Pkcs11ArrayAttribute.class, Pkcs11AttributeType.CKA_UNWRAP_TEMPLATE);
    private final Pkcs11BooleanAttribute mAlwaysAuthenticateAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_ALWAYS_AUTHENTICATE);
    //private final Pkcs11ByteArrayAttribute mPublicKeyInfoAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PUBLIC_KEY_INFO);
}
