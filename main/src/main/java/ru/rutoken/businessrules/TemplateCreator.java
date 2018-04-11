/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.businessrules;

import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11ObjectFactory;
import ru.rutoken.pkcs11wrapper.object.Pkcs11RsaPrivateKeyObject;

public class TemplateCreator {
    public static List<Pkcs11Attribute> createRsaPrivateKeyWithModulusAndExponentTemplate(byte[] modulus, byte[] pubExp) {
        final List<Pkcs11Attribute> template = Pkcs11ObjectFactory.getInstance().makeTemplate(Pkcs11RsaPrivateKeyObject.class);
        template.add(Pkcs11AttributeFactory.getInstance().makeAttribute(Pkcs11AttributeType.CKA_MODULUS, modulus));
        template.add(Pkcs11AttributeFactory.getInstance().makeAttribute(Pkcs11AttributeType.CKA_PUBLIC_EXPONENT, pubExp));
        return template;
    }
}
