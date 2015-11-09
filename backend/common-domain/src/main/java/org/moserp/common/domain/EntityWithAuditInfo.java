package org.moserp.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class EntityWithAuditInfo extends IdentifiableEntity {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Order(Ordered.LOWEST_PRECEDENCE-3)
    private String createdBy;

    @CreatedDate
    @Order(Ordered.LOWEST_PRECEDENCE-2)
    private Instant createdDate;

    @LastModifiedBy
    @Order(Ordered.LOWEST_PRECEDENCE-1)
    private String lastModifiedBy;

    @LastModifiedDate
    @Order(Ordered.LOWEST_PRECEDENCE)
    private Instant lastModifiedDate;

}
