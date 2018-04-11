/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11;

import android.support.annotation.NonNull;

import com.sun.jna.Native;

import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.pkcs11jna.Pkcs11;
import ru.rutoken.pkcs11wrapper.Pkcs11Module;

/**
 * Rutoken pkcs11 library accessor
 */
public class RtPkcs11Module implements Pkcs11Module {
    public RtPkcs11Module() {
        mLibrary = Native.loadLibrary("rtpkcs11ecp", Pkcs11.class);
    }

    @NonNull
    @Override
    public Pkcs11 getPkcs11() {
        return mLibrary;
    }

    private final Pkcs11 mLibrary;
}
