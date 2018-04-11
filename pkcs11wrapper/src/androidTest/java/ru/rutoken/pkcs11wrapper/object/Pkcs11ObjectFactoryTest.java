/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ObjectClass;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class Pkcs11ObjectFactoryTest {

    @Test
    public void makePkcs11Object() {
        Pkcs11Object object = getFactory().makePkcs11Object(0);
    }

    @Test
    public void makeObject() {
        Pkcs11RsaPrivateKeyObject key = getFactory().makeObject(Pkcs11RsaPrivateKeyObject.class, 0);
    }

    @Test
    public void makeTemplate() {
        List<Pkcs11Attribute> template = getFactory().makeTemplate(Pkcs11RsaPrivateKeyObject.class);

        List<Pkcs11Attribute> etalonTemplate = new ArrayList<>();
        etalonTemplate.add(Pkcs11AttributeFactory.getInstance().makeAttribute(Pkcs11AttributeType.CKA_KEY_TYPE, Pkcs11KeyType.CKK_RSA));
        etalonTemplate.add(Pkcs11AttributeFactory.getInstance().makeAttribute(Pkcs11AttributeType.CKA_CLASS, Pkcs11ObjectClass.CKO_PRIVATE_KEY));

        for (Pkcs11Attribute etalon : etalonTemplate) {
            boolean found = false;
            for (Pkcs11Attribute attribute : template) {
                if (attribute.getType().equals(etalon.getType()) &&
                        attribute.getValue().equals(etalon.getValue())) {
                    found = true;
                    break;
                }
            }
            assertTrue("template not found", found);
        }
    }

    @Test
    public void testAllObjectClasses() {
        for (Class<? extends Pkcs11Object> objectClass : getFactory().getRegisteredObjectClasses()) {
            testObjectClass(objectClass);
        }
    }

    private void testObjectClass(@NonNull Class<? extends Pkcs11Object> objectClass) {
        assertTrue(objectClass.toString(), getFactory().isObjectClassRegistered(objectClass));
        getFactory().makeTemplate(objectClass);
        getFactory().makeObject(objectClass, 0);
    }

    private Pkcs11ObjectFactory getFactory() {
        return Pkcs11ObjectFactory.getInstance();
    }


}