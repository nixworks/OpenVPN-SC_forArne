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

public class Pkcs11PublicKeyObject extends Pkcs11KeyObject {
    Pkcs11PublicKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getSubjectAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mSubjectAttribute);
    }

    public Pkcs11BooleanAttribute getEncryptAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mEncryptAttribute);
    }

    public Pkcs11BooleanAttribute getVerifyAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mVerifyAttribute);
    }

    public Pkcs11BooleanAttribute getVerifyRecoverAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mVerifyRecoverAttribute);
    }

    public Pkcs11BooleanAttribute getWrapAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mWrapAttribute);
    }

    public Pkcs11BooleanAttribute getTrustedAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mTrustedAttribute);
    }

    public Pkcs11ArrayAttribute getWrapTemplateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mWrapTemplateAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSubjectAttribute);
        registerAttribute(mEncryptAttribute);
        registerAttribute(mVerifyAttribute);
        registerAttribute(mVerifyRecoverAttribute);
        registerAttribute(mWrapAttribute);
        registerAttribute(mTrustedAttribute);
        registerAttribute(mWrapTemplateAttribute);
    }

    private final Pkcs11ByteArrayAttribute mSubjectAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_SUBJECT);
    private final Pkcs11BooleanAttribute mEncryptAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_ENCRYPT);
    private final Pkcs11BooleanAttribute mVerifyAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_VERIFY);
    private final Pkcs11BooleanAttribute mVerifyRecoverAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_VERIFY_RECOVER);
    private final Pkcs11BooleanAttribute mWrapAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_WRAP);
    private final Pkcs11BooleanAttribute mTrustedAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_TRUSTED);
    private final Pkcs11ArrayAttribute mWrapTemplateAttribute = makeAttribute(Pkcs11ArrayAttribute.class, Pkcs11AttributeType.CKA_WRAP_TEMPLATE);
    //private final Pkcs11ByteArrayAttribute mPublicKeyInfoAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_PUBLIC_KEY_INFO);
}
