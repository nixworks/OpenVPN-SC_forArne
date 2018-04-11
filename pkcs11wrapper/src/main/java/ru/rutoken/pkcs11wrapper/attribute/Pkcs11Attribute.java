/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.attribute;

import android.support.annotation.NonNull;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_ATTRIBUTE;
import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ReturnValue;

/**
 * Base class for pkcs11 attributes.
 * Instances of this hierarchy could be read from token, or created by user to pass to pkcs11 in template parameter
 */
public abstract class Pkcs11Attribute {
    public static void getAttributeValue(@NonNull Pkcs11Session session, long objectHandle,
                                         @NonNull Pkcs11Attribute attribute) throws Pkcs11Exception {
        List<Pkcs11Attribute> attributes = new ArrayList<>();
        attributes.add(attribute);
        getAttributeValues(session, objectHandle, attributes);
    }

    /**
     * Read attributes values from token
     */
    public static void getAttributeValues(@NonNull Pkcs11Session session, long objectHandle,
                                          @NonNull List<Pkcs11Attribute> attributes) throws Pkcs11Exception {
        CK_ATTRIBUTE[] ckAttributes = (CK_ATTRIBUTE[]) new CK_ATTRIBUTE().toArray(attributes.size());
        for (int a = 0; a < attributes.size(); ++a) {
            attributes.get(a).copyToCkAttribute(ckAttributes[a]);
        }
        NativeLong r = session.getModule().getPkcs11().C_GetAttributeValue(
                new NativeLong(session.getSessionHandle()), new NativeLong(objectHandle), ckAttributes, new NativeLong(ckAttributes.length));
        @NonNull Pkcs11ReturnValue res = Pkcs11ReturnValue.fromValue(r.longValue());
        if (!res.equals(Pkcs11ReturnValue.CKR_ATTRIBUTE_SENSITIVE) &&
                !res.equals(Pkcs11ReturnValue.CKR_ATTRIBUTE_TYPE_INVALID)) {
            Pkcs11Exception.throwIfNotOk(res, "can not get attributes lengths");
        }

        //allocate memory
        for (CK_ATTRIBUTE attribute : ckAttributes) {
            long size = attribute.ulValueLen.longValue();
            if (size != Pkcs11Constants.CK_UNAVAILABLE_INFORMATION.longValue() &&
                    size > 0) {
                attribute.pValue = new Memory(size);
            }
        }

        r = session.getModule().getPkcs11().C_GetAttributeValue(
                new NativeLong(session.getSessionHandle()), new NativeLong(objectHandle), ckAttributes, new NativeLong(ckAttributes.length));
        res = Pkcs11ReturnValue.fromValue(r.longValue());
        //not all return codes are real errors
        if (!res.equals(Pkcs11ReturnValue.CKR_ATTRIBUTE_SENSITIVE) &&
                !res.equals(Pkcs11ReturnValue.CKR_ATTRIBUTE_TYPE_INVALID)) {
            Pkcs11Exception.throwIfNotOk(res, "can not get attributes values");
        }
        for (int a = 0; a < attributes.size(); ++a) {
            Pkcs11Attribute attribute = attributes.get(a);
            attribute.copyFromCkAttribute(ckAttributes[a]);
            if (ckAttributes[a].ulValueLen.equals(Pkcs11Constants.CK_UNAVAILABLE_INFORMATION)) {
                if (res.equals(Pkcs11ReturnValue.CKR_ATTRIBUTE_SENSITIVE)) {
                    attribute.setSensitive(true);
                    attribute.setExist(true);
                } else if (res.equals(Pkcs11ReturnValue.CKR_ATTRIBUTE_TYPE_INVALID)) {
                    attribute.setExist(false);
                }
            } else {
                attribute.setExist(true);
            }
        }
    }

    protected Pkcs11Attribute(@NonNull Pkcs11AttributeType type) {
        mType = Objects.requireNonNull(type);
        mAttribute = new CK_ATTRIBUTE();
        mAttribute.type = new NativeLong(mType.getValue());
    }

    public void copyToCkAttribute(@NonNull CK_ATTRIBUTE to) {
        to.type = mAttribute.type;
        to.pValue = mAttribute.pValue;
        to.ulValueLen = mAttribute.ulValueLen;
    }

    public void copyFromCkAttribute(@NonNull CK_ATTRIBUTE from) {
        mAttribute.type = from.type;
        mAttribute.pValue = from.pValue;
        mAttribute.ulValueLen = from.ulValueLen;
    }

    @NonNull
    public Pkcs11AttributeType getType() {
        return mType;
    }

    public boolean isPresent() {
        return mExist;
    }

    public boolean isSensitive() {
        return mSensitive;
    }

    @NonNull
    public CK_ATTRIBUTE getAttribute() {
        return mAttribute;
    }

    @NonNull
    abstract public Object getValue();
    abstract public void setValue(@NonNull Object value);

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Pkcs11Attribute) {
            Pkcs11Attribute other = (Pkcs11Attribute) otherObject;
            if (this == other)
                return true;
            boolean attributeEquals = Objects.equals(mAttribute.type, other.mAttribute.type) &&
                    Objects.equals(mAttribute.pValue, other.mAttribute.pValue) &&
                    Objects.equals(mAttribute.ulValueLen, other.mAttribute.ulValueLen);
            return attributeEquals && Objects.equals(mType, other.mType) &&
                    mSensitive == other.mSensitive && mExist == other.mExist;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int attributeHash = Objects.hash(mAttribute.type, mAttribute.pValue, mAttribute.ulValueLen);
        return Objects.hash(attributeHash, mType, mSensitive, mExist);
    }

    private void setSensitive(boolean value) {
        mSensitive = value;
    }

    private void setExist(boolean value) {
        mExist = value;
    }

    protected final CK_ATTRIBUTE mAttribute;
    private final Pkcs11AttributeType mType;
    /**
     * Some attributes can not be read from token, so this flag is set for them
     */
    private boolean mSensitive = false;
    /**
     * In some cases, attribute may be optional
     */
    private boolean mExist = false;
}
