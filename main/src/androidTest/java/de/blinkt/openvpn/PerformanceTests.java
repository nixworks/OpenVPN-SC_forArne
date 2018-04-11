/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package de.blinkt.openvpn;


import android.support.test.runner.AndroidJUnit4;
import android.util.TimingLogger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import ru.rutoken.businessrules.CertificateEnumerator;
import ru.rutoken.businessrules.RsaKeyFinder;
import ru.rutoken.businessrules.TokenFinder;
import ru.rutoken.businessrules.exception.BusinessRuleException;
import ru.rutoken.pkcs11.Pkcs11Fasade;
import ru.rutoken.pkcs11.session.SessionWrapper;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.object.Pkcs11RsaPrivateKeyObject;
import ru.rutoken.utility.Optional;

import static ru.rutoken.utility.CertificateConverter.X509CertificatesFromPkcs11Certificates;


@RunWith(AndroidJUnit4.class)
public class PerformanceTests {
    // Replace this with your token serial
    private final String TOKEN_SERIAL = "383f5639        ";
    private final String DEFAULT_PIN = "12345678";

    @Test
    public void testSignData() throws Pkcs11Exception, BusinessRuleException, CertificateException  {
        final byte[] shortData = new byte[6];
        final byte[] longData = new byte[100];
        measureSignData(shortData, "Sign short data (" + shortData.length + " bytes)");
        measureSignData(longData, "Sign long data (" + longData.length + " bytes)");
    }

    private void measureSignData(byte[] dataToSign, String logLabel) throws Pkcs11Exception, BusinessRuleException, CertificateException {
        TimingLogger timings = new TimingLogger(getClass().getSimpleName(), logLabel);
        try {
            Pkcs11Fasade.getInstance();
            timings.addSplit("initialise");

            Optional<Pkcs11Token> token = TokenFinder.getBySerial(TOKEN_SERIAL);
            timings.addSplit("find token");
            if (!token.isPresent())
                throw new RuntimeException("token not found");
            try (SessionWrapper firstSession = new SessionWrapper.Builder(token.get()).build()) {
                timings.addSplit("open first session");
                CertificateEnumerator enumerator = new CertificateEnumerator(firstSession.getSession().getObjectManager());
                X509Certificate cert = null;
                for (X509Certificate certificate : X509CertificatesFromPkcs11Certificates(firstSession.getSession(), enumerator.getCertificates())) {
                    if (certificate.getPublicKey().getAlgorithm().equals("RSA"))
                        cert = certificate;
                }
                if (cert == null)
                    throw new RuntimeException("no certificate found");


                try (SessionWrapper secondSession = new SessionWrapper.Builder(token.get()).setPin(DEFAULT_PIN).build()) {
                    timings.addSplit("open second session");
                        secondSession.login();
                        timings.addSplit("login");
                        Pkcs11RsaPrivateKeyObject rsaPrivKey = RsaKeyFinder.getPkcs11RsaPrivateKeyByCertificate(secondSession, cert);
                        timings.addSplit("find private rsa key");
                        secondSession.getSession().getSignVerifyManager().signAtOnce(dataToSign, new Pkcs11Mechanism(Pkcs11MechanismType.CKM_RSA_PKCS), rsaPrivKey);
                        timings.addSplit("sign data");
                }
            }
        } finally {
            timings.dumpToLog();
        }
    }
}
