/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

public interface Pkcs11MutexHandler {
    void createMutex(Object mutex) throws Pkcs11Exception;

    void destroyMutex(Object mutex) throws Pkcs11Exception;

    void lockMutex(Object mutex) throws Pkcs11Exception;

    void unlockMutex(Object mutex) throws Pkcs11Exception;
}
