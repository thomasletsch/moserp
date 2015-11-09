package org.moserp.common.domain;

import lombok.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.hateoas.Identifiable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class IdentifiableEntity extends RestLinkContainer implements Displayable, Identifiable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @Order(Ordered.HIGHEST_PRECEDENCE)
    private String id;

    @Version
    private Long version;

    public String getDisplayName() {
        return id;
    }

}
