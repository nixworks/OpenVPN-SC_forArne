/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.utility.keyexecutor;

import android.support.annotation.NonNull;

public interface KeyExecutor<Key> {
    void execute(Key key, @NonNull Runnable command);
}
