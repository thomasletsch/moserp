package org.moserp.environment.domain;

import lombok.*;
import org.moserp.common.domain.DependentEntity;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ValueListItem extends DependentEntity {

    @NonNull
    private String value;

    @Override
    public String getDisplayName() {
        return value;
    }
}
