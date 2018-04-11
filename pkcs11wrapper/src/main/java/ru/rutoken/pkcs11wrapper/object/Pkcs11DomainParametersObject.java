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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11KeyTypeAttribute;

public class Pkcs11DomainParametersObject extends Pkcs11StorageObject {
    Pkcs11DomainParametersObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11KeyTypeAttribute getKeyTypeAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mKeyTypeAttribute);
    }

    public Pkcs11BooleanAttribute getLocalAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mLocalAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mKeyTypeAttribute);
        registerAttribute(mLocalAttribute);
    }

    private final Pkcs11KeyTypeAttribute mKeyTypeAttribute = makeAttribute(Pkcs11KeyTypeAttribute.class, Pkcs11AttributeType.CKA_KEY_TYPE);
    private final Pkcs11BooleanAttribute mLocalAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_LOCAL);
}
