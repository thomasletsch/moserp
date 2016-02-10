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

package org.moserp.customer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithHistory;

import javax.validation.constraints.Size;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Address extends EntityWithHistory {

    @Size(max = 100)
    private String street;

    @Size(max = 100)
    private String city;

    @Size(max = 10)
    private String zip;

    @ValueListKey("Country")
    @Size(min = 2, max = 2)
    private String country;

}
