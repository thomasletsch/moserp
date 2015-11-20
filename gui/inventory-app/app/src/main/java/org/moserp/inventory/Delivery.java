package org.moserp.inventory;

import org.moserp.common.domain.IdentifiableEntity;

import java.util.ArrayList;
import java.util.List;

public class Delivery extends IdentifiableEntity {
    private List<DeliveryItem> items = new ArrayList<>();

    public List<DeliveryItem> getItems() {
        return items;
    }

    public void add(DeliveryItem item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return "items=" + items + ", " + super.toString();
    }
}
