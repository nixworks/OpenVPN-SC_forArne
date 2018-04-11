/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ObjectClassAttribute;

/**
 * Base class for pkcs11 objects
 */
public class Pkcs11Object {
    /**
     * Constructor for object creation by reading attributes from token
     */
    Pkcs11Object(long objectHandle) {
        mObjectHandle = objectHandle;
    }

    public long getHandle() {
        return mObjectHandle;
    }

    public Pkcs11ObjectClassAttribute getClassAttributeValue(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValue(session, mClassAttribute);
    }

    /**
     * Read attribute from token
     */
    @NonNull
    public Pkcs11Attribute getAttributeValue(@NonNull Pkcs11Session session, @NonNull Pkcs11AttributeType type) throws Pkcs11Exception {
        return getAttributeValue(session, getAttribute(type));
    }

    /**
     * Read attributes from token
     */
    @NonNull
    public List<Pkcs11Attribute> getAttributeValues(@NonNull Pkcs11Session session, @NonNull List<Pkcs11Attribute> attributes) throws Pkcs11Exception {
        Pkcs11Attribute.getAttributeValues(session, mObjectHandle, attributes);
        return attributes;
    }

    /**
     * Read all attributes from token
     */
    @NonNull
    public List<Pkcs11Attribute> getAttributeValues(@NonNull Pkcs11Session session) throws Pkcs11Exception {
        return getAttributeValues(session, getAttributes());
    }

    /**
     * Makes template from all object attributes. Values of attributes are not set.
     */
    @NonNull
    List<Pkcs11Attribute> makeClearTemplate() {
        final List<Pkcs11Attribute> template = new ArrayList<>();
        for (Pkcs11Attribute attribute : getAttributes()) {
            template.add(Pkcs11AttributeFactory.getInstance().makeAttribute(attribute.getType()));
        }
        return template;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Virtual method to add subclass attributes into attributes map
     */
    @CallSuper
    protected void registerAttributes() {
        registerAttribute(mClassAttribute);
    }

    protected void registerAttribute(@NonNull Pkcs11Attribute attribute) {
        if (mAttributes.containsKey(attribute.getType()))
            throw new InvalidParameterException();
        mAttributes.put(attribute.getType(), attribute);
    }

    @NonNull
    protected <A extends Pkcs11Attribute> A getAttributeValue(@NonNull Pkcs11Session session, A attribute) throws Pkcs11Exception {
        Pkcs11Attribute.getAttributeValue(session, mObjectHandle, attribute);
        return attribute;
    }

    protected static <Attr extends Pkcs11Attribute > Attr makeAttribute(@NonNull Class<Attr> attributeClass, @NonNull Pkcs11AttributeType type) {
        return Pkcs11AttributeFactory.getInstance().makeAttribute(attributeClass, type);
    }

    @NonNull
    private Pkcs11Attribute getAttribute(@NonNull Pkcs11AttributeType type) {
        return getAttribute(Pkcs11Attribute.class, type);
    }

    /**
     *  Get attribute casting it to specified class.
     *  Type check is performed, ClassCastException exception will be thrown if user passes inconsistent type and class
     */
    @NonNull
    private <Attr extends Pkcs11Attribute> Attr getAttribute(@NonNull Class<Attr> attributeClass, @NonNull Pkcs11AttributeType type) {
        if (type.getAttributeClass().isInstance(attributeClass))
            throw new ClassCastException("attribute class is invalid");
        @SuppressWarnings("unchecked")
        final Attr attribute = (Attr) mAttributes.get(type);
        if (null == attribute)
            throw new IllegalArgumentException();
        return attribute;
    }

    @NonNull
    private List<Pkcs11Attribute> getAttributes() {
        return new ArrayList<>(mAttributes.values());
    }

    private final long mObjectHandle;
    private final Map<Pkcs11AttributeType, Pkcs11Attribute> mAttributes = new HashMap<>();
    private final Pkcs11ObjectClassAttribute mClassAttribute = makeAttribute(Pkcs11ObjectClassAttribute.class, Pkcs11AttributeType.CKA_CLASS);
}
