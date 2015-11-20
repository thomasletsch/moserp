package org.moserp.inventory;


import org.moserp.environment.Facility;

public class IncomingDelivery extends Delivery {

    public IncomingDelivery() {
    }

    public String getToFacility() {
        return getLinkHref("toFacility");
    }

    public void setToFacility(Facility facility) {
        addLink("toFacility", facility.getSelf());
    }

    @Override
    public String toString() {
        return "IncomingDelivery [toFacility=" + getToFacility() + ", " + super.toString() + "]";
    }

}
