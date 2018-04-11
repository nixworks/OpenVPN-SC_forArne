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

import java.text.ParseException;

import de.blinkt.openvpnsc.R;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;

public class TokenItem implements Item {
    private final Pkcs11Token mToken;
    private final String mLabel;

    TokenItem(@NonNull Pkcs11Token token) throws Pkcs11Exception, ParseException {
        mToken = token;
        mLabel = token.getTokenInfo().getLabel();
    }

    @Override
    public int getViewType() {
        return ItemType.TOKEN_ITEM.ordinal();
    }

    @NonNull
    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.token_item, parent);
        } else {
            view = convertView;
        }

        TextView text = view.findViewById(R.id.token_label);
        text.setText(mLabel);

        return view;
    }

    @NonNull
    @Override
    public Pkcs11Token getToken() {
        return mToken;
    }
}
