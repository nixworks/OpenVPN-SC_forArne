/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package de.blinkt.openvpnsc.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.security.InvalidParameterException;

import de.blinkt.openvpnsc.R;
import ru.rutoken.utility.ServicePackageHelper;

public class ServiceInstallDialog extends DialogFragment {

    public static final String PARAM_DIALOG_TYPE = "dialogType";

    public enum DialogType {
        INSTALL_ON_CERTIFICATE_LIST(0, R.string.msg_install_rutoken_service),
        INSTALL_ON_VPN_CONNECT(1, R.string.msg_install_rutoken_service_on_connect);

        DialogType(int value, @StringRes int msg) {
            mValue = value;
            mMessage = msg;
        }

        @NonNull
        public static DialogType fromValue(int value) {
            for (DialogType type : DialogType.values()) {
                if (type.getValue() == value)
                    return type;
            }
            throw new InvalidParameterException();
        }

        public int getValue() {
            return mValue;
        }

        public int getMessage() {
            return mMessage;
        }

        private final int mValue;
        @StringRes
        private final int mMessage;
    }

    @NonNull
    public static ServiceInstallDialog newInstance(@NonNull DialogType type) {
        Bundle args = new Bundle();
        return newInstance(type, args);
    }

    public static ServiceInstallDialog newInstance(@NonNull DialogType type, @NonNull Bundle bundle) {
        ServiceInstallDialog dialog = new ServiceInstallDialog();
        bundle.putInt(PARAM_DIALOG_TYPE, type.getValue());
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DialogType type = DialogType.fromValue(getArguments().getInt(PARAM_DIALOG_TYPE));

        builder.setMessage(type.getMessage());
        builder.setPositiveButton(R.string.install, (dialog, which) -> ServicePackageHelper.installRutokenService(getActivity()));
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            Toast toast = Toast.makeText(getActivity(), "ERROR:" + getString(R.string.rutoken_service_required), Toast.LENGTH_LONG);
            toast.show();
        });
        setCancelable(false);
        return builder.create();
    }
}
