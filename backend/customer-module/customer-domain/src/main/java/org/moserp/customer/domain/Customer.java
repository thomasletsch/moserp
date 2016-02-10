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
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends EntityWithHistory {

    @ValueListKey("SalutationType")
    private String salutation;

    @ValueListKey("TitleType")
    private String title;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    private LocalDate dateOfBirth;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    private String mobile;

    @Size(max = 50)
    private String email;

    private Address address = new Address();

    @Size(max = 1024)
    private String additionalInformation;

    private Boolean active = Boolean.TRUE;

}
