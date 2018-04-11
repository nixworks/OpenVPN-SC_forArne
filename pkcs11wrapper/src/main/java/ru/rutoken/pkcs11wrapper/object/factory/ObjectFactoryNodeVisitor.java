/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import android.support.annotation.NonNull;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

public interface ObjectFactoryNodeVisitor {
    void visit(@NonNull ObjectFactoryNode<? extends Pkcs11Object> node) throws Exception;
}
