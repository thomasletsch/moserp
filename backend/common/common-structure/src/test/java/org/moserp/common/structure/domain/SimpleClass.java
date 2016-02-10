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

package org.moserp.common.structure.domain;

import lombok.Data;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
public class SimpleClass extends EntityWithAuditInfo {

    private int intProperty;
    private Boolean booleanProperty;
    private String stringProperty;
    private TestEnum enumProperty;
    private LocalDate localDateProperty;
    private Instant instantProperty;
    @ValueListKey("listKey")
    private String valueProperty;
    private DependentEntityClass dependentEntityProperty;
    private List<DependentEntityClass> dependentEntityListProperty;
    private OtherRepositoryClass otherRepositoryClass;
    private Quantity ownSerializerProperty;
    @ResourceAssociation("unitOfMeasurements")
    private RestUri resourceAssociationProperty;

    public String getStringMethod() {
        return null;
    }

}
