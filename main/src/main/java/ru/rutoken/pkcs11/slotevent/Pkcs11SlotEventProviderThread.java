/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.slotevent;


import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.Pkcs11SlotInfo;

/**
 * Takes slot events from queue and passes them to listeners
 */
public class Pkcs11SlotEventProviderThread extends Thread {
    public interface SlotEventListener {
        @WorkerThread
        void onPkcs11SlotEvent(@NonNull Pkcs11SlotEvent event);
    }

    public Pkcs11SlotEventProviderThread(@NonNull BlockingQueue<Pkcs11SlotEvent> queue) {
        setName(getClass().getSimpleName());
        mQueue = queue;
    }

    public Queue<Pkcs11SlotEvent> getQueue() {
        return mQueue;
    }

    @Override
    @WorkerThread
    public void run() {
        while (!isInterrupted()) {
            try {
                handleEvent(mQueue.take());
            } catch (InterruptedException e) {
                // no need to handle
                return;
            }
        }
    }

    public void addSlotEventListener(@NonNull SlotEventListener listener) {
        synchronized (mListeners) {
            if (!mListeners.contains(listener))
                mListeners.add(listener);
        }
    }

    /**
     * Creates event with opposite token present flag
     */
    @NonNull
    private static Pkcs11SlotEvent createMissedReinsertEvent(@NonNull Pkcs11SlotEvent previousEvent, @NonNull Pkcs11SlotEvent newEvent) {
        final Pkcs11SlotInfo previousInfo = previousEvent.getEventSlotInfo();
        if (previousInfo.isTokenPresent()) { // double insert
            final long flags = previousInfo.getFlags() & ~Pkcs11Constants.CKF_TOKEN_PRESENT.longValue();
            // create from previous info
            final Pkcs11SlotInfo info = new Pkcs11SlotInfo(previousInfo.getSlotDescription(), previousInfo.getManufacturerId(),
                    previousInfo.getHardwareVersion(), previousInfo.getFirmwareVersion(), flags);
            return new Pkcs11SlotEvent(previousEvent.getSlot(), info);
        } else { // double remove
            final Pkcs11SlotInfo newInfo = newEvent.getEventSlotInfo();
            final long flags = newInfo.getFlags() | Pkcs11Constants.CKF_TOKEN_PRESENT.longValue();
            // create from new info
            final Pkcs11SlotInfo info = new Pkcs11SlotInfo(newInfo.getSlotDescription(), newInfo.getManufacturerId(),
                    newInfo.getHardwareVersion(), newInfo.getFirmwareVersion(), flags);
            return new Pkcs11SlotEvent(newEvent.getSlot(), info);
        }
    }

    /**
     * Some events can be missed when token is reinserted rather fast. So we have to generate them.
     */
    @WorkerThread
    private void handleEvent(@NonNull Pkcs11SlotEvent event) {
        final Pkcs11SlotEvent previousEvent = mPreviosSlotEvent.get(event.getSlot());
        if (null != previousEvent &&
                previousEvent.getEventSlotInfo().isTokenPresent() == event.getEventSlotInfo().isTokenPresent()) {
            handleEvent(SlotEventLogger.log("generate missing event", createMissedReinsertEvent(previousEvent, event)));
        }
        provideEvent(event);
    }

    @WorkerThread
    private void provideEvent(@NonNull Pkcs11SlotEvent event) {
        SlotEventLogger.log("provide event", event);
        mPreviosSlotEvent.put(event.getSlot(), event);
        synchronized (mListeners) {
            for (SlotEventListener listener : mListeners) {
                listener.onPkcs11SlotEvent(event);
            }
        }
    }

    private final List<SlotEventListener> mListeners = new ArrayList<>();
    private final BlockingQueue<Pkcs11SlotEvent> mQueue;
    private final Map<Pkcs11Slot, Pkcs11SlotEvent> mPreviosSlotEvent = new HashMap<>();
}
