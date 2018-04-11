/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11KeyType implements Supplier<Long> {
    CKK_RSA(Pkcs11Constants.CKK_RSA),
    CKK_DSA(Pkcs11Constants.CKK_DSA),
    CKK_DH(Pkcs11Constants.CKK_DH),
    CKK_ECDSA(Pkcs11Constants.CKK_ECDSA),
    CKK_EC(Pkcs11Constants.CKK_EC),
    CKK_X9_42_DH(Pkcs11Constants.CKK_X9_42_DH),
    CKK_KEA(Pkcs11Constants.CKK_KEA),
    CKK_GENERIC_SECRET(Pkcs11Constants.CKK_GENERIC_SECRET),
    CKK_RC2(Pkcs11Constants.CKK_RC2),
    CKK_RC4(Pkcs11Constants.CKK_RC4),
    CKK_DES(Pkcs11Constants.CKK_DES),
    CKK_DES2(Pkcs11Constants.CKK_DES2),
    CKK_DES3(Pkcs11Constants.CKK_DES3),
    CKK_CAST(Pkcs11Constants.CKK_CAST),
    CKK_CAST3(Pkcs11Constants.CKK_CAST3),
    CKK_CAST5(Pkcs11Constants.CKK_CAST5),
    CKK_CAST128(Pkcs11Constants.CKK_CAST128),
    CKK_RC5(Pkcs11Constants.CKK_RC5),
    CKK_IDEA(Pkcs11Constants.CKK_IDEA),
    CKK_SKIPJACK(Pkcs11Constants.CKK_SKIPJACK),
    CKK_BATON(Pkcs11Constants.CKK_BATON),
    CKK_JUNIPER(Pkcs11Constants.CKK_JUNIPER),
    CKK_CDMF(Pkcs11Constants.CKK_CDMF),
    CKK_AES(Pkcs11Constants.CKK_AES),
    CKK_BLOWFISH(Pkcs11Constants.CKK_BLOWFISH),
    CKK_TWOFISH(Pkcs11Constants.CKK_TWOFISH),
    CKK_SECURID(Pkcs11Constants.CKK_SECURID),
    CKK_HOTP(Pkcs11Constants.CKK_HOTP),
    CKK_ACTI(Pkcs11Constants.CKK_ACTI),
    CKK_CAMELLIA(Pkcs11Constants.CKK_CAMELLIA),
    CKK_ARIA(Pkcs11Constants.CKK_ARIA),
    //CKK_SHA512_224_HMAC(Pkcs11Constants.CKK_SHA512_224_HMAC),
    //CKK_SHA512_256_HMAC(Pkcs11Constants.CKK_SHA512_256_HMAC),
    //CKK_SHA512_T_HMAC(Pkcs11Constants.CKK_SHA512_T_HMAC),
    CKK_VENDOR_DEFINED(Pkcs11Constants.CKK_VENDOR_DEFINED);


    public static Pkcs11KeyType fromValue(long value) {
        for (Pkcs11KeyType val : values()) {
            if (val.getValue() == value)
                return val;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Long get() { return getValue(); }

    public long getValue() {
        return mValue;
    }

    Pkcs11KeyType(NativeLong value) {
        mValue = value.longValue();
    }

    private final long mValue;
}
