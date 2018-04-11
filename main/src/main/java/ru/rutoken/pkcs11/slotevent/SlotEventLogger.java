/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.slotevent;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Helper class for logging
 */
public class SlotEventLogger {
    @NonNull
    public static Pkcs11SlotEvent log(@NonNull String description, @NonNull Pkcs11SlotEvent event) {
        Log.d(SlotEventLogger.class.getName() + " [Slot event]",description + " slotId=" + event.getSlot().getId() +
                ", tokenPresent=" + event.getEventSlotInfo().isTokenPresent() + " " + event);
        return event;
    }
}
