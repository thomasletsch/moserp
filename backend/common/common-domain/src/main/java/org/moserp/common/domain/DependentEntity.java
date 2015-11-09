package org.moserp.common.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class DependentEntity extends RestLinkContainer implements Displayable {

    private static final long serialVersionUID = 1L;

    public String getDisplayName() {
        return "";
    }

}
