package org.moserp.inventory.domain;

import lombok.*;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.product.domain.ProductInstance;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class InventoryTransfer extends EntityWithAuditInfo {

    @NonNull
    private ProductInstance productInstance;

    @NonNull
    @ResourceAssociation("facilities")
    private RestUri fromFacility;

    @NonNull
    @ResourceAssociation("facilities")
    private RestUri toFacility;

    @NonNull
    private Quantity quantity;

    @ValueListKey("InventoryTransferStatus")
    private String status;

}
