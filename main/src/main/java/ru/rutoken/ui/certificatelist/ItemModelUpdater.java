/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.ui.certificatelist;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ru.rutoken.businessrules.CertificateEnumerator;
import ru.rutoken.businessrules.LoadProgress;
import ru.rutoken.businessrules.exception.BusinessRuleException;
import ru.rutoken.pkcs11.Pkcs11Fasade;
import ru.rutoken.pkcs11.tokenmanager.Pkcs11TokenManager;
import ru.rutoken.pkcs11.session.SessionWrapper;
import ru.rutoken.pkcs11.tokenmanager.TokenStorage;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;
import ru.rutoken.utility.InnerHandler;

import static ru.rutoken.utility.CertificateConverter.X509CertificatesFromPkcs11Certificates;

/**
 * Constructs items asynchronously
 */
public class ItemModelUpdater implements Pkcs11TokenManager.TokenListener {
    public ItemModelUpdater(@NonNull ArrayAdapter<Item> adapter, LoadProgress loadProgress) {
        mAdapter = adapter;
        mLoadProgress = loadProgress;
    }

    @MainThread
    @Override
    public void onTokenAdded(@NonNull Pkcs11Token token) {
        mLoadProgress.startProgress();
        mTokenStorage.add(token);
        Handler handler = new ExecutionHandler(this);
        Pkcs11Fasade.getInstance().getTokenThreadPool().execute(token,
                () -> handler.obtainMessage(TOKEN_ITEMS_READY, new ItemsTransfer(token, makeItemsForToken(token))).sendToTarget());
    }

    @MainThread
    @Override
    public void onTokenRemoved(@NonNull Pkcs11Token token) {
        mTokenStorage.remove(token);
        try (NotifyGuard guard = createNotifyGuard()) {
            for (int i = 0; i < mAdapter.getCount(); ) {
                Item item = mAdapter.getItem(i);
                if (null != item && item.getToken().equals(token)) {
                    mAdapter.remove(item);
                } else {
                    ++i;
                }
            }
        }
    }

    @NonNull
    private static List<Item> makeItemsForToken(@NonNull Pkcs11Token token) {
        List<Item> items = new ArrayList<>();
        try {
            items.add(new TokenItem(token));
            try (SessionWrapper session = new SessionWrapper.Builder(token).build()) {
                CertificateEnumerator enumerator = new CertificateEnumerator(session.getSession().getObjectManager());
                for (X509Certificate certificate : X509CertificatesFromPkcs11Certificates(session.getSession(), enumerator.getCertificates())) {
                    if (certificate.getPublicKey().getAlgorithm().equals("RSA"))
                        items.add(new CertificateItem(token, certificate));
                }
            } catch (BusinessRuleException | CertificateException | Pkcs11Exception e) {
                e.printStackTrace();
            }
        } catch (Pkcs11Exception | ParseException e) {
            e.printStackTrace();
        }
        return items;
    }

    @NonNull
    private NotifyGuard createNotifyGuard() {
        return new NotifyGuard();
    }

    private static class ExecutionHandler extends InnerHandler<ItemModelUpdater> {
        ExecutionHandler(@NonNull ItemModelUpdater itemModelUpdater) {
            super(itemModelUpdater);
        }

        @MainThread
        @Override
        protected void onHandleMessage(@NonNull ItemModelUpdater itemModelUpdater, Message msg) {
            switch (msg.what) {
                case TOKEN_ITEMS_READY:
                    if (msg.obj instanceof ItemsTransfer) {
                        ItemsTransfer transfer = (ItemsTransfer) msg.obj;
                        // check if token was not removed while async calculation was made
                        if (itemModelUpdater.mTokenStorage.contains(transfer.token)) {
                            try (NotifyGuard guard = itemModelUpdater.createNotifyGuard()) {
                                itemModelUpdater.mAdapter.addAll(transfer.items);
                            }
                        } else {
                            Log.d(getClass().getName(), "protected from adding items of removed token");
                        }
                    }
                    itemModelUpdater.mLoadProgress.stopProgress();
                    break;
            }
        }
    }

    private static class ItemsTransfer {
        public ItemsTransfer(@NonNull Pkcs11Token token, @NonNull List<Item> items) {
            this.token = token;
            this.items = items;
        }

        public final Pkcs11Token token;
        public final List<Item> items;
    }

    private class NotifyGuard implements AutoCloseable {
        NotifyGuard() {
            mAdapter.setNotifyOnChange(false);
        }

        @Override
        public void close() {
            mAdapter.notifyDataSetChanged();
        }
    }

    private static final int TOKEN_ITEMS_READY = 0;
    private final ArrayAdapter<Item> mAdapter;
    private final TokenStorage mTokenStorage = new TokenStorage();
    private LoadProgress mLoadProgress;
}
