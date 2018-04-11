/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11.session;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11UserType;

/**
 * Wraps Pkcs11Session to share it between callers
 */
public class SessionWrapper implements AutoCloseable {
    public static class Builder {
        public Builder(@NonNull Pkcs11Token token) {
            mToken = token;
        }

        public SessionWrapper build() throws Pkcs11Exception {
            return new SessionWrapper(this);
        }

        public Builder setUserType(@NonNull Pkcs11UserType userType) {
            mUserType = userType;
            return this;
        }

        public Builder setRwSession(boolean rwSession) {
            mRwSession = rwSession;
            return this;
        }

        public Builder setSerialSession(boolean serialSession) {
            mSerialSession = serialSession;
            return this;
        }

        public Builder setPin(@Nullable String pin) {
            mPin = pin;
            return this;
        }

        private final Pkcs11Token mToken;
        private boolean mSerialSession = true;
        private boolean mRwSession = false;
        private Pkcs11UserType mUserType = Pkcs11UserType.CKU_USER;
        @Nullable
        private String mPin;
    }

    protected SessionWrapper(@NonNull Builder builder) throws Pkcs11Exception {
        mBuilder = builder;
        mSession = mBuilder.mToken.openSession(mBuilder.mSerialSession, mBuilder.mRwSession);
    }

    public Pkcs11Session getSession() {
        return mSession;
    }

    public void login() throws Pkcs11Exception {
        if (null == mLoginGuard) {
            mLoginGuard = mSession.login(mBuilder.mUserType, mBuilder.mPin);
        }
    }

    public void logout() throws Pkcs11Exception {
        if (null != mLoginGuard) {
            mLoginGuard.close();
            mLoginGuard = null;
        }
    }

    @Override
    public void close() throws Pkcs11Exception {
        logout();
        mSession.close();
    }

    private final Builder mBuilder;
    private final Pkcs11Session mSession;
    @Nullable
    private Pkcs11Session.LoginGuard mLoginGuard = null;
}
