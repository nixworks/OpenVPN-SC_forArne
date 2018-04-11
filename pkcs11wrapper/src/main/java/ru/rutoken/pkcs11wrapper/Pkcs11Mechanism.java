/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.CK_MECHANISM;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11MechanismType;

public class Pkcs11Mechanism {
    public Pkcs11Mechanism(@NonNull Pkcs11MechanismType type) {
        mMechanismType = type;
        mMechanism = new CK_MECHANISM();
        mMechanism.mechanism = new NativeLong(mMechanismType.getValue());
    }

    public void copyToCkMechanism(@NonNull CK_MECHANISM to) {
        to.mechanism = mMechanism.mechanism;
        to.pParameter = mMechanism.pParameter;
        to.ulParameterLen = mMechanism.ulParameterLen;
    }

    public void copyFromCkMechanism(@NonNull CK_MECHANISM from) {
        mMechanism.mechanism = from.mechanism;
        mMechanism.pParameter = from.pParameter;
        mMechanism.ulParameterLen = from.ulParameterLen;
    }

    @NonNull
    public Pkcs11MechanismType getMechanismType() {
        return mMechanismType;
    }

    @NonNull
    public CK_MECHANISM getMechanism() {
        return mMechanism;
    }

    private final Pkcs11MechanismType mMechanismType;
    private final CK_MECHANISM mMechanism;
}
