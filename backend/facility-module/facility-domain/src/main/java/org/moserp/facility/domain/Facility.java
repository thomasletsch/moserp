package org.moserp.facility.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Facility extends EntityWithAuditInfo {

    private String name;

    @ValueListKey("FacilityType")
    private String type;


    @Override
    public String getDisplayName() {
        return name;
    }
}
