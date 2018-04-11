/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.ui.certificatelist;


import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.cert.X509Certificate;

import de.blinkt.openvpnsc.R;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;
import ru.rutoken.utility.X509Utility;


public class CertificateItem implements Item {

    private final X509Certificate mCertificate;
    private final Pkcs11Token mToken;
    private final String mLabel;

    CertificateItem(Pkcs11Token token, X509Certificate certificate) {
        mToken = token;
        mCertificate = certificate;
        mLabel = X509Utility.getCertificateCommonName(certificate);
    }

    @Override
    public int getViewType() {
        return ItemType.CERTIFICATE_ITEM.ordinal();
    }

    @NonNull
    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.certificate_item, parent);
        } else {
            view = convertView;
        }

        TextView text = view.findViewById(R.id.cert_label);
        text.setText(mLabel);

        return view;
    }

    @NonNull
    @Override
    public Pkcs11Token getToken() {
        return mToken;
    }

    @NonNull
    public X509Certificate getCertificate() {
        return mCertificate;
    }
}
