package org.moserp.common.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HateoasLinkContainer implements Serializable {

    @JsonProperty("_links")
    private Map<String, Link> links = new HashMap<>();

    @JsonIgnore
    public String getLinkHref(String rel) {
        Link link = getLink(rel);
        if(link == null) {
            return null;
        }
        return link.getHref();
    }

    @JsonIgnore
    public Link getSelf() {
        return links.get(Link.REL_SELF);
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public Link getLink(String rel) {
        return links.get(rel);
    }

    public void addLink(String rel, Link link) {
        links.put(rel, link);
    }

}
