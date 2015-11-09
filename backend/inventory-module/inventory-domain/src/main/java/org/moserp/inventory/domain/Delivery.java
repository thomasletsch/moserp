package org.moserp.inventory.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.moserp.common.domain.EntityWithAuditInfo;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Delivery extends EntityWithAuditInfo {
    @Getter
    private List<DeliveryItem> items = new ArrayList<>();

    public void add(DeliveryItem item) {
        items.add(item);
    }

}
