/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.utility;

import java.util.NoSuchElementException;

// TODO replace with standard class when go to 24 api level
public class Optional<T> {
    public Optional(T value) {
        mValue = value;
    }

    public static <T> Optional<T> empty() {
        return new Optional<>(null);
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public boolean isPresent() {
        return null != mValue;
    }

    public T get() {
        if (null == mValue) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }

    private final T mValue;
}
