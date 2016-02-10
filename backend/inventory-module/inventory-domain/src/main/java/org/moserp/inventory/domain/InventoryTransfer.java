/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
