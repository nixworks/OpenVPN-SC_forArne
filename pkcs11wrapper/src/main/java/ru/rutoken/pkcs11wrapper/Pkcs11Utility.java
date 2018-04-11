/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class Pkcs11Utility {
    @NonNull
    public static Date parseTime(@NonNull byte[] time) throws ParseException {
        final int reserved = 2;
        if (time.length > reserved) {
            final String timeString = new String(time, 0, time.length - reserved);
            final SimpleDateFormat utc = new SimpleDateFormat("yyyyMMddhhmmss");
            utc.setTimeZone(TimeZone.getTimeZone("UTC"));
            return utc.parse(timeString);
        }
        throw new ParseException("time parse error", 0);
    }
}
