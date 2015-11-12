package org.moserp.common.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class RestUri implements Serializable {
    @NonNull
    private String uri;

    public static RestUri from(URI uri) {
        return new RestUri(uri.toString());
    }

    public RestUri slash(String pathElement) {
        return new RestUri(uri + "/" + pathElement);
    }

    public RestUri append(String additional) {
        return new RestUri(uri + additional);
    }

    public RestUri withoutPath() {
        try {
            URI parsedUri = new URI(uri);
            return new RestUri(parsedUri.getScheme() + "://" + parsedUri.getHost() + ((parsedUri.getPort() > -1) ? ":" + parsedUri.getPort() : "") + "/");
        } catch (URISyntaxException e) {
            log.warn("Could not parse uri " + uri, e);
        }
        return new RestUri(uri);
    }

    @Override
    public String toString() {
        return uri;
    }

}
