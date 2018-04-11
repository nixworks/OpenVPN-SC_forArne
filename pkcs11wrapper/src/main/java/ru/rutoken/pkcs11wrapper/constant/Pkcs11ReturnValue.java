/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.Pkcs11Constants;

public enum Pkcs11ReturnValue {
    CKR_OK(Pkcs11Constants.CKR_OK),
    CKR_CANCEL(Pkcs11Constants.CKR_CANCEL),
    CKR_HOST_MEMORY(Pkcs11Constants.CKR_HOST_MEMORY),
    CKR_SLOT_ID_INVALID(Pkcs11Constants.CKR_SLOT_ID_INVALID),
    CKR_GENERAL_ERROR(Pkcs11Constants.CKR_GENERAL_ERROR),
    CKR_FUNCTION_FAILED(Pkcs11Constants.CKR_FUNCTION_FAILED),
    CKR_ARGUMENTS_BAD(Pkcs11Constants.CKR_ARGUMENTS_BAD),
    CKR_NO_EVENT(Pkcs11Constants.CKR_NO_EVENT),
    CKR_NEED_TO_CREATE_THREADS(Pkcs11Constants.CKR_NEED_TO_CREATE_THREADS),
    CKR_CANT_LOCK(Pkcs11Constants.CKR_CANT_LOCK),
    CKR_ATTRIBUTE_READ_ONLY(Pkcs11Constants.CKR_ATTRIBUTE_READ_ONLY),
    CKR_ATTRIBUTE_SENSITIVE(Pkcs11Constants.CKR_ATTRIBUTE_SENSITIVE),
    CKR_ATTRIBUTE_TYPE_INVALID(Pkcs11Constants.CKR_ATTRIBUTE_TYPE_INVALID),
    CKR_ATTRIBUTE_VALUE_INVALID(Pkcs11Constants.CKR_ATTRIBUTE_VALUE_INVALID),
    //CKR_ACTION_PROHIBITED(Pkcs11Constants.CKR_ACTION_PROHIBITED),
    CKR_DATA_INVALID(Pkcs11Constants.CKR_DATA_INVALID),
    CKR_DATA_LEN_RANGE(Pkcs11Constants.CKR_DATA_LEN_RANGE),
    CKR_DEVICE_ERROR(Pkcs11Constants.CKR_DEVICE_ERROR),
    CKR_DEVICE_MEMORY(Pkcs11Constants.CKR_DEVICE_MEMORY),
    CKR_DEVICE_REMOVED(Pkcs11Constants.CKR_DEVICE_REMOVED),
    CKR_ENCRYPTED_DATA_INVALID(Pkcs11Constants.CKR_ENCRYPTED_DATA_INVALID),
    CKR_ENCRYPTED_DATA_LEN_RANGE(Pkcs11Constants.CKR_ENCRYPTED_DATA_LEN_RANGE),
    CKR_FUNCTION_CANCELED(Pkcs11Constants.CKR_FUNCTION_CANCELED),
    CKR_FUNCTION_NOT_PARALLEL(Pkcs11Constants.CKR_FUNCTION_NOT_PARALLEL),
    CKR_FUNCTION_NOT_SUPPORTED(Pkcs11Constants.CKR_FUNCTION_NOT_SUPPORTED),
    CKR_KEY_HANDLE_INVALID(Pkcs11Constants.CKR_KEY_HANDLE_INVALID),
    CKR_KEY_SIZE_RANGE(Pkcs11Constants.CKR_KEY_SIZE_RANGE),
    CKR_KEY_TYPE_INCONSISTENT(Pkcs11Constants.CKR_KEY_TYPE_INCONSISTENT),
    CKR_KEY_NOT_NEEDED(Pkcs11Constants.CKR_KEY_NOT_NEEDED),
    CKR_KEY_CHANGED(Pkcs11Constants.CKR_KEY_CHANGED),
    CKR_KEY_NEEDED(Pkcs11Constants.CKR_KEY_NEEDED),
    CKR_KEY_INDIGESTIBLE(Pkcs11Constants.CKR_KEY_INDIGESTIBLE),
    CKR_KEY_FUNCTION_NOT_PERMITTED(Pkcs11Constants.CKR_KEY_FUNCTION_NOT_PERMITTED),
    CKR_KEY_NOT_WRAPPABLE(Pkcs11Constants.CKR_KEY_NOT_WRAPPABLE),
    CKR_KEY_UNEXTRACTABLE(Pkcs11Constants.CKR_KEY_UNEXTRACTABLE),
    CKR_MECHANISM_INVALID(Pkcs11Constants.CKR_MECHANISM_INVALID),
    CKR_MECHANISM_PARAM_INVALID(Pkcs11Constants.CKR_MECHANISM_PARAM_INVALID),
    CKR_OBJECT_HANDLE_INVALID(Pkcs11Constants.CKR_OBJECT_HANDLE_INVALID),
    CKR_OPERATION_ACTIVE(Pkcs11Constants.CKR_OPERATION_ACTIVE),
    CKR_OPERATION_NOT_INITIALIZED(Pkcs11Constants.CKR_OPERATION_NOT_INITIALIZED),
    CKR_PIN_INCORRECT(Pkcs11Constants.CKR_PIN_INCORRECT),
    CKR_PIN_INVALID(Pkcs11Constants.CKR_PIN_INVALID),
    CKR_PIN_LEN_RANGE(Pkcs11Constants.CKR_PIN_LEN_RANGE),
    CKR_PIN_EXPIRED(Pkcs11Constants.CKR_PIN_EXPIRED),
    CKR_PIN_LOCKED(Pkcs11Constants.CKR_PIN_LOCKED),
    CKR_SESSION_CLOSED(Pkcs11Constants.CKR_SESSION_CLOSED),
    CKR_SESSION_COUNT(Pkcs11Constants.CKR_SESSION_COUNT),
    CKR_SESSION_HANDLE_INVALID(Pkcs11Constants.CKR_SESSION_HANDLE_INVALID),
    CKR_SESSION_PARALLEL_NOT_SUPPORTED(Pkcs11Constants.CKR_SESSION_PARALLEL_NOT_SUPPORTED),
    CKR_SESSION_READ_ONLY(Pkcs11Constants.CKR_SESSION_READ_ONLY),
    CKR_SESSION_EXISTS(Pkcs11Constants.CKR_SESSION_EXISTS),
    CKR_SESSION_READ_ONLY_EXISTS(Pkcs11Constants.CKR_SESSION_READ_ONLY_EXISTS),
    CKR_SESSION_READ_WRITE_SO_EXISTS(Pkcs11Constants.CKR_SESSION_READ_WRITE_SO_EXISTS),
    CKR_SIGNATURE_INVALID(Pkcs11Constants.CKR_SIGNATURE_INVALID),
    CKR_SIGNATURE_LEN_RANGE(Pkcs11Constants.CKR_SIGNATURE_LEN_RANGE),
    CKR_TEMPLATE_INCOMPLETE(Pkcs11Constants.CKR_TEMPLATE_INCOMPLETE),
    CKR_TEMPLATE_INCONSISTENT(Pkcs11Constants.CKR_TEMPLATE_INCONSISTENT),
    CKR_TOKEN_NOT_PRESENT(Pkcs11Constants.CKR_TOKEN_NOT_PRESENT),
    CKR_TOKEN_NOT_RECOGNIZED(Pkcs11Constants.CKR_TOKEN_NOT_RECOGNIZED),
    CKR_TOKEN_WRITE_PROTECTED(Pkcs11Constants.CKR_TOKEN_WRITE_PROTECTED),
    CKR_UNWRAPPING_KEY_HANDLE_INVALID(Pkcs11Constants.CKR_UNWRAPPING_KEY_HANDLE_INVALID),
    CKR_UNWRAPPING_KEY_SIZE_RANGE(Pkcs11Constants.CKR_UNWRAPPING_KEY_SIZE_RANGE),
    CKR_UNWRAPPING_KEY_TYPE_INCONSISTENT(Pkcs11Constants.CKR_UNWRAPPING_KEY_TYPE_INCONSISTENT),
    CKR_USER_ALREADY_LOGGED_IN(Pkcs11Constants.CKR_USER_ALREADY_LOGGED_IN),
    CKR_USER_NOT_LOGGED_IN(Pkcs11Constants.CKR_USER_NOT_LOGGED_IN),
    CKR_USER_PIN_NOT_INITIALIZED(Pkcs11Constants.CKR_USER_PIN_NOT_INITIALIZED),
    CKR_USER_TYPE_INVALID(Pkcs11Constants.CKR_USER_TYPE_INVALID),
    CKR_USER_ANOTHER_ALREADY_LOGGED_IN(Pkcs11Constants.CKR_USER_ANOTHER_ALREADY_LOGGED_IN),
    CKR_USER_TOO_MANY_TYPES(Pkcs11Constants.CKR_USER_TOO_MANY_TYPES),
    CKR_WRAPPED_KEY_INVALID(Pkcs11Constants.CKR_WRAPPED_KEY_INVALID),
    CKR_WRAPPED_KEY_LEN_RANGE(Pkcs11Constants.CKR_WRAPPED_KEY_LEN_RANGE),
    CKR_WRAPPING_KEY_HANDLE_INVALID(Pkcs11Constants.CKR_WRAPPING_KEY_HANDLE_INVALID),
    CKR_WRAPPING_KEY_SIZE_RANGE(Pkcs11Constants.CKR_WRAPPING_KEY_SIZE_RANGE),
    CKR_WRAPPING_KEY_TYPE_INCONSISTENT(Pkcs11Constants.CKR_WRAPPING_KEY_TYPE_INCONSISTENT),
    CKR_RANDOM_SEED_NOT_SUPPORTED(Pkcs11Constants.CKR_RANDOM_SEED_NOT_SUPPORTED),
    CKR_RANDOM_NO_RNG(Pkcs11Constants.CKR_RANDOM_NO_RNG),
    CKR_DOMAIN_PARAMS_INVALID(Pkcs11Constants.CKR_DOMAIN_PARAMS_INVALID),
    //CKR_CURVE_NOT_SUPPORTED(Pkcs11Constants.CKR_CURVE_NOT_SUPPORTED),
    CKR_BUFFER_TOO_SMALL(Pkcs11Constants.CKR_BUFFER_TOO_SMALL),
    CKR_SAVED_STATE_INVALID(Pkcs11Constants.CKR_SAVED_STATE_INVALID),
    CKR_INFORMATION_SENSITIVE(Pkcs11Constants.CKR_INFORMATION_SENSITIVE),
    CKR_STATE_UNSAVEABLE(Pkcs11Constants.CKR_STATE_UNSAVEABLE),
    CKR_CRYPTOKI_NOT_INITIALIZED(Pkcs11Constants.CKR_CRYPTOKI_NOT_INITIALIZED),
    CKR_CRYPTOKI_ALREADY_INITIALIZED(Pkcs11Constants.CKR_CRYPTOKI_ALREADY_INITIALIZED),
    CKR_MUTEX_BAD(Pkcs11Constants.CKR_MUTEX_BAD),
    CKR_MUTEX_NOT_LOCKED(Pkcs11Constants.CKR_MUTEX_NOT_LOCKED),
    CKR_NEW_PIN_MODE(Pkcs11Constants.CKR_NEW_PIN_MODE),
    CKR_NEXT_OTP(Pkcs11Constants.CKR_NEXT_OTP),
    //CKR_EXCEEDED_MAX_ITERATIONS(Pkcs11Constants.CKR_EXCEEDED_MAX_ITERATIONS),
    //CKR_FIPS_SELF_TEST_FAILED(Pkcs11Constants.CKR_FIPS_SELF_TEST_FAILED),
    //CKR_LIBRARY_LOAD_FAILED(Pkcs11Constants.CKR_LIBRARY_LOAD_FAILED),
    //CKR_PIN_TOO_WEAK(Pkcs11Constants.CKR_PIN_TOO_WEAK),
    //CKR_PUBLIC_KEY_INVALID(Pkcs11Constants.CKR_PUBLIC_KEY_INVALID),
    CKR_FUNCTION_REJECTED(Pkcs11Constants.CKR_FUNCTION_REJECTED),
    CKR_VENDOR_DEFINED(Pkcs11Constants.CKR_VENDOR_DEFINED);

    public static Pkcs11ReturnValue fromValue(long value) {
        for (Pkcs11ReturnValue val : values()) {
            if (val.getValue() == value)
                return val;
        }
        throw new IllegalArgumentException();
    }

    public long getValue() {
        return mValue;
    }

    Pkcs11ReturnValue(NativeLong value) {
        mValue = value.longValue();
    }

    private final long mValue;
}
