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
public class OutgoingDelivery extends Delivery {

    @ResourceAssociation("facilities")
    private RestUri fromFacility;

    public OutgoingDelivery(RestUri fromFacility, DeliveryItem... items) {
        this.fromFacility = fromFacility;
        this.getItems().addAll(Arrays.asList(items));
    }

}
