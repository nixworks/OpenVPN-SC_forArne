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
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismTypeAttribute;

public class Pkcs11MechanismObject extends Pkcs11Object {
    Pkcs11MechanismObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11MechanismTypeAttribute getMechanismTypeAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mMechanismTypeAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mMechanismTypeAttribute);
    }

    private final Pkcs11MechanismTypeAttribute mMechanismTypeAttribute = makeAttribute(Pkcs11MechanismTypeAttribute.class, Pkcs11AttributeType.CKA_MECHANISM_TYPE);
}
