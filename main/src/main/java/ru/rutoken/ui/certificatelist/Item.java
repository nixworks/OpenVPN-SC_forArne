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

import ru.rutoken.pkcs11wrapper.Pkcs11Token;

public interface Item {
    public enum ItemType {
        TOKEN_ITEM, CERTIFICATE_ITEM
    }

    int getViewType();

    @NonNull
    View getView(LayoutInflater inflater, View convertView, ViewGroup parent);

    @NonNull
    Pkcs11Token getToken();
}
