/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Finds first node with equal object class
 */
public class NodeByObjectClassFinderVisitor<Obj extends Pkcs11Object> implements ObjectFactoryNodeVisitor {
    public NodeByObjectClassFinderVisitor(@NonNull Class<Obj> objectClass) {
        mObjectClass = Objects.requireNonNull(objectClass);
    }

    @Override
    public void visit(@NonNull ObjectFactoryNode<? extends Pkcs11Object> node) {
        if (node.getObjectClass().equals(mObjectClass)) {
            @SuppressWarnings("unchecked")
            ObjectFactoryNode<Obj> foundNode = (ObjectFactoryNode<Obj>) node;
            mNode = foundNode;
        } else {
            for (ObjectFactoryNode<?> child : node.getChildren().values()) {
                child.acceptVisitor(this);
                if (null != mNode)
                    break;
            }
        }
    }

    @Nullable
    public ObjectFactoryNode<Obj> getNode() {
        return mNode;
    }

    @Nullable
    private ObjectFactoryNode<Obj> mNode;
    private final Class<Obj> mObjectClass;
}
