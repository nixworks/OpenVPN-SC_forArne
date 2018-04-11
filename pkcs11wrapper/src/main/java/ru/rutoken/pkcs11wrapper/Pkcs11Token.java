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

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_MECHANISM_INFO;
import ru.rutoken.pkcs11jna.CK_TOKEN_INFO;
import ru.rutoken.pkcs11jna.Pkcs11;
import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ReturnValue;

public class Pkcs11Token {
    public Pkcs11Token(@NonNull Pkcs11Slot slot) {
        mSlot = Objects.requireNonNull(slot);
    }

    @NonNull
    public Pkcs11Slot getSlot() {
        return mSlot;
    }

    public long getSlotId() {
        return mSlot.getId();
    }

    @NonNull
    public Pkcs11TokenInfo getTokenInfo() throws Pkcs11Exception, ParseException {
        final CK_TOKEN_INFO tokenInfo = new CK_TOKEN_INFO();
        final NativeLong r = mSlot.getModule().getPkcs11().C_GetTokenInfo(new NativeLong(getSlotId()), tokenInfo);
        Pkcs11Exception.throwIfNotOk(r, "can not get token info");
        return new Pkcs11TokenInfo(tokenInfo);
    }

    @NonNull
    public List<Pkcs11MechanismType> getMechanismList() throws Pkcs11Exception {
        @NonNull NativeLong mechanisms[] = new NativeLong[0];
        @NonNull Pkcs11ReturnValue res = Pkcs11ReturnValue.CKR_OK;
        do {
            final NativeLongByReference mechanismCount = new NativeLongByReference();
            NativeLong r = mSlot.getModule().getPkcs11().C_GetMechanismList(new NativeLong(getSlotId()), null, mechanismCount);
            Pkcs11Exception.throwIfNotOk(r, "can not get mechanism count");

            int count = mechanismCount.getValue().intValue();
            if (count > 0) {
                mechanisms = new NativeLong[count];
                r = mSlot.getModule().getPkcs11().C_GetMechanismList(new NativeLong(getSlotId()), mechanisms, mechanismCount);
                res = Pkcs11ReturnValue.fromValue(r.longValue());
            }
        } while (res.equals(Pkcs11ReturnValue.CKR_BUFFER_TOO_SMALL));
        Pkcs11Exception.throwIfNotOk(res, "can not get mechanism list");

        List<Pkcs11MechanismType> mechanismTypes = new ArrayList<>();
        for (NativeLong mechanism : mechanisms) {
            mechanismTypes.add(Pkcs11MechanismType.fromValue(mechanism.longValue()));
        }
        return mechanismTypes;
    }

    @NonNull
    public Pkcs11MechanismInfo getMechanismInfo(Pkcs11MechanismType type) throws Pkcs11Exception {
        final CK_MECHANISM_INFO mechanismInfo = new CK_MECHANISM_INFO();
        final NativeLong r = mSlot.getModule().getPkcs11().C_GetMechanismInfo(new NativeLong(getSlotId()), new NativeLong(type.getValue()), mechanismInfo);
        Pkcs11Exception.throwIfNotOk(r, "can not get mechanism info");
        return new Pkcs11MechanismInfo(mechanismInfo);
    }

    @NonNull
    public Pkcs11Session openSession(boolean serialSession, boolean rwSession) throws Pkcs11Exception {
        long flags = 0;
        flags |= serialSession ? Pkcs11Constants.CKF_SERIAL_SESSION.longValue() : 0L;
        flags |= rwSession ? Pkcs11Constants.CKF_RW_SESSION.longValue() : 0L;

        final NativeLongByReference sessionHandle = new NativeLongByReference();
        final NativeLong r = mSlot.getModule().getPkcs11().C_OpenSession(
                new NativeLong(getSlotId()), new NativeLong(flags), null, null, sessionHandle);
        Pkcs11Exception.throwIfNotOk(r, "can not open session");
        return new Pkcs11Session(this, sessionHandle.getValue().longValue());
    }

    public void closeAllSessions() throws Pkcs11Exception {
        mSlot.getModule().getPkcs11().C_CloseAllSessions(new NativeLong(getSlotId()));
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Pkcs11Token) {
            Pkcs11Token other = (Pkcs11Token) otherObject;
            return (this == other) || Objects.equals(mSlot, other.mSlot);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mSlot);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private final Pkcs11Slot mSlot;
}
