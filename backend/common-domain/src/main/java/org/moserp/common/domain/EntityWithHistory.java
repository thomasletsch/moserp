package org.moserp.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class EntityWithHistory extends EntityWithAuditInfo {

    private LocalDate validFrom;
    private LocalDate validTo;
    private Boolean active = Boolean.TRUE;

}
