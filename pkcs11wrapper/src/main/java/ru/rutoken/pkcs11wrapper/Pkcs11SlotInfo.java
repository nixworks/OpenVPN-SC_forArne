/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;

import org.apache.commons.lang3.builder.ToStringBuilder;

import ru.rutoken.pkcs11jna.CK_SLOT_INFO;
import ru.rutoken.pkcs11jna.Pkcs11Constants;

public class Pkcs11SlotInfo {
    public Pkcs11SlotInfo(@NonNull CK_SLOT_INFO slotInfo) {
        mSlotDescription = new String(slotInfo.slotDescription);
        mManufacturerId = new String(slotInfo.manufacturerID);
        mHardwareVersion = new Pkcs11Version(slotInfo.hardwareVersion);
        mFirmwareVersion = new Pkcs11Version(slotInfo.firmwareVersion);
        mFlags = slotInfo.flags.longValue();
    }

    public Pkcs11SlotInfo(@NonNull String slotDescription, @NonNull String manufacturerId,
                          @NonNull Pkcs11Version hardwareVersion, @NonNull Pkcs11Version firmwareVersion,
                          long flags) {
        mSlotDescription = slotDescription;
        mManufacturerId = manufacturerId;
        mHardwareVersion = hardwareVersion;
        mFirmwareVersion = firmwareVersion;
        mFlags = flags;
    }

    @NonNull
    public String getSlotDescription() {
        return mSlotDescription;
    }

    @NonNull
    public String getManufacturerId() {
        return mManufacturerId;
    }

    @NonNull
    public Pkcs11Version getHardwareVersion() {
        return mHardwareVersion;
    }

    @NonNull
    public Pkcs11Version getFirmwareVersion() {
        return mFirmwareVersion;
    }

    public boolean isTokenPresent() {
        return checkFlag(Pkcs11Constants.CKF_TOKEN_PRESENT);
    }

    public boolean isRemovableDevice() {
        return checkFlag(Pkcs11Constants.CKF_REMOVABLE_DEVICE);
    }

    public boolean isHwSlot() {
        return checkFlag(Pkcs11Constants.CKF_HW_SLOT);
    }

    public long getFlags() {
        return mFlags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private boolean checkFlag(NativeLong flag) {
        return (mFlags & flag.longValue()) != 0L;
    }

    private final String mSlotDescription;
    private final String mManufacturerId;
    private final Pkcs11Version mHardwareVersion;
    private final Pkcs11Version mFirmwareVersion;
    private final long mFlags;
}
