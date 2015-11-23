package org.moserp.customer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moserp.common.domain.EntityWithAuditInfo;
import org.moserp.environment.domain.User;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomerActivity extends EntityWithAuditInfo {

    private Instant activityDate = Instant.now();
    private Customer customer;
    private String activityType;
    private User user;

    @Size(max = 1024)
    private String remarks;

    public CustomerActivity() {
        setActivityType(getClass().getSimpleName());
    }

}
