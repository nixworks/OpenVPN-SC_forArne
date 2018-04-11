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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;

public class Pkcs11DataObject extends Pkcs11StorageObject {
    Pkcs11DataObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11StringAttribute getApplicationAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mApplicationAttribute);
    }

    public Pkcs11ByteArrayAttribute getObjectIdAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mObjectIdAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mValueAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mApplicationAttribute);
        registerAttribute(mObjectIdAttribute);
        registerAttribute(mValueAttribute);
    }
    private final Pkcs11StringAttribute mApplicationAttribute = makeAttribute(Pkcs11StringAttribute.class, Pkcs11AttributeType.CKA_APPLICATION);
    private final Pkcs11ByteArrayAttribute mObjectIdAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_OBJECT_ID);
    private final Pkcs11ByteArrayAttribute mValueAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_VALUE);
}
