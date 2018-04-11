/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package de.blinkt.openvpnsc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.security.cert.X509Certificate;
import java.text.ParseException;

import de.blinkt.openvpnsc.R;
import ru.rutoken.businessrules.LoadProgress;
import ru.rutoken.pkcs11.Pkcs11Fasade;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.ui.certificatelist.CertificateItem;
import ru.rutoken.ui.certificatelist.Item;
import ru.rutoken.ui.certificatelist.ItemStorage;
import ru.rutoken.utility.InnerHandler;

public class CertificateSelectActivity extends AppCompatActivity implements LoadProgress.LoadProgressListener {
    public static final String TOKEN_SERIAL = "TOKEN_SERIAL";
    public static final String TOKEN_CERT = "TOKEN_CERT";

    private ListView mList;
    private MenuItem mActionProgressItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_select_acitvity);

        try {
            setupUi();
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            setResult(RESULT_CANCELED, getIntent());
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ItemStorage.getInstance(this).getLoadProgressCounter().removeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.certificatelistmenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mActionProgressItem = menu.findItem(R.id.mActionProgress);
        ItemStorage.getInstance(this).getLoadProgressCounter().addListener(this);
        mActionProgressItem.setVisible(ItemStorage.getInstance(this).getLoadProgressCounter().isLoading());
        return super.onPrepareOptionsMenu(menu);
    }

    private void setupUi() {
        mList = findViewById(R.id.rt_list);
        mList.setAdapter(ItemStorage.getInstance(this).getAdapter());
        mList.setOnItemClickListener(this::onItemClicked);
    }

    private void onItemClicked(AdapterView<?> arg0, View arg1, int pos, long id) {
        showProgressBar(true);

        Item item = (Item) mList.getItemAtPosition(pos);
        if (item instanceof CertificateItem) {
            CertificateItem certItem = (CertificateItem) item;
            X509Certificate tokenAuthCert = certItem.getCertificate();

            Handler handler = new ExecutionHandler(this);
            Pkcs11Fasade.getInstance().getTokenThreadPool().execute(certItem.getToken(), () -> {
                try {
                    Message msg = handler.obtainMessage(CONNECTION_PARAMS_GOT);
                    Bundle bundle = new Bundle();
                    bundle.putString(TOKEN_SERIAL, certItem.getToken().getTokenInfo().getSerialNumber());
                    bundle.putSerializable(TOKEN_CERT, tokenAuthCert);
                    msg.setData(bundle);
                    msg.sendToTarget();
                } catch (ParseException | Pkcs11Exception e) {
                    handler.obtainMessage(CONNECTION_PARAMS_ERROR, e.getMessage()).sendToTarget();
                }
            });
        }
    }

    @Override
    @MainThread
    public void onProgressStateChanged(boolean started) {
        mActionProgressItem.setVisible(started);
    }

    private static class ExecutionHandler extends InnerHandler<CertificateSelectActivity> {
        ExecutionHandler(@NonNull CertificateSelectActivity activity) {
            super(activity);
        }

        @MainThread
        @Override
        protected void onHandleMessage(@NonNull CertificateSelectActivity activity, Message msg) {
            switch (msg.what) {
                case CONNECTION_PARAMS_GOT:
                    activity.setResult(RESULT_OK, new Intent().
                            putExtra(TOKEN_SERIAL, msg.getData().getString(TOKEN_SERIAL)).
                            putExtra(TOKEN_CERT, msg.getData().getSerializable(TOKEN_CERT)));
                    activity.finish();
                    break;
                case CONNECTION_PARAMS_ERROR:
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "ERROR:" + msg.obj, Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }
            activity.showProgressBar(false);
        }
    }

    private void showProgressBar(boolean value) {
        if (value) {
            findViewById(R.id.rt_list).setEnabled(false);
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
            findViewById(R.id.rt_list).setEnabled(true);
        }
    }

    private static final int CONNECTION_PARAMS_GOT = 0;
    private static final int CONNECTION_PARAMS_ERROR = 1;
}
