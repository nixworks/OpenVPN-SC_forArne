/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.businessrules;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.businessrules.exception.BusinessRuleException;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.Pkcs11ObjectManager;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ObjectClass;

public class CertificateEnumerator {
    public CertificateEnumerator(@NonNull Pkcs11ObjectManager objectManager) {
        mObjectManager = objectManager;
    }

    @NonNull
    public List<Pkcs11CertificateObject> getCertificates() throws BusinessRuleException {
        try {
            return mObjectManager.findObjectsAtOnce(Pkcs11CertificateObject.class);
        } catch (Pkcs11Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    private final Pkcs11ObjectManager mObjectManager;
}

