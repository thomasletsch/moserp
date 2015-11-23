package org.moserp.customer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithHistory;

import javax.validation.constraints.Size;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Address extends EntityWithHistory {

    @Size(max = 100)
    private String street;

    @Size(max = 100)
    private String city;

    @Size(max = 10)
    private String zip;

    @ValueListKey("Country")
    @Size(min = 2, max = 2)
    private String country;

}
