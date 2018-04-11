/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.slotevent;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import ru.rutoken.pkcs11wrapper.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.Pkcs11SlotInfo;

public class Pkcs11SlotEvent {
    public Pkcs11SlotEvent(@NonNull Pkcs11Slot slot, @NonNull Pkcs11SlotInfo slotInfo) {
        mSlot = slot;
        mEventSlotInfo = slotInfo;
    }

    @NonNull
    public Pkcs11Slot getSlot() {
        return mSlot;
    }

    @NonNull
    public Pkcs11SlotInfo getEventSlotInfo() {
        return mEventSlotInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @NonNull
    private final Pkcs11Slot mSlot;
    @NonNull
    private final Pkcs11SlotInfo mEventSlotInfo;
}
