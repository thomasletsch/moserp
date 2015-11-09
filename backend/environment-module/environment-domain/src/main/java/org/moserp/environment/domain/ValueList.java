package org.moserp.environment.domain;

import lombok.*;
import org.moserp.common.domain.EntityWithAuditInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "values")
public class ValueList extends EntityWithAuditInfo {

    @NonNull
    private String key;

    private List<ValueListItem> values = new ArrayList<>();

    public void addValue(ValueListItem item) {
        values.add(item);
    }

    @Override
    public String getDisplayName() {
        return key;
    }
}
