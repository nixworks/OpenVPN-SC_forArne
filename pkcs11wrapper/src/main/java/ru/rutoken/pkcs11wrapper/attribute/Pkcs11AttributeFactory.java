/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;

public class Pkcs11AttributeFactory {
    public static Pkcs11AttributeFactory getInstance() {
        if (null == sInstance.get()) {
            synchronized (Pkcs11AttributeFactory.class) {
                if (null == sInstance.get()) {
                    sInstance.set(new Pkcs11AttributeFactory());
                }
            }
        }
        return sInstance.get();
    }

    public boolean isAttributeRegistered(@NonNull Pkcs11AttributeType type) {
        return mAttributeClasses.containsKey(type);
    }

    public void registerAttribute(@NonNull Pkcs11AttributeType type, @NonNull Class<? extends Pkcs11Attribute> attribute) {
        if (!isAttributeRegistered(type))
            mAttributeClasses.put(Objects.requireNonNull(type), Objects.requireNonNull(attribute));
    }

    @NonNull
    public Pkcs11Attribute makeAttribute(@NonNull Pkcs11AttributeType type) {
        return makeAttribute(Pkcs11Attribute.class, type);
    }

    @NonNull
    public Pkcs11Attribute makeAttribute(@NonNull Pkcs11AttributeType type, @NonNull Object value) {
        checkRegistration(type);
        try {
            Constructor<? extends Pkcs11Attribute> constructor = mAttributeClasses.get(type).getDeclaredConstructor(Pkcs11AttributeType.class, Object.class);
            constructor.setAccessible(true);
            return constructor.newInstance(type, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  Makes attribute casting it to specified class.
     *  Type check is performed, ClassCastException exception will be thrown if user passes inconsistent type and class
     */
    @NonNull
    public <Attr extends Pkcs11Attribute > Attr makeAttribute(@NonNull Class<Attr> attributeClass, @NonNull Pkcs11AttributeType type) {
        checkRegistration(type);
        try {
            @SuppressWarnings("unchecked")
            final Class<Attr> attrClass = (Class<Attr>) mAttributeClasses.get(type);
            Constructor<Attr> constructor = attrClass.getDeclaredConstructor(Pkcs11AttributeType.class);
            constructor.setAccessible(true);
            Attr attribute = constructor.newInstance(type);
            if (!attributeClass.isInstance(attribute))
                throw new ClassCastException("attribute class is invalid");
            return attribute;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public <Attr extends Pkcs11Attribute > Attr makeAttribute(@NonNull Class<Attr> attributeClass, @NonNull Pkcs11AttributeType type, @NonNull Object value) {
        checkRegistration(type);
        try {
            @SuppressWarnings("unchecked")
            final Class<Attr> attrClass = (Class<Attr>) mAttributeClasses.get(type);
            Constructor<Attr> constructor = attrClass.getDeclaredConstructor(Pkcs11AttributeType.class,  Object.class);
            constructor.setAccessible(true);
            Attr attribute = constructor.newInstance(type, value);
            if (!attributeClass.isInstance(attribute))
                throw new ClassCastException("attribute class is invalid");
            return attribute;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Pkcs11AttributeFactory() {
        for(Pkcs11AttributeType type : Pkcs11AttributeType.values())
            registerAttribute(type, type.getAttributeClass());
    }

    private void checkRegistration(@NonNull Pkcs11AttributeType type) {
        if (!isAttributeRegistered(type))
            throw new RuntimeException("attribute not registered");
    }

    private static final AtomicReference<Pkcs11AttributeFactory> sInstance = new AtomicReference<>();
    private final Map<Pkcs11AttributeType, Class<? extends Pkcs11Attribute>> mAttributeClasses = new HashMap<>();
}
