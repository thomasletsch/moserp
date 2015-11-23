package org.moserp.billing.domain;

import lombok.*;
import org.moserp.common.domain.DependentEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class YearBill extends DependentEntity {

    private List<Bill> bills = new ArrayList<>();

    @NonNull
    private String year;

}
