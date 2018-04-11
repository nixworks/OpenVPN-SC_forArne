/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11jna.CK_C_INITIALIZE_ARGS;
import ru.rutoken.pkcs11jna.Pkcs11;
import ru.rutoken.pkcs11jna.Pkcs11Constants;

/**
 * Defines top live PKCS11 methods and Gives access to underlying Pkcs11 library
 */
public interface Pkcs11Module {
    /**
     * @return JNA pkcs11 library
     */
    @NonNull
    Pkcs11 getPkcs11();

    @NonNull
    default List<Pkcs11Slot> getSlotList(boolean tokenPresent) throws Pkcs11Exception {
        @NonNull NativeLong slotIds[] = new NativeLong[0];
        @NonNull NativeLong res = Pkcs11Constants.CKR_OK;
        do {
            final NativeLongByReference slotCount = new NativeLongByReference();
            final NativeLong r = getPkcs11().C_GetSlotList(tokenPresent, null, slotCount);
            Pkcs11Exception.throwIfNotOk(r, "can not get slot count");

            final int count = slotCount.getValue().intValue();
            if (count > 0) {
                slotIds = new NativeLong[count];
                res = getPkcs11().C_GetSlotList(tokenPresent, slotIds, slotCount);
            }
        } while (res.equals(Pkcs11Constants.CKR_BUFFER_TOO_SMALL));
        Pkcs11Exception.throwIfNotOk(res, "can not get slot list");

        final List<Pkcs11Slot> slots = new ArrayList<>();
        for (NativeLong id : slotIds) {
            slots.add(new Pkcs11Slot(this, id.longValue()));
        }
        return slots;
    }

    @NonNull
    default Pkcs11Slot waitForSlotEvent(boolean dontBlock) throws Pkcs11Exception {
        final NativeLong flags = dontBlock ? Pkcs11Constants.CKF_DONT_BLOCK : new NativeLong();
        final NativeLongByReference id = new NativeLongByReference();
        final NativeLong r = getPkcs11().C_WaitForSlotEvent(flags, id, null);
        Pkcs11Exception.throwIfNotOk(r, "waitForSlotEvent failed");

        return new Pkcs11Slot(this, id.getValue().longValue());
    }

    default void initialize(@NonNull Pkcs11InitializeParameters parameters) throws Pkcs11Exception {
        //TODO mutex callbacks
        final CK_C_INITIALIZE_ARGS nativeArgs = new CK_C_INITIALIZE_ARGS(null, null,
                null, null, new NativeLong(parameters.getFlags()), null);
        final NativeLong r = getPkcs11().C_Initialize(nativeArgs);
        Pkcs11Exception.throwIfNotOk(r, "can not initialize module");
    }

    default void finalizeModule() throws Pkcs11Exception {
        final NativeLong r = getPkcs11().C_Finalize(null);
        Pkcs11Exception.throwIfNotOk(r, "can not finalize module");
    }
}
