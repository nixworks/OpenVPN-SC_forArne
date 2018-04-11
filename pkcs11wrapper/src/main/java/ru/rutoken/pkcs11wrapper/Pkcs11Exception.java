/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ReturnValue;

/**
 * Represents error on pkcs11 level
 */
public class Pkcs11Exception extends Exception {
    public static void throwIfNotOk(NativeLong code, String message) throws Pkcs11Exception {
        throwIfNotOk(code.longValue(), message);
    }

    public static void throwIfNotOk(Pkcs11ReturnValue code, String message) throws Pkcs11Exception {
        throwIfNotOk(code.getValue(), message);
    }

    public static void throwIfNotOk(long code, String message) throws Pkcs11Exception {
        if (Pkcs11ReturnValue.CKR_OK.getValue() != code) {
            throw new Pkcs11Exception(code, message);
        }
    }

    long getCode() {
        return mCode;
    }

    private Pkcs11Exception(long code, String message) {
        super(generateMessage(code, message));
        mCode = code;
    }

    private static String generateMessage(long code, String message) {
        @NonNull String codeName;
        try {
            codeName = Pkcs11ReturnValue.fromValue(code).toString();
        } catch (IllegalArgumentException e) {
            codeName = "Unknown code";
        }
        return message + " [code: " + String.format("0x%08X", code) + ", name: " + codeName + "]";
    }

    private final long mCode;
}
