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
