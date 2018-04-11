/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.businessrules;

import java.util.ArrayList;
import java.util.List;

public class LoadProgress {

    private int mLoadCounter = 0;
    private final List<LoadProgressListener> mListeners = new ArrayList<>();

    public interface LoadProgressListener {
        void onProgressStateChanged(boolean started);
    }

    public void stopProgress() {
        if (mLoadCounter > 0) {
            --mLoadCounter;

            if (mLoadCounter == 0) {
                for (LoadProgressListener listener : mListeners)
                    listener.onProgressStateChanged(false);
            }
        }
    }

    public void startProgress() {
        if (mLoadCounter == 0) {
            for (LoadProgressListener listener : mListeners)
                listener.onProgressStateChanged(true);
        }
        ++mLoadCounter;
    }

    public boolean isLoading() {
        return mLoadCounter > 0;
    }

    public void addListener(LoadProgressListener listener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener);
        }
    }

    public void removeListener(LoadProgressListener listener) {
        mListeners.remove(listener);
    }
}
