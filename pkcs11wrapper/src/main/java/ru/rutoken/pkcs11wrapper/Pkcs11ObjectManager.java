/*
 * Copyright 2018  Aktiv Co.
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package ru.rutoken.pkcs11wrapper;

import android.support.annotation.NonNull;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.NativeLongByReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ru.rutoken.pkcs11jna.CK_ATTRIBUTE;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.Pkcs11ObjectFactory;

public class Pkcs11ObjectManager {
    Pkcs11ObjectManager(@NonNull Pkcs11Session session) {
        mSession = Objects.requireNonNull(session);
        mModule = session.getToken().getSlot().getModule();
    }

    public void setMaxObjectCount(int maxObjectCount) {
        mMaxObjectCount = maxObjectCount;
    }

    /**
     * Simply find all objects matching template
     */
    @NonNull
    public List<Pkcs11Object> findObjectsAtOnce(@NonNull List<Pkcs11Attribute> template) throws Pkcs11Exception {
        final List<Pkcs11Object> objects = new ArrayList<>();
        findObjectsInit(template);
        while (true) {
            final List<Pkcs11Object> foundObjects = findObjects(mMaxObjectCount);
            if (!foundObjects.isEmpty()) {
                objects.addAll(foundObjects);
            }
            else {
                break;
            }
        }
        findObjectsFinal();
        return objects;
    }

    /**
     * Find all objects of known object class, template is deduced from object class
     */
    @NonNull
    public <Obj extends Pkcs11Object> List<Obj> findObjectsAtOnce(@NonNull Class<Obj> objectClass) throws Pkcs11Exception {
        @SuppressWarnings("unchecked")
        final List<Obj> objects = (List<Obj>) findObjectsAtOnce(Pkcs11ObjectFactory.getInstance().makeTemplate(objectClass));
        return objects;
    }

    /**
     * Find all objects of known class, template is provided by user. Template is completed with template deduced from class
     */
    @NonNull
    public <Obj extends Pkcs11Object> List<Obj> findObjectsAtOnce(@NonNull Class<Obj> objectClass, @NonNull List<Pkcs11Attribute> template) throws Pkcs11Exception {
        final Set<Pkcs11Attribute> uniqueAttributes = new HashSet<>(template);
        uniqueAttributes.addAll(Pkcs11ObjectFactory.getInstance().makeTemplate(objectClass));
        @SuppressWarnings("unchecked")
        final List<Obj> objects = (List<Obj>) findObjectsAtOnce(new ArrayList<>(uniqueAttributes));
        return objects;
    }

    public void findObjectsInit(List<Pkcs11Attribute> template) throws Pkcs11Exception {
        final CK_ATTRIBUTE[] ckAttributes = (CK_ATTRIBUTE[]) new CK_ATTRIBUTE().toArray(template.size());
        for (int a = 0; a < template.size(); ++a) {
            template.get(a).copyToCkAttribute(ckAttributes[a]);
        }

        final NativeLong r = mModule.getPkcs11().C_FindObjectsInit(
                new NativeLong(mSession.getSessionHandle()), ckAttributes, new NativeLong(ckAttributes.length));
        Pkcs11Exception.throwIfNotOk(r, "find objects initialization failed");
    }

    @NonNull
    public List<Pkcs11Object> findObjects(int maxObjectCount) throws Pkcs11Exception {
        final NativeLong[] objectHandles = new NativeLong[maxObjectCount];
        final NativeLongByReference actualCount = new NativeLongByReference();
        final NativeLong r = mModule.getPkcs11().C_FindObjects(
                new NativeLong(mSession.getSessionHandle()), objectHandles, new NativeLong(objectHandles.length), actualCount);
        Pkcs11Exception.throwIfNotOk(r, "find objects failed");

        final List<Pkcs11Object> objects = new ArrayList<>(actualCount.getValue().intValue());
        for (int h = 0; h < actualCount.getValue().intValue(); ++h) {
            //type is dynamic
            try {
                objects.add(Pkcs11ObjectFactory.getInstance().makeObject(mSession, objectHandles[h].longValue()));
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public void findObjectsFinal() throws Pkcs11Exception {
        final NativeLong r = mModule.getPkcs11().C_FindObjectsFinal(new NativeLong(mSession.getSessionHandle()));
        Pkcs11Exception.throwIfNotOk(r, "find objects finalization failed");
    }

    /**
     * Maximum number of objects searched by iteration in findObjectsAtOnce functions
     */
    private int mMaxObjectCount = 50;
    private final Pkcs11Session mSession;
    private final Pkcs11Module mModule;
}
