/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.businessrules;

import android.support.annotation.NonNull;
import android.util.Log;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import ru.rutoken.businessrules.exception.BusinessRuleException;
import ru.rutoken.pkcs11.session.SessionWrapper;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.Pkcs11RsaPrivateKeyObject;

public class RsaKeyFinder {
    @NonNull
    public static Pkcs11RsaPrivateKeyObject getPkcs11RsaPrivateKeyByCertificate(
            @NonNull SessionWrapper session, @NonNull X509Certificate certificate) throws BusinessRuleException {
        List<Pkcs11Attribute> template = getRsaPrivateKeyTemplate(certificate);
        try {
            session.login();
            List<Pkcs11RsaPrivateKeyObject> rsaKeys = session.getSession().getObjectManager().findObjectsAtOnce(Pkcs11RsaPrivateKeyObject.class, template);
            if (rsaKeys.size() == 0)
                throw new BusinessRuleException("No RSA private keys found");
            if (rsaKeys.size() != 1)
                Log.w(Pkcs11RsaPrivateKeyObject.class.getName(), "Multiple private keys found");
            return rsaKeys.get(0);
        } catch (Pkcs11Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    private static List<Pkcs11Attribute> getRsaPrivateKeyTemplate(@NonNull X509Certificate certificate) throws BusinessRuleException {
        PublicKey publicKey = certificate.getPublicKey();
        if (!(publicKey instanceof RSAPublicKey)) {
            throw new BusinessRuleException("Public key extracted from certificate is not an RSA key");
        }
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        BigInteger modulus = rsaPublicKey.getModulus();
        BigInteger publicExp = rsaPublicKey.getPublicExponent();
        if (modulus.signum() < 0 && publicExp.signum() < 0) {
            throw new BusinessRuleException("Modulus or public exponent is less than zero");
        }

        return TemplateCreator.createRsaPrivateKeyWithModulusAndExponentTemplate(
                dropPrecedingZeros(modulus.toByteArray()), publicExp.toByteArray());
    }

    private static byte[] dropPrecedingZeros(@NonNull byte[] array) {
        if (array.length == 0)
            return array;
        int numPrecedingZeros = 0;
        for (byte elem: array) {
            if (elem == 0)
                ++numPrecedingZeros;
            else
                break;
        }
        return Arrays.copyOfRange(array, numPrecedingZeros, array.length);
    }
}
