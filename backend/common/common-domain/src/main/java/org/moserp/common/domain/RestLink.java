package org.moserp.common.domain;

import org.springframework.hateoas.Link;

public class RestLink extends Link {

    public RestUri toUri() {
        return new RestUri(getHref());
    }
}
