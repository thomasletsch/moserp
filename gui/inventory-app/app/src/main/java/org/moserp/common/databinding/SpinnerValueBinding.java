package org.moserp.common.databinding;

import org.moserp.common.domain.IdentifiableEntity;

public interface SpinnerValueBinding<RESOURCE extends IdentifiableEntity> {

    void setValue(RESOURCE resource);

    RESOURCE getValue();

}
