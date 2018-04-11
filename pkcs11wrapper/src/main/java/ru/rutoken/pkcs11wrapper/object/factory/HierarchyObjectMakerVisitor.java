/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Creates Pkcs11Object depending on attribute values read from token
 */
public class HierarchyObjectMakerVisitor implements ObjectFactoryNodeVisitor {
    public HierarchyObjectMakerVisitor(@NonNull Pkcs11Session session, long objectHandle) {
        mSession = Objects.requireNonNull(session);
        mObjectHandle = objectHandle;
    }

    @Override
    public void visit(@NonNull ObjectFactoryNode<? extends Pkcs11Object> node) throws Pkcs11Exception, ReflectiveOperationException {
        final Pkcs11Attribute attribute = node.getAttribute();
        if (null != attribute) {
            Pkcs11Attribute.getAttributeValue(mSession, mObjectHandle, attribute);
            final ObjectFactoryNode<?> child = node.getChildren().get(attribute.getValue());
            if (null != child) {
                child.acceptVisitor(this);
                return;
            }
        }
        mObject = construct(node.getObjectClass());
    }

    @NonNull
    public Pkcs11Object getObject() {
        return Objects.requireNonNull(mObject);
    }

    private <Obj extends Pkcs11Object> Pkcs11Object construct(Class<Obj> objectClass) throws ReflectiveOperationException {
        Constructor<? extends Pkcs11Object> constructor = objectClass.getDeclaredConstructor(long.class);
        constructor.setAccessible(true);
        return constructor.newInstance(mObjectHandle);
    }

    private Pkcs11Object mObject;
    private final Pkcs11Session mSession;
    private final long mObjectHandle;
}
