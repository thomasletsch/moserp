package org.moserp.inventory.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.RestUri;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IncomingDelivery extends Delivery {

    @ResourceAssociation("facilities")
    private RestUri toFacility;

    public IncomingDelivery(RestUri toFacility, DeliveryItem... items) {
        this.toFacility = toFacility;
        this.getItems().addAll(Arrays.asList(items));
    }

}
