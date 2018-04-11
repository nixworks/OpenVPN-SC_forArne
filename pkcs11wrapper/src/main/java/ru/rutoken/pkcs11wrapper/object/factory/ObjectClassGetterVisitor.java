/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Collects unique objects classes
 */
public class ObjectClassGetterVisitor implements ObjectFactoryNodeVisitor {
    @Override
    public void visit(@NonNull ObjectFactoryNode<? extends Pkcs11Object> node) {
        mObjectClasses.add(node.getObjectClass());
        for (ObjectFactoryNode child : node.getChildren().values()) {
            child.acceptVisitor(this);
        }
    }

    @NonNull
    public List<Class<? extends Pkcs11Object>> getObjectClasses() {
        return mObjectClasses;
    }

    private List<Class<? extends Pkcs11Object>> mObjectClasses = new ArrayList<>();
}
