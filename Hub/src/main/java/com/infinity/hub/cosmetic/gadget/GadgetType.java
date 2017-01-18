package com.infinity.hub.cosmetic.gadget;

import com.infinity.hub.cosmetic.gadget.gadgets.IceGunGadget;

public enum GadgetType {

    ICEGUN(IceGunGadget.class),


    ;

    private Class<? extends Gadget> gadget;

    GadgetType(Class<? extends Gadget> gadget)
    {
        this.gadget = gadget;
    }

    public Class<? extends Gadget> getGadget() {
        return gadget;
    }

    public static String getName(Gadget gadget) {
        for(GadgetType gadgetType : GadgetType.values()) {
            if(gadgetType.gadget.isInstance(gadget)) {
                return gadgetType.name();
            }
        }

        return "";
    }
}
