package org.moserp.common.rest;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class ExtendedResourceSupport extends ResourceSupport {

    public void addLinkHref(String rel, String href) {
        add(new Link(href, rel));
    }

    public String getLinkHref(String rel) {
        Link link = getLink(rel);
        if (link == null) {
            return null;
        }
        return link.getHref();
    }
}
