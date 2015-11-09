package org.moserp.common.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.hal.Jackson2HalModule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RestLinkContainer implements Serializable {

    @Transient
    @JsonProperty("_links")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(using = Jackson2HalModule.HalLinkListDeserializer.class)
    private List<RestLink> links = new ArrayList<>();

    @JsonIgnore
    public RestLink getSelf() {
        return getLink(Link.REL_SELF);
    }

    /**
     * Returns the link with the given rel.
     *
     * @param rel
     * @return the link with the given rel or {@literal null} if none found.
     */
    public RestLink getLink(String rel) {
        for (RestLink link : links) {
            if(link.getRel().equals(rel)) {
                return link;
            }
        }
        return null;
    }

    public void addLink(RestLink link) {
        links.add(link);
    }

}
