/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.tokenmanager;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import ru.rutoken.pkcs11wrapper.Pkcs11Token;

/**
 * Stores tokens, useful to check presence of token depending on received events.
 * Token presence state may differ from actual state in Pkcs11TokenManager or some other TokenListener,
 * that may have not received current Pkcs11SlotEvent yet, or already received next ones.
 * This class is designed to be a member of some TokenListener.
 */
public class TokenStorage {
    public boolean contains(@NonNull Pkcs11Token token) {
        return mTokens.contains(token);
    }

    public void add(@NonNull Pkcs11Token token) {
        mTokens.add(token);
    }

    public void remove(@NonNull Pkcs11Token token) {
        mTokens.remove(token);
    }

    private final Set<Pkcs11Token> mTokens = new HashSet<>();
}
