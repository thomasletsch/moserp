/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
        if (uri.endsWith("/")) {
            return new RestUri(uri + pathElement);
        }
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
