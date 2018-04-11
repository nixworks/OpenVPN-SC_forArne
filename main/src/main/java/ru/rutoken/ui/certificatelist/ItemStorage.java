/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.ui.certificatelist;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.businessrules.LoadProgress;
import ru.rutoken.pkcs11.Pkcs11Fasade;
import ru.rutoken.pkcs11.tokenmanager.Pkcs11TokenManager;
import ru.rutoken.pkcs11.tokenmanager.CurrentThreadTokenListener;

public class ItemStorage {
    public static ItemStorage getInstance(Context context) throws ExceptionInInitializerError {
        if (null == sInstance.get()) {
            synchronized (ItemStorage.class) {
                if (null == sInstance.get()) {
                    sInstance.set(new ItemStorage(context));
                }
            }
        }
        return sInstance.get();
    }

    @NonNull
    public ItemArrayAdapter getAdapter() {
        return mAdapter;
    }

    private ItemStorage(Context context) {
        mAdapter = new ItemArrayAdapter(context, new ArrayList<>());
        final ItemModelUpdater modelUpdater = new ItemModelUpdater(mAdapter, mLoadProgress);
        final Pkcs11TokenManager tokenManager = Pkcs11Fasade.getInstance().getTokenManager();
        tokenManager.addTokenListener(new CurrentThreadTokenListener(modelUpdater));
    }

    private static final AtomicReference<ItemStorage> sInstance = new AtomicReference<>();
    private final ItemArrayAdapter mAdapter;

    public LoadProgress getLoadProgressCounter() {
        return mLoadProgress;
    }

    private final LoadProgress mLoadProgress = new LoadProgress();

}
