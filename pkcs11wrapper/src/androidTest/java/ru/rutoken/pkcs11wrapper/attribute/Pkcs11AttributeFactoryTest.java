/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ObjectClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * We have to use instrumented tests because of unit tests problems with native libraries.
 */
@RunWith(AndroidJUnit4.class)
public class Pkcs11AttributeFactoryTest {
    @Test
    public void makeAttribute() {
        Pkcs11Attribute attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CLASS);
        assertTrue("wrong type", attribute instanceof Pkcs11ObjectClassAttribute);

        Pkcs11LongAttribute longAttribute = getFactory().makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_CLASS);
        assertTrue("wrong type", longAttribute instanceof Pkcs11ObjectClassAttribute);

        Pkcs11ObjectClassAttribute classAttribute = getFactory().makeAttribute(Pkcs11ObjectClassAttribute.class, Pkcs11AttributeType.CKA_CLASS);
    }

    @Test
    public void makeAttributeSetBooleanValue() {
        boolean value = false;
        Pkcs11Attribute attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_TOKEN, value);
        assertTrue("wrong type", attribute instanceof Pkcs11BooleanAttribute);
        assertEquals("value not equal", attribute.getValue(), value);
    }

    @Test
    public void makeAttributeSetStringValue() {
        String value = "test";
        Pkcs11Attribute attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_LABEL, value);
        assertTrue("wrong type", attribute instanceof Pkcs11StringAttribute);
        assertEquals("value not equal", attribute.getValue(), value);
    }

    @Test
    public void makeAttributeSetIntValue() {
        //int is not long
        int value = 0;
        Pkcs11Attribute attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY, value);
        assertTrue("wrong type", attribute instanceof Pkcs11LongAttribute);
        assertEquals("value not equal", attribute.getValue(), /*autoboxing fails*/new Long(value));
    }

    @Test
    public void makeAttributeSetLongValue() {
        long value = 0L;
        Pkcs11Attribute attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY, value);
        assertTrue("wrong type", attribute instanceof Pkcs11LongAttribute);
        assertEquals("value not equal", attribute.getValue(), value);
    }

    @Test
    public void makeAttributeSetEnumValue() {
        Pkcs11ObjectClass value = Pkcs11ObjectClass.CKO_PRIVATE_KEY;
        Pkcs11Attribute attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CLASS, value);
        assertTrue("wrong type", attribute instanceof Pkcs11ObjectClassAttribute);
        assertEquals("value not equal", attribute.getValue(), value.getValue());
    }

    @Test(expected = ClassCastException.class)
    public void makeAttributeWrongClass() {
        Pkcs11ObjectClassAttribute classAttribute = getFactory().makeAttribute(Pkcs11ObjectClassAttribute.class, Pkcs11AttributeType.CKA_KEY_TYPE);
    }

    @Test
    public void makeAllAttributes() {
        for (Pkcs11AttributeType type : Pkcs11AttributeType.values()) {
            getFactory().makeAttribute(type);
        }
    }

    private static Pkcs11AttributeFactory getFactory() {
        return Pkcs11AttributeFactory.getInstance();
    }
}
