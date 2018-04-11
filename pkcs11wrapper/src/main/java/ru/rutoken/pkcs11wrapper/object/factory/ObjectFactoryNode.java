/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * We have to select Pkcs11Object type depending on many attribute values.
 * Each node is associated with concrete Pkcs11Object subclass, and attribute.
 * Node's children are mapped by attribute values
 */
public class ObjectFactoryNode<Obj extends Pkcs11Object> {
    public ObjectFactoryNode(@NonNull Class<Obj> objectClass, @Nullable Pkcs11Attribute attribute) {
        mObjectClass = Objects.requireNonNull(objectClass);
        mAttribute = attribute;
    }

    public void acceptVisitor(@NonNull ObjectFactoryNodeVisitor visitor) throws RuntimeException {
        try {
            visitor.visit(this);
        } catch (Exception e) {
            throw new RuntimeException("Visitor " + visitor.getClass().getSimpleName() + " exception: " + e.getMessage());
        }
    }

    @NonNull
    public Class<Obj> getObjectClass() {
        return mObjectClass;
    }

    @Nullable
    public Pkcs11Attribute getAttribute() {
        return mAttribute;
    }

    @Nullable
    public ObjectFactoryNode getParent() {
        return mParent;
    }

    public Map<Object, ObjectFactoryNode> getChildren() {
        return mChildren;
    }

    public void addChild(@NonNull Object attributeValue, @NonNull ObjectFactoryNode child) {
        if (this == child)
            throw new RuntimeException("node cannot add self instance as child");
        if (null == mAttribute)
            throw new RuntimeException("can not add child node if no attribute was specified");
        if (mChildren.containsKey(attributeValue))
            throw new IllegalArgumentException();
        child.setParent(this);
        innerAddChild(Objects.requireNonNull(attributeValue), Objects.requireNonNull(child));
    }

    public void setParent(@NonNull ObjectFactoryNode parent) {
        if (this == parent)
            throw new RuntimeException("node cannot add self instance as parent");
        mParent = Objects.requireNonNull(parent);
    }

    @Nullable
    public Object getParentAttributeValue() {
        return mParentAttributeValue;
    }

    private void innerAddChild(@NonNull Object attributeValue, @NonNull ObjectFactoryNode child) {
        mChildren.put(attributeValue, child);
        child.mParentAttributeValue = attributeValue;
    }

    private final Map<Object/*attribute value*/, ObjectFactoryNode> mChildren = new HashMap<>();
    private final Class<Obj> mObjectClass;
    @Nullable
    private final Pkcs11Attribute mAttribute;
    @Nullable
    private ObjectFactoryNode mParent;
    @Nullable
    private Object mParentAttributeValue;
}
