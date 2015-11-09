package org.moserp.common.domain;

import lombok.*;

import java.io.Serializable;
import java.net.URI;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
public class RestUri implements Serializable {

    @NonNull
    private String uri;

    public RestUri slash(String pathElement) {
        return new RestUri(uri + "/" + pathElement);
    }

    public RestUri append(String additional) {
        return new RestUri(uri + additional);
    }

    @Override
    public String toString() {
        return uri;
    }

    public static RestUri from(URI uri) {
        return new RestUri(uri.toString());
    }
}
