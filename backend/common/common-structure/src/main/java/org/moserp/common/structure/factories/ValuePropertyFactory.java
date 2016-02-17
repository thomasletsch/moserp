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

package org.moserp.common.structure.factories;

import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.common.structure.PropertyFactoryContext;
import org.moserp.common.structure.domain.EntityProperty;
import org.moserp.common.structure.domain.EntityPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ValuePropertyFactory extends BasicPropertyFactory {

    private ModuleRegistry moduleRegistry;

    @Autowired
    public ValuePropertyFactory(ModuleRegistry moduleRegistry) {
        this.moduleRegistry = moduleRegistry;
    }

    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return isAnnotationPresent(context, ValueListKey.class);
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.VALUE);
        property.setUri(calculateValueListUri(context));
        return property;
    }

    private String calculateValueListUri(PropertyFactoryContext context) {
        ValueListKey valueListValue = getAnnotation(context, ValueListKey.class);
        String valueListKey = valueListValue.value();
        final BaseUri baseUri = new BaseUri("http://" + moduleRegistry.getModuleForResource("valueLists") + "/valueLists");
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        return builder.pathSegment(valueListKey).pathSegment("values").build().toUriString();
    }
}
