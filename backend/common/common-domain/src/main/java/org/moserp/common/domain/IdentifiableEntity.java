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
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.hateoas.Identifiable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class IdentifiableEntity extends RestLinkContainer implements Displayable, Identifiable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @Order(Ordered.HIGHEST_PRECEDENCE)
    private String id;

    @Version
    private Long version;

    public String getDisplayName() {
        return id;
    }

}
