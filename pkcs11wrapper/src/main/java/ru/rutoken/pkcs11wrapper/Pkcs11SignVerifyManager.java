/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;

import java.util.Arrays;
import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_MECHANISM;
import ru.rutoken.pkcs11wrapper.object.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.object.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.Pkcs11RsaPrivateKeyObject;

/**
 * Signing and MACing functions
 */
public class Pkcs11SignVerifyManager {
    Pkcs11SignVerifyManager(@NonNull Pkcs11Session session) {
        mSession = Objects.requireNonNull(session);
        mModule = session.getModule();
    }

    public void signInit(Pkcs11Mechanism mechanism, Pkcs11PrivateKeyObject key) throws Pkcs11Exception {
        final NativeLong rv = mModule.getPkcs11().C_SignInit(new NativeLong(mSession.getSessionHandle()),
                mechanism.getMechanism(), new NativeLong(key.getHandle()));
        Pkcs11Exception.throwIfNotOk(rv, "Sign init failed");
    }

    public byte[] sign(byte[] data) throws Pkcs11Exception {
        final NativeLongByReference signatureLength = new NativeLongByReference();

        final NativeLong rv = mModule.getPkcs11().C_Sign(new NativeLong(mSession.getSessionHandle()),
                data, new NativeLong(data.length), null, signatureLength);
        Pkcs11Exception.throwIfNotOk(rv, "Sign can not get signature length");
        return sign(data, signatureLength.getValue().intValue());
    }

    /**
     * Version for optimisation, if signatureLength is known
     * @param data data to sign
     * @param signatureLength length of result signature
     */
    public byte[] sign(byte[] data, int signatureLength) throws Pkcs11Exception {
        final NativeLongByReference nativeSignatureLength = new NativeLongByReference(new NativeLong(signatureLength));
        final byte signature[] = new byte[signatureLength];
        final NativeLong r = mModule.getPkcs11().C_Sign(new NativeLong(mSession.getSessionHandle()), data,
                new NativeLong(data.length), signature, nativeSignatureLength);
        Pkcs11Exception.throwIfNotOk(r, "Sign failed");
        return Arrays.copyOf(signature, nativeSignatureLength.getValue().intValue());
    }

    public byte[] signAtOnce(byte[] data, Pkcs11Mechanism mechanism, Pkcs11PrivateKeyObject key) throws Pkcs11Exception {
        signInit(mechanism, key);
        return sign(data);
    }

    public byte[] signAtOnce(byte[] data, int signatureLength, Pkcs11Mechanism mechanism, Pkcs11PrivateKeyObject key) throws Pkcs11Exception {
        signInit(mechanism, key);
        return sign(data, signatureLength);
    }

    private final Pkcs11Session mSession;
    private final Pkcs11Module mModule;
}
