/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.utility;

import android.support.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.io.pem.PemWriter;
import org.bouncycastle.util.io.pem.PemObject;

import ru.rutoken.businessrules.exception.BusinessRuleException;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.Pkcs11X509PublicKeyCertificateObject;

public class CertificateConverter {
    public static X509Certificate X509CertificateFromDer(byte[] derEncodedCert) throws CertificateException {
        Certificate cert = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(derEncodedCert));
        if (!(cert instanceof X509Certificate))
            throw new CertificateException("Invalid certificate type");
        return (X509Certificate) cert;
    }

    @NonNull
    public static X509Certificate X509CertificateFromPkcs11Certificate(@NonNull Pkcs11Session session,
                                                                       @NonNull Pkcs11X509PublicKeyCertificateObject certificate)
            throws CertificateException, Pkcs11Exception {
        return X509CertificateFromDer(certificate.getValueAttributeValue(session).getByteArrayValue());
    }

    public static String X509CertificateToPEMString(X509Certificate cert) throws CertificateException {
        StringWriter writer = new StringWriter();
        try (PemWriter pw = new PemWriter(writer)) {
            pw.writeObject(new PemObject("CERTIFICATE", cert.getEncoded()));
        } catch (IOException e) {
            throw new CertificateException("Can not convert certificate");
        }
        return writer.toString();
    }

    @NonNull
    public static List<X509Certificate> X509CertificatesFromPkcs11Certificates(@NonNull Pkcs11Session session,
                                                                               @NonNull List<Pkcs11CertificateObject> certificates)
            throws CertificateException, Pkcs11Exception {
        List<X509Certificate> x509Certificates = new ArrayList<>();
        for (Pkcs11CertificateObject certificate : certificates) {
            if (certificate instanceof Pkcs11X509PublicKeyCertificateObject) {
                X509Certificate x509Certificate = X509CertificateFromPkcs11Certificate(session, (Pkcs11X509PublicKeyCertificateObject) certificate);
                x509Certificates.add(x509Certificate);
            }
        }
        return x509Certificates;
    }
}
