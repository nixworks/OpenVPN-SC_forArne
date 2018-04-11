/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package de.blinkt.openvpnsc.views;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.cert.X509Certificate;

import de.blinkt.openvpnsc.R;
import de.blinkt.openvpnsc.activities.CertificateSelectActivity;
import ru.rutoken.utility.ServicePackageHelper;
import de.blinkt.openvpnsc.fragments.ServiceInstallDialog;
import ru.rutoken.utility.X509Utility;

import static de.blinkt.openvpnsc.activities.CertificateSelectActivity.TOKEN_CERT;
import static de.blinkt.openvpnsc.activities.CertificateSelectActivity.TOKEN_SERIAL;
import static de.blinkt.openvpnsc.fragments.Settings_Basic.CERTIFICATE_SELECT_REQUEST;

public class CertificateSelectLayout extends LinearLayout implements View.OnClickListener {

    private TextView mDataView;
    private Button mSelectButton;
    private TextView mDataDetails;
    private Button mClearButton;
    private Fragment mBaseFragment;

    private X509Certificate mTokenAuthCert;
    private String mTokenSerial;

    public CertificateSelectLayout(Context context, AttributeSet attrset) {
        super(context, attrset);

        TypedArray ta = context.obtainStyledAttributes(attrset, R.styleable.FileSelectLayout);

        setupViews(ta.getString(R.styleable.FileSelectLayout_fileTitle));

        ta.recycle();
    }

    public void parseResponse(Intent data) {
        mTokenSerial = data.getStringExtra(TOKEN_SERIAL);
        mTokenAuthCert = (X509Certificate) data.getSerializableExtra(TOKEN_CERT);
        mClearButton.setVisibility(VISIBLE);
        mDataView.setText(X509Utility.getCertificateCommonName(mTokenAuthCert));
    }

    @Override
    public void onClick(View v) {
        if (v == mSelectButton) {
            if (!ServicePackageHelper.isInstalledRutokenService(getContext())) {
                ServiceInstallDialog.newInstance(ServiceInstallDialog.DialogType.INSTALL_ON_CERTIFICATE_LIST)
                        .show(mBaseFragment.getFragmentManager(), "rutoken_service_installation_dialog");
                return;
            }
            startCertificateSelectActivity();
        } else if (v == mClearButton) {
            clearData(getContext());
        }
    }

    public void setBaseFragment(Fragment baseFragment) {
        this.mBaseFragment = baseFragment;
    }

    public X509Certificate getTokenAuthCert() {
        return mTokenAuthCert;
    }

    public String getTokenSerial() {
        return mTokenSerial;
    }

    public void setTokenAuthCert(X509Certificate tokenAuthCert) {
        mTokenAuthCert = tokenAuthCert;
        if (tokenAuthCert != null)
            mDataView.setText(X509Utility.getCertificateCommonName(mTokenAuthCert));
        mClearButton.setVisibility(VISIBLE);
    }

    public void setTokenSerial(String tokenSerial) {
        mTokenSerial = tokenSerial;
    }

    private void startCertificateSelectActivity() {
        Intent startFC = new Intent(getContext(), CertificateSelectActivity.class);
        mBaseFragment.startActivityForResult(startFC, CERTIFICATE_SELECT_REQUEST);
    }

    private void clearData(Context c) {
        mTokenAuthCert = null;
        mTokenSerial = null;
        mDataView.setText(c.getString(R.string.no_data));
        mDataDetails.setText("");
        mClearButton.setVisibility(GONE);
    }


    private void setupViews(String title) {
        inflate(getContext(), R.layout.file_select, this);

        TextView tView = findViewById(R.id.file_title);
        tView.setText(title);

        mDataView = findViewById(R.id.file_selected_item);

        mDataDetails = findViewById(R.id.file_selected_description);
        mSelectButton = findViewById(R.id.file_select_button);
        mSelectButton.setOnClickListener(this);

        mClearButton = findViewById(R.id.file_clear_button);
        mClearButton.setOnClickListener(this);
        mClearButton.setVisibility(GONE);
    }
}
