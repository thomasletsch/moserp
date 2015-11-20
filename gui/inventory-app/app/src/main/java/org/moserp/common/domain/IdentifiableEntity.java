package org.moserp.common.domain;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.moserp.common.rest.HateoasLinkContainer;
import org.moserp.common.rest.RestUri;

public class IdentifiableEntity extends HateoasLinkContainer {

    public IdentifiableEntity() {
    }

    @JsonIgnore
    public String getId() {
        if(getSelf() == null || getSelf().getHref() == null || !getSelf().getHref().contains("/")) {
            Log.w("Entity", "Could not determine id. Self: " + getSelf());
            return null;
        }
        RestUri uri = new RestUri(getSelf().getHref());
        return uri.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifiableEntity that = (IdentifiableEntity) o;

        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "id=" + getId();
    }
}
