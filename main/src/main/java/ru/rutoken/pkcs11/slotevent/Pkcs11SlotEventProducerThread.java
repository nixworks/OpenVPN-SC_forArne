/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.slotevent;

import android.support.annotation.NonNull;

import java.util.Queue;

import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Module;
import ru.rutoken.pkcs11wrapper.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.Pkcs11SlotInfo;

/**
 * Waits for slot events and puts then into a queue
 */
public class Pkcs11SlotEventProducerThread extends Thread {
    public Pkcs11SlotEventProducerThread(@NonNull Pkcs11Module module, @NonNull Queue<Pkcs11SlotEvent> queue) {
        setName(getClass().getSimpleName());
        mModule = module;
        mQueue = queue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                final Pkcs11Slot slot = mModule.waitForSlotEvent(false);
                mQueue.add(SlotEventLogger.log("waitForSlotEvent result", new Pkcs11SlotEvent(slot, slot.getSlotInfo())));
            } catch (Pkcs11Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final Pkcs11Module mModule;
    private final Queue<Pkcs11SlotEvent> mQueue;
}
