/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.utility.keyexecutor;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Executes operations on separate worker thread for each key.
 */
public class KeyThreadPool<Key> implements KeyExecutor<Key> {
    @Override
    public void execute(Key key, @NonNull Runnable command) {
        Worker<Key> worker = mWorkers.get(key);
        if (null == worker) {
            worker = new Worker<>(key);
            mWorkers.put(key, worker);
        }
        worker.add(command);
    }

    public void shutdown() {
        for (Worker<Key> worker : mWorkers.values()) {
            worker.interrupt();
        }
    }

    private static class Worker<Key> extends Thread {
        Worker(Key key) {
            setName(getClass().getSimpleName() + " on key: " + key);
            start();
        }

        @WorkerThread
        @Override
        public void run() {
            while (!isInterrupted()) {
                final Runnable runnable = mQueue.poll();
                if (null != runnable) {
                    runnable.run();
                } else {
                    Thread.yield();
                }
            }
        }

        void add(@NonNull Runnable runnable) {
            mQueue.add(runnable);
        }

        private Queue<Runnable> mQueue = new ConcurrentLinkedQueue<>();
    }

    private Map<Key, Worker<Key>> mWorkers = new ConcurrentHashMap<>();
}
