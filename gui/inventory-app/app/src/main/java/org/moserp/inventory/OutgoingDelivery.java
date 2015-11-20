package org.moserp.inventory;


import org.moserp.environment.Facility;

public class OutgoingDelivery extends Delivery {

    public OutgoingDelivery() {
    }

    public String getFromFacility() {
        return getLinkHref("fromFacility");
    }

    public void setFromFacility(Facility facility) {
        addLink("fromFacility", facility.getSelf());
    }

    @Override
    public String toString() {
        return "OutgoingDelivery [fromFacility=" + getFromFacility() + ", " + super.toString() + "]";
    }

}
