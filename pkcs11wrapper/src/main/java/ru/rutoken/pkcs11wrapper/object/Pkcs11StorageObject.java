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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;

public class Pkcs11StorageObject extends Pkcs11Object {
    Pkcs11StorageObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11BooleanAttribute getTokenAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mTokenAttribute);
    }

    public Pkcs11BooleanAttribute getPrivateAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mPrivateAttribute);
    }

    public Pkcs11BooleanAttribute getModifiableAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mModifiableAttribute);
    }

    public Pkcs11StringAttribute getLabelAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mLabelAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mTokenAttribute);
        registerAttribute(mPrivateAttribute);
        registerAttribute(mModifiableAttribute);
        registerAttribute(mLabelAttribute);
    }

    private final Pkcs11BooleanAttribute mTokenAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_TOKEN);
    private final Pkcs11BooleanAttribute mPrivateAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_PRIVATE);
    private final Pkcs11BooleanAttribute mModifiableAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_MODIFIABLE);
    private final Pkcs11StringAttribute mLabelAttribute = makeAttribute(Pkcs11StringAttribute.class, Pkcs11AttributeType.CKA_LABEL);
    //private final Pkcs11BooleanAttribute mCopyableAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_COPYABLE));
    //private final Pkcs11BooleanAttribute mDestroyableAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_DESTROYABLE));
}
