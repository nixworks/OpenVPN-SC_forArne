/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_SLOT_INFO;

public class Pkcs11Slot {
    public Pkcs11Slot(@NonNull Pkcs11Module module, long id) {
        mModule = Objects.requireNonNull(module);
        mId = id;
        mToken = new Pkcs11Token(this);
    }

    @NonNull
    public Pkcs11Module getModule() {
        return mModule;
    }

    public long getId() {
        return mId;
    }

    @NonNull
    public Pkcs11SlotInfo getSlotInfo() throws Pkcs11Exception {
        final CK_SLOT_INFO slotInfo = new CK_SLOT_INFO();
        final NativeLong r = mModule.getPkcs11().C_GetSlotInfo(new NativeLong(mId), slotInfo);
        Pkcs11Exception.throwIfNotOk(r, "can not get slot info");
        return new Pkcs11SlotInfo(slotInfo);
    }

    @NonNull
    public Pkcs11Token getToken() {
        return mToken;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Pkcs11Slot) {
            Pkcs11Slot other = (Pkcs11Slot) otherObject;
            return (this == other) || Objects.equals(mModule, other.mModule) && mId == other.mId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mModule, mId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @NonNull
    private final Pkcs11Module mModule;
    private final long mId;
    private final Pkcs11Token mToken;
}
