/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.utility.keyexecutor;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;

/**
 * Completion service for KeyExecutor see java.concurrent package documentation for more info.
 *
 * @param <R> return value
 */
public class KeyExecutorCompletionService<R, Key> {
    public KeyExecutorCompletionService(@NonNull KeyExecutor<Key> executor) {
        mExecutor = executor;
    }

    public Future<R> submit(Key key, @NonNull Callable<R> task) {
        RunnableFuture<R> future = new FutureTask<>(task);
        mExecutor.execute(key, new QueueingFuture(future));
        return future;
    }

    public Future<R> submit(Key key, @NonNull Runnable task, R result) {
        RunnableFuture<R> future = new FutureTask<>(task, result);
        mExecutor.execute(key, new QueueingFuture(future));
        return future;
    }

    public Future<R> take() throws InterruptedException {
        return mCompletionQueue.take();
    }

    public Future<R> poll() {
        return mCompletionQueue.poll();
    }

    /**
     * FutureTask extension to enqueue upon completion.
     */
    private class QueueingFuture extends FutureTask<Void> {
        QueueingFuture(RunnableFuture<R> task) {
            super(task, null);
            mTask = task;
        }

        @Override
        protected void done() {
            mCompletionQueue.add(mTask);
        }

        private final Future<R> mTask;
    }

    private final BlockingQueue<Future<R>> mCompletionQueue = new LinkedBlockingQueue<>();
    private final KeyExecutor<Key> mExecutor;
}
