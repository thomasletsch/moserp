package org.moserp.environment.domain;

import lombok.*;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.IdentifiableEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnitOfMeasurement extends IdentifiableEntity {
    public static final String RESOURCE = "unitOfMeasurements";

    @ValueListKey("UnitOfMeasurementType")
    private String type;

    @NonNull
    private String code;

    private String description;

    @Override
    public String getDisplayName() {
        return code;
    }
}
