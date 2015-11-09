package org.moserp.environment.domain;

import lombok.*;
import org.moserp.common.domain.IdentifiableEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends IdentifiableEntity {

    public static final String RESOURCE = "users";

    @NonNull
    private String name;

    @Override
    public String getDisplayName() {
        return name;
    }
}
