/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.businessrules;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import ru.rutoken.pkcs11.Pkcs11Fasade;
import ru.rutoken.pkcs11wrapper.Pkcs11Token;
import ru.rutoken.utility.Optional;
import ru.rutoken.utility.keyexecutor.KeyExecutorCompletionService;

public class TokenFinder {
    @NonNull
    public static Optional<Pkcs11Token> getBySerial(String serial) {
        final KeyExecutorCompletionService<Optional<Pkcs11Token>, Pkcs11Token> completionService =
                new KeyExecutorCompletionService<>(Pkcs11Fasade.getInstance().getTokenThreadPool());
        final List<Pkcs11Token> tokens = Pkcs11Fasade.getInstance().getTokenManager().getTokenList();
        for (Pkcs11Token token : tokens) {
            completionService.submit(token, () -> token.getTokenInfo().getSerialNumber().equals(serial) ?
                    Optional.of(token) : Optional.empty());
        }

        for (int t = 0; t < tokens.size(); ++t) {
            try {
                final Future<Optional<Pkcs11Token>> future = completionService.take();
                if (future.get().isPresent())
                    return future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
