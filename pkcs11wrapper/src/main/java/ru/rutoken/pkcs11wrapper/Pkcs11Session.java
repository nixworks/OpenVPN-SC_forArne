/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sun.jna.NativeLong;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_SESSION_INFO;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11UserType;

public class Pkcs11Session implements AutoCloseable {
    /**
     * Helper class, to logout automatically
     */
    public static class LoginGuard implements AutoCloseable {
        private LoginGuard(@NonNull Pkcs11Session session) {
            mSession = Objects.requireNonNull(session);
        }

        @Override
        public void close() throws Pkcs11Exception {
            mSession.logout();
        }

        private final Pkcs11Session mSession;
    }

    public Pkcs11Session(@NonNull Pkcs11Token token, long sessionHandle) {
        mToken = Objects.requireNonNull(token);
        mModule = token.getSlot().getModule();
        mSessionHandle = sessionHandle;
        mObjectManager = new Pkcs11ObjectManager(this);
        mSignVerifyManager = new Pkcs11SignVerifyManager(this);
    }

    @Override
    public void close() throws Pkcs11Exception {
        closeSession();
    }

    @NonNull
    public Pkcs11Token getToken() {
        return mToken;
    }

    @NonNull
    public Pkcs11Module getModule() {
        return mModule;
    }

    public long getSessionHandle() {
        return mSessionHandle;
    }

    @NonNull
    public Pkcs11ObjectManager getObjectManager() {
        return mObjectManager;
    }

    @NonNull
    public Pkcs11SignVerifyManager getSignVerifyManager() {
        return mSignVerifyManager;
    }

    @NonNull
    public Pkcs11SessionInfo getSessionInfo() throws Pkcs11Exception {
        CK_SESSION_INFO sessionInfo = new CK_SESSION_INFO();
        final NativeLong r = mModule.getPkcs11().C_GetSessionInfo(new NativeLong(mSessionHandle), sessionInfo);
        Pkcs11Exception.throwIfNotOk(r, "can not get session info");
        return new Pkcs11SessionInfo(sessionInfo);
    }

    /**
     * All sessions on token become logged in
     *
     * @param pin can be null if token uses CKF_PROTECTED_AUTHENTICATION_PATH
     */
    @NonNull
    public LoginGuard login(@NonNull Pkcs11UserType userType, @Nullable String pin) throws Pkcs11Exception {
        if (null == pin) {
            final NativeLong r = mModule.getPkcs11().C_Login(new NativeLong(mSessionHandle),
                    new NativeLong(userType.getValue()), null, null);
            Pkcs11Exception.throwIfNotOk(r, "can not login with protected authentication path");
        } else {
            final NativeLong r = mModule.getPkcs11().C_Login(new NativeLong(mSessionHandle),
                    new NativeLong(userType.getValue()), pin.getBytes(), new NativeLong(pin.length()));
            Pkcs11Exception.throwIfNotOk(r, "can not login");
        }
        return new LoginGuard(this);
    }

    private void logout() throws Pkcs11Exception {
        final NativeLong r = mModule.getPkcs11().C_Logout(new NativeLong(mSessionHandle));
        Pkcs11Exception.throwIfNotOk(r, "can not logout");
    }

    private void closeSession() throws Pkcs11Exception {
        final NativeLong r = mModule.getPkcs11().C_CloseSession(new NativeLong(mSessionHandle));
        Pkcs11Exception.throwIfNotOk(r, "cannot close session");
    }

    private final Pkcs11Token mToken;
    private final Pkcs11Module mModule;
    private final long mSessionHandle;
    private final Pkcs11ObjectManager mObjectManager;
    private final Pkcs11SignVerifyManager mSignVerifyManager;
}
