/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper.object;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11CertificateType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11KeyType;
import ru.rutoken.pkcs11wrapper.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.constant.Pkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.object.factory.NodeByObjectClassFinderVisitor;
import ru.rutoken.pkcs11wrapper.object.factory.ObjectClassGetterVisitor;
import ru.rutoken.pkcs11wrapper.object.factory.ObjectFactoryNode;
import ru.rutoken.pkcs11wrapper.object.factory.HierarchyObjectMakerVisitor;
import ru.rutoken.pkcs11wrapper.object.factory.TemplateMakerVisitor;

public class Pkcs11ObjectFactory {
    public static Pkcs11ObjectFactory getInstance() {
        if (null == sInstance.get()) {
            synchronized (Pkcs11ObjectFactory.class) {
                if (null == sInstance.get()) {
                    sInstance.set(new Pkcs11ObjectFactory());
                }
            }
        }
        return sInstance.get();
    }

    /**
     * Create simple Pkcs11Object (no inheritance take place) to wrap objectHandle. No calls to token are made.
     */
    public Pkcs11Object makePkcs11Object(long objectHandle) {
        return makeObject(Pkcs11Object.class, objectHandle);
    }

    /**
     * Create Pkcs11Object of known class. No calls to token are made.
     */
    public <Obj extends Pkcs11Object> Obj makeObject(@NonNull Class<Obj> objectClass, long objectHandle) {
        try {
            Constructor<Obj> constructor = objectClass.getDeclaredConstructor(long.class);
            constructor.setAccessible(true);
            Obj object = constructor.newInstance(objectHandle);
            object.registerAttributes();
            return object;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create Pkcs11Object which class is deduced using attributes read from token
     */
    @NonNull
    public Pkcs11Object makeObject(@NonNull Pkcs11Session session, long objectHandle) {
        final HierarchyObjectMakerVisitor visitor = new HierarchyObjectMakerVisitor(session, objectHandle);
        mRootNode.acceptVisitor(visitor);
        final Pkcs11Object object = visitor.getObject();
        object.registerAttributes();
        return object;
    }

    /**
     * Creates template using Pkcs11Object class. Resulting template contains only attributes with filled values.
     * That template can be used to find objects represented by objectClass.
     * Use Pkcs11Object.makeClearTemplate() if you need template for all object attributes
     */
    @NonNull
    public <Obj extends Pkcs11Object> List<Pkcs11Attribute> makeTemplate(@NonNull Class<Obj> objectClass) {
        final NodeByObjectClassFinderVisitor<Obj> findVisitor = new NodeByObjectClassFinderVisitor<>(objectClass);
        mRootNode.acceptVisitor(findVisitor);
        final ObjectFactoryNode<Obj> classNode = findVisitor.getNode();
        if (null == classNode)
            throw new RuntimeException("object class not registered");

        TemplateMakerVisitor makeVisitor = new TemplateMakerVisitor();
        classNode.acceptVisitor(makeVisitor);
        return makeVisitor.getTemplate();
    }

    public boolean isObjectClassRegistered(@NonNull Class<? extends Pkcs11Object> objectClass) {
        final NodeByObjectClassFinderVisitor visitor = new NodeByObjectClassFinderVisitor<>(objectClass);
        mRootNode.acceptVisitor(visitor);
        return null != visitor.getNode();
    }

    @NonNull
    public List<Class<? extends Pkcs11Object>> getRegisteredObjectClasses() {
        final ObjectClassGetterVisitor visitor = new ObjectClassGetterVisitor();
        mRootNode.acceptVisitor(visitor);
        return visitor.getObjectClasses();
    }

    private Pkcs11ObjectFactory() {
        registerClasses();
    }

    private static Pkcs11Attribute makeAttribute(@NonNull Pkcs11AttributeType type) {
        return Pkcs11AttributeFactory.getInstance().makeAttribute(type);
    }

    /**
     * One class cannot not be used in multiple nodes, like you can not have hierarchy like this: A -> B -> B, (B can't be inherited from itself)
     */
    private void registerClasses() {
        ObjectFactoryNode data = new ObjectFactoryNode<>(Pkcs11DataObject.class, null);
        ObjectFactoryNode certificate = new ObjectFactoryNode<>(Pkcs11CertificateObject.class, makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_TYPE));
        ObjectFactoryNode publicKey = new ObjectFactoryNode<>(Pkcs11PublicKeyObject.class, makeAttribute(Pkcs11AttributeType.CKA_KEY_TYPE));
        ObjectFactoryNode privateKey = new ObjectFactoryNode<>(Pkcs11PrivateKeyObject.class, makeAttribute(Pkcs11AttributeType.CKA_KEY_TYPE));
        ObjectFactoryNode secretKey = new ObjectFactoryNode<>(Pkcs11SecretKeyObject.class, null);
        ObjectFactoryNode hwFeature = new ObjectFactoryNode<>(Pkcs11HardwareFeatureObject.class, makeAttribute(Pkcs11AttributeType.CKA_HW_FEATURE_TYPE));
        ObjectFactoryNode domainParameters = new ObjectFactoryNode<>(Pkcs11DomainParametersObject.class, null);
        ObjectFactoryNode mechanism = new ObjectFactoryNode<>(Pkcs11MechanismObject.class, null);

        publicKey.addChild(Pkcs11KeyType.CKK_RSA.getValue(), new ObjectFactoryNode<>(Pkcs11RsaPublicKeyObject.class, null));

        privateKey.addChild(Pkcs11KeyType.CKK_RSA.getValue(), new ObjectFactoryNode<>(Pkcs11RsaPrivateKeyObject.class, null));

        certificate.addChild(Pkcs11CertificateType.CKC_X_509.getValue(), new ObjectFactoryNode<>(Pkcs11X509PublicKeyCertificateObject.class, null));
        certificate.addChild(Pkcs11CertificateType.CKC_WTLS.getValue(), new ObjectFactoryNode<>(Pkcs11WtlsCertificateObject.class, null));
        certificate.addChild(Pkcs11CertificateType.CKC_X_509_ATTR_CERT.getValue(), new ObjectFactoryNode<>(Pkcs11X509AttributeCertificateObject.class, null));

        hwFeature.addChild(Pkcs11HardwareFeatureType.CKH_MONOTONIC_COUNTER.getValue(), new ObjectFactoryNode<>(Pkcs11MonotonicCounterObject.class, null));
        hwFeature.addChild(Pkcs11HardwareFeatureType.CKH_CLOCK.getValue(), new ObjectFactoryNode<>(Pkcs11ClockObject.class, null));
        hwFeature.addChild(Pkcs11HardwareFeatureType.CKH_USER_INTERFACE.getValue(), new ObjectFactoryNode<>(Pkcs11UserInterfaceObject.class, null));

        mRootNode.addChild(Pkcs11ObjectClass.CKO_DATA.getValue(), data);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_CERTIFICATE.getValue(), certificate);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_PUBLIC_KEY.getValue(), publicKey);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_PRIVATE_KEY.getValue(), privateKey);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_SECRET_KEY.getValue(), secretKey);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_HW_FEATURE.getValue(), hwFeature);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_DOMAIN_PARAMETERS.getValue(), domainParameters);
        mRootNode.addChild(Pkcs11ObjectClass.CKO_MECHANISM.getValue(), mechanism);
    }

    private static final AtomicReference<Pkcs11ObjectFactory> sInstance = new AtomicReference<>();
    private final ObjectFactoryNode<?> mRootNode = new ObjectFactoryNode<>(Pkcs11Object.class, makeAttribute(Pkcs11AttributeType.CKA_CLASS));
}
