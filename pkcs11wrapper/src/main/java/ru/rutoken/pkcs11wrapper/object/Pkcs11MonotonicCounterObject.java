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

public class Pkcs11MonotonicCounterObject extends Pkcs11HardwareFeatureObject {
    Pkcs11MonotonicCounterObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11BooleanAttribute getResetOnInitAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mResetOnInitAttribute);
    }

    public Pkcs11BooleanAttribute getHasResetAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mHasResetAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mValueAttribute);
    }

    @CallSuper
    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mResetOnInitAttribute);
        registerAttribute(mHasResetAttribute);
        registerAttribute(mValueAttribute);
    }

    private final Pkcs11BooleanAttribute mResetOnInitAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_RESET_ON_INIT);
    private final Pkcs11BooleanAttribute mHasResetAttribute = makeAttribute(Pkcs11BooleanAttribute.class, Pkcs11AttributeType.CKA_HAS_RESET);
    private final Pkcs11ByteArrayAttribute mValueAttribute = makeAttribute(Pkcs11ByteArrayAttribute.class, Pkcs11AttributeType.CKA_VALUE);
}


