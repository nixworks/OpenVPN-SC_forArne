/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.businessrules;

import android.support.annotation.NonNull;

import java.security.cert.X509Certificate;

import ru.rutoken.businessrules.exception.BusinessRuleException;
import ru.rutoken.pkcs11.session.SessionWrapper;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.object.Pkcs11RsaPrivateKeyObject;

public class RsaSigner {
    public RsaSigner(@NonNull SessionWrapper session, @NonNull X509Certificate cert) {
        mSession = session;
        mCertForKey = cert;
    }

    public byte[] signData(byte[] data) throws BusinessRuleException {
        try {
             mSession.login();
             Pkcs11RsaPrivateKeyObject rsaPrivKey = RsaKeyFinder.getPkcs11RsaPrivateKeyByCertificate(mSession, mCertForKey);
             return mSession.getSession().getSignVerifyManager().signAtOnce(data, MAX_SIGNATURE_LENGTH, makeRsaSignMechanism(), rsaPrivKey);
        } catch (Pkcs11Exception e) {
            throw new BusinessRuleException(e.getMessage());
        }
    }

    private Pkcs11Mechanism makeRsaSignMechanism() {
        return new Pkcs11Mechanism(Pkcs11MechanismType.CKM_RSA_PKCS);
    }

    /**
     * Preallocated signature length, for speed optimisation
     */
    private static final int MAX_SIGNATURE_LENGTH = 2048;
    private final X509Certificate mCertForKey;
    private final SessionWrapper mSession;
}
