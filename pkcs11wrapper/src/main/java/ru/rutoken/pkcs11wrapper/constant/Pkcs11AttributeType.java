/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.constant;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;

import java.util.Objects;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11CertificateTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11HardwareFeatureTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11KeyTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;

public enum Pkcs11AttributeType {
    CKA_CLASS(Pkcs11Constants.CKA_CLASS, Pkcs11ObjectClassAttribute.class),
    CKA_TOKEN(Pkcs11Constants.CKA_TOKEN, Pkcs11BooleanAttribute.class),
    CKA_PRIVATE(Pkcs11Constants.CKA_PRIVATE, Pkcs11BooleanAttribute.class),
    CKA_LABEL(Pkcs11Constants.CKA_LABEL, Pkcs11StringAttribute.class),
    CKA_APPLICATION(Pkcs11Constants.CKA_APPLICATION, Pkcs11StringAttribute.class),
    CKA_VALUE(Pkcs11Constants.CKA_VALUE, Pkcs11ByteArrayAttribute.class),
    CKA_OBJECT_ID(Pkcs11Constants.CKA_OBJECT_ID, Pkcs11ByteArrayAttribute.class),
    CKA_CERTIFICATE_TYPE(Pkcs11Constants.CKA_CERTIFICATE_TYPE, Pkcs11CertificateTypeAttribute.class),
    CKA_ISSUER(Pkcs11Constants.CKA_ISSUER, Pkcs11ByteArrayAttribute.class),
    CKA_SERIAL_NUMBER(Pkcs11Constants.CKA_SERIAL_NUMBER, Pkcs11ByteArrayAttribute.class),
    CKA_AC_ISSUER(Pkcs11Constants.CKA_AC_ISSUER, Pkcs11ByteArrayAttribute.class),
    CKA_OWNER(Pkcs11Constants.CKA_OWNER, Pkcs11ByteArrayAttribute.class),
    CKA_ATTR_TYPES(Pkcs11Constants.CKA_ATTR_TYPES, Pkcs11ByteArrayAttribute.class),
    CKA_TRUSTED(Pkcs11Constants.CKA_TRUSTED, Pkcs11BooleanAttribute.class),
    CKA_CERTIFICATE_CATEGORY(Pkcs11Constants.CKA_CERTIFICATE_CATEGORY, Pkcs11LongAttribute.class),
    CKA_JAVA_MIDP_SECURITY_DOMAIN(Pkcs11Constants.CKA_JAVA_MIDP_SECURITY_DOMAIN, Pkcs11LongAttribute.class),
    CKA_URL(Pkcs11Constants.CKA_URL, Pkcs11StringAttribute.class),
    CKA_HASH_OF_SUBJECT_PUBLIC_KEY(Pkcs11Constants.CKA_HASH_OF_SUBJECT_PUBLIC_KEY, Pkcs11ByteArrayAttribute.class),
    CKA_HASH_OF_ISSUER_PUBLIC_KEY(Pkcs11Constants.CKA_HASH_OF_ISSUER_PUBLIC_KEY, Pkcs11ByteArrayAttribute.class),
    // CKA_NAME_HASH_ALGORITHM(Pkcs11Constants.CKA_NAME_HASH_ALGORITHM, Pkcs11MechanismTypeAttribute.class),
    CKA_CHECK_VALUE(Pkcs11Constants.CKA_CHECK_VALUE, Pkcs11ByteArrayAttribute.class),
    CKA_KEY_TYPE(Pkcs11Constants.CKA_KEY_TYPE, Pkcs11KeyTypeAttribute.class),
    CKA_SUBJECT(Pkcs11Constants.CKA_SUBJECT, Pkcs11ByteArrayAttribute.class),
    CKA_ID(Pkcs11Constants.CKA_ID, Pkcs11ByteArrayAttribute.class),
    CKA_SENSITIVE(Pkcs11Constants.CKA_SENSITIVE, Pkcs11BooleanAttribute.class),
    CKA_ENCRYPT(Pkcs11Constants.CKA_ENCRYPT, Pkcs11BooleanAttribute.class),
    CKA_DECRYPT(Pkcs11Constants.CKA_DECRYPT, Pkcs11BooleanAttribute.class),
    CKA_WRAP(Pkcs11Constants.CKA_WRAP, Pkcs11BooleanAttribute.class),
    CKA_UNWRAP(Pkcs11Constants.CKA_UNWRAP, Pkcs11BooleanAttribute.class),
    CKA_SIGN(Pkcs11Constants.CKA_SIGN, Pkcs11BooleanAttribute.class),
    CKA_SIGN_RECOVER(Pkcs11Constants.CKA_SIGN_RECOVER, Pkcs11BooleanAttribute.class),
    CKA_VERIFY(Pkcs11Constants.CKA_VERIFY, Pkcs11BooleanAttribute.class),
    CKA_VERIFY_RECOVER(Pkcs11Constants.CKA_VERIFY_RECOVER, Pkcs11BooleanAttribute.class),
    CKA_DERIVE(Pkcs11Constants.CKA_DERIVE, Pkcs11BooleanAttribute.class),
    CKA_START_DATE(Pkcs11Constants.CKA_START_DATE, Pkcs11DateAttribute.class),
    CKA_END_DATE(Pkcs11Constants.CKA_END_DATE, Pkcs11DateAttribute.class),
    CKA_MODULUS(Pkcs11Constants.CKA_MODULUS, Pkcs11ByteArrayAttribute.class),
    CKA_MODULUS_BITS(Pkcs11Constants.CKA_MODULUS_BITS, Pkcs11LongAttribute.class),
    CKA_PUBLIC_EXPONENT(Pkcs11Constants.CKA_PUBLIC_EXPONENT, Pkcs11ByteArrayAttribute.class),
    CKA_PRIVATE_EXPONENT(Pkcs11Constants.CKA_PRIVATE_EXPONENT, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME_1(Pkcs11Constants.CKA_PRIME_1, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME_2(Pkcs11Constants.CKA_PRIME_2, Pkcs11ByteArrayAttribute.class),
    CKA_EXPONENT_1(Pkcs11Constants.CKA_EXPONENT_1, Pkcs11ByteArrayAttribute.class),
    CKA_EXPONENT_2(Pkcs11Constants.CKA_EXPONENT_2, Pkcs11ByteArrayAttribute.class),
    CKA_COEFFICIENT(Pkcs11Constants.CKA_COEFFICIENT, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME(Pkcs11Constants.CKA_PRIME, Pkcs11ByteArrayAttribute.class),
    CKA_SUBPRIME(Pkcs11Constants.CKA_SUBPRIME, Pkcs11ByteArrayAttribute.class),
    CKA_BASE(Pkcs11Constants.CKA_BASE, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME_BITS(Pkcs11Constants.CKA_PRIME_BITS, Pkcs11LongAttribute.class),
    CKA_SUBPRIME_BITS(Pkcs11Constants.CKA_SUBPRIME_BITS, Pkcs11LongAttribute.class),
    CKA_VALUE_BITS(Pkcs11Constants.CKA_VALUE_BITS, Pkcs11LongAttribute.class),
    CKA_VALUE_LEN(Pkcs11Constants.CKA_VALUE_LEN, Pkcs11LongAttribute.class),
    CKA_EXTRACTABLE(Pkcs11Constants.CKA_EXTRACTABLE, Pkcs11BooleanAttribute.class),
    CKA_LOCAL(Pkcs11Constants.CKA_LOCAL, Pkcs11BooleanAttribute.class),
    CKA_NEVER_EXTRACTABLE(Pkcs11Constants.CKA_NEVER_EXTRACTABLE, Pkcs11BooleanAttribute.class),
    CKA_ALWAYS_SENSITIVE(Pkcs11Constants.CKA_ALWAYS_SENSITIVE, Pkcs11BooleanAttribute.class),
    CKA_KEY_GEN_MECHANISM(Pkcs11Constants.CKA_KEY_GEN_MECHANISM, Pkcs11MechanismTypeAttribute.class),
    CKA_MODIFIABLE(Pkcs11Constants.CKA_MODIFIABLE, Pkcs11BooleanAttribute.class),
    //CKA_COPYABLE(Pkcs11Constants.CKA_COPYABLE, Pkcs11BooleanAttribute.class),
    //CKA_DESTROYABLE(Pkcs11Constants.CKA_DESTROYABLE, Pkcs11BooleanAttribute.class),
    CKA_ECDSA_PARAMS(Pkcs11Constants.CKA_ECDSA_PARAMS, Pkcs11ByteArrayAttribute.class),
    CKA_EC_PARAMS(Pkcs11Constants.CKA_EC_PARAMS, Pkcs11ByteArrayAttribute.class),
    CKA_EC_POINT(Pkcs11Constants.CKA_EC_POINT, Pkcs11ByteArrayAttribute.class),
    @Deprecated CKA_SECONDARY_AUTH(Pkcs11Constants.CKA_SECONDARY_AUTH, Pkcs11BooleanAttribute.class),
    @Deprecated CKA_AUTH_PIN_FLAGS(Pkcs11Constants.CKA_AUTH_PIN_FLAGS, Pkcs11LongAttribute.class),
    CKA_ALWAYS_AUTHENTICATE(Pkcs11Constants.CKA_ALWAYS_AUTHENTICATE, Pkcs11BooleanAttribute.class),
    CKA_WRAP_WITH_TRUSTED(Pkcs11Constants.CKA_WRAP_WITH_TRUSTED, Pkcs11BooleanAttribute.class),
    CKA_WRAP_TEMPLATE(Pkcs11Constants.CKA_WRAP_TEMPLATE, Pkcs11ArrayAttribute.class),
    CKA_UNWRAP_TEMPLATE(Pkcs11Constants.CKA_UNWRAP_TEMPLATE, Pkcs11ArrayAttribute.class),
    CKA_HW_FEATURE_TYPE(Pkcs11Constants.CKA_HW_FEATURE_TYPE, Pkcs11HardwareFeatureTypeAttribute.class),
    CKA_RESET_ON_INIT(Pkcs11Constants.CKA_RESET_ON_INIT, Pkcs11BooleanAttribute.class),
    CKA_HAS_RESET(Pkcs11Constants.CKA_HAS_RESET, Pkcs11BooleanAttribute.class),
    CKA_PIXEL_X(Pkcs11Constants.CKA_PIXEL_X, Pkcs11LongAttribute.class),
    CKA_PIXEL_Y(Pkcs11Constants.CKA_PIXEL_Y, Pkcs11LongAttribute.class),
    CKA_RESOLUTION(Pkcs11Constants.CKA_RESOLUTION, Pkcs11LongAttribute.class),
    CKA_CHAR_ROWS(Pkcs11Constants.CKA_CHAR_ROWS, Pkcs11LongAttribute.class),
    CKA_CHAR_COLUMNS(Pkcs11Constants.CKA_CHAR_COLUMNS, Pkcs11LongAttribute.class),
    CKA_COLOR(Pkcs11Constants.CKA_COLOR, Pkcs11BooleanAttribute.class),
    CKA_BITS_PER_PIXEL(Pkcs11Constants.CKA_BITS_PER_PIXEL, Pkcs11LongAttribute.class),
    CKA_CHAR_SETS(Pkcs11Constants.CKA_CHAR_SETS, Pkcs11StringAttribute.class),
    CKA_ENCODING_METHODS(Pkcs11Constants.CKA_ENCODING_METHODS, Pkcs11StringAttribute.class),
    CKA_MIME_TYPES(Pkcs11Constants.CKA_MIME_TYPES, Pkcs11StringAttribute.class),
    CKA_MECHANISM_TYPE(Pkcs11Constants.CKA_MECHANISM_TYPE, Pkcs11MechanismTypeAttribute.class),
    CKA_REQUIRED_CMS_ATTRIBUTES(Pkcs11Constants.CKA_REQUIRED_CMS_ATTRIBUTES, Pkcs11ByteArrayAttribute.class),
    CKA_DEFAULT_CMS_ATTRIBUTES(Pkcs11Constants.CKA_DEFAULT_CMS_ATTRIBUTES, Pkcs11ByteArrayAttribute.class),
    CKA_SUPPORTED_CMS_ATTRIBUTES(Pkcs11Constants.CKA_SUPPORTED_CMS_ATTRIBUTES, Pkcs11ByteArrayAttribute.class),
    CKA_ALLOWED_MECHANISMS(Pkcs11Constants.CKA_ALLOWED_MECHANISMS, Pkcs11MechanismArrayAttribute.class),
    CKA_VENDOR_DEFINED(Pkcs11Constants.CKA_VENDOR_DEFINED, Pkcs11ByteArrayAttribute.class);

    public static Pkcs11AttributeType fromValue(long value) {
        for (Pkcs11AttributeType val : values()) {
            if (val.getValue() == value)
                return val;
        }
        throw new IllegalArgumentException();
    }

    public long getValue() {
        return mValue;
    }

    public Class<? extends Pkcs11Attribute> getAttributeClass() {
        return mAttributeClass;
    }

    Pkcs11AttributeType(NativeLong value, @NonNull Class<? extends Pkcs11Attribute> attributeClass) {
        mValue = value.longValue();
        mAttributeClass = Objects.requireNonNull(attributeClass);
    }
    private final long mValue;

    private final Class<? extends Pkcs11Attribute> mAttributeClass;
}
