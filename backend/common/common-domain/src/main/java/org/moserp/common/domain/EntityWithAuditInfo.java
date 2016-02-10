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

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class EntityWithAuditInfo extends IdentifiableEntity {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Order(Ordered.LOWEST_PRECEDENCE-3)
    private String createdBy;

    @CreatedDate
    @Order(Ordered.LOWEST_PRECEDENCE-2)
    private Instant createdDate;

    @LastModifiedBy
    @Order(Ordered.LOWEST_PRECEDENCE-1)
    private String lastModifiedBy;

    @LastModifiedDate
    @Order(Ordered.LOWEST_PRECEDENCE)
    private Instant lastModifiedDate;

}
