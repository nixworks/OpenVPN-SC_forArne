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
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

public class TemplateMakerVisitor implements ObjectFactoryNodeVisitor {
    @Override
    public void visit(@NonNull ObjectFactoryNode<?> node) {
        ObjectFactoryNode<?> currentNode = node;
        while (null != currentNode) {
            final ObjectFactoryNode parent = currentNode.getParent();
            final Object value = currentNode.getParentAttributeValue();
            if (null == parent || null == value)
                break;
            final Pkcs11Attribute attribute = parent.getAttribute();
            if (null == attribute)
                break;
            mTemplate.add(Pkcs11AttributeFactory.getInstance().makeAttribute(attribute.getType(), value));
            currentNode = currentNode.getParent();
        }
    }

    @NonNull
    public List<Pkcs11Attribute> getTemplate() {
        return mTemplate;
    }

    private final List<Pkcs11Attribute> mTemplate = new ArrayList<>();
}
