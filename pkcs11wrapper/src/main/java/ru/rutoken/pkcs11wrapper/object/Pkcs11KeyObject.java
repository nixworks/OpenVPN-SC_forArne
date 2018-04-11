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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11KeyTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismTypeAttribute;

public class Pkcs11KeyObject extends Pkcs11StorageObject {
    Pkcs11KeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11KeyTypeAttribute getKeyTypeAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mKeyTypeAttribute);
    }

    public Pkcs11ByteArrayAttribute getIdAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mIdAttribute);
    }

    public Pkcs11DateAttribute getStartDateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mStartDateAttribute);
    }

    public Pkcs11DateAttribute getEndDateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mEndDateAttribute);
    }

    public Pkcs11BooleanAttribute getDeriveAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mDeriveAttribute);
    }

    public Pkcs11BooleanAttribute getLocalAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mLocalAttribute);
    }

    public Pkcs11MechanismTypeAttribute getKeyGenMechanismAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mKeyGenMechanismAttribute);
    }

    public Pkcs11MechanismArrayAttribute getAllowedMechanismsAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mAllowedMechanismsAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mKeyTypeAttribute);
        registerAttribute(mIdAttribute);
        registerAttribute(mStartDateAttribute);
        registerAttribute(mEndDateAttribute);
        registerAttribute(mDeriveAttribute);
        registerAttribute(mLocalAttribute);
        registerAttribute(mKeyGenMechanismAttribute);
        registerAttribute(mAllowedMechanismsAttribute);
    }

    private final Pkcs11KeyTypeAttribute mKeyTypeAttribute = makeAttribute(Pkcs11KeyTypeAttribute.class, Pkcs11AttributeType.CKA_KEY_TYPE);
    private final Pkcs11ByteArrayAttribute mIdAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_ID);
    private final Pkcs11DateAttribute mStartDateAttribute = makeAttribute(Pkcs11DateAttribute.class, Pkcs11AttributeType.CKA_START_DATE);
    private final Pkcs11DateAttribute mEndDateAttribute = makeAttribute(Pkcs11DateAttribute.class, Pkcs11AttributeType.CKA_END_DATE);
    private final Pkcs11BooleanAttribute mDeriveAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_DERIVE);
    private final Pkcs11BooleanAttribute mLocalAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_LOCAL);
    private final Pkcs11MechanismTypeAttribute mKeyGenMechanismAttribute =
            makeAttribute(Pkcs11MechanismTypeAttribute.class, Pkcs11AttributeType.CKA_KEY_GEN_MECHANISM);
    private final Pkcs11MechanismArrayAttribute mAllowedMechanismsAttribute =
            makeAttribute(Pkcs11MechanismArrayAttribute.class, Pkcs11AttributeType.CKA_ALLOWED_MECHANISMS);
}
