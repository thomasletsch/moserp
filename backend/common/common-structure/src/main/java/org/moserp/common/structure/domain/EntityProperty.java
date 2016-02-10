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

package org.moserp.common.structure.domain;

import lombok.Data;

import java.util.List;

@Data
public class EntityProperty {

    private String name;
    private String description;
    private EntityPropertyType type;
    private String format;
    private String uri;
    private int order = 0;
    private List<String> items;
    private BusinessEntity dependentEntity;
    private boolean required;
    private boolean readOnly;
}
