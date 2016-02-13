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

package org.moserp.common.json_schema.factories;

import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.json_schema.PropertyFactoryContext;
import org.moserp.common.json_schema.domain.EntityProperty;
import org.moserp.common.json_schema.domain.EntityPropertyType;
import org.moserp.common.modules.ModuleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ValuePropertyJsonSchemaFactory extends BasicPropertyFactory {
    private ModuleRegistry moduleRegistry;
    private RepositoryRestConfiguration config;

    @Autowired
    public ValuePropertyJsonSchemaFactory(ModuleRegistry moduleRegistry, RepositoryRestConfiguration config) {
        this.moduleRegistry = moduleRegistry;
        this.config = config;
    }


    @Override
    public boolean appliesTo(PropertyFactoryContext context) {
        return isAnnotationPresent(context, ValueListKey.class);
    }

    @Override
    public EntityProperty create(PropertyFactoryContext context) {
        EntityProperty property = createPropertyWithBasicValues(context);
        property.setType(EntityPropertyType.STRING);
        property.setHref(calculateValueListUri(context));
        return property;
    }

    private String calculateValueListUri(PropertyFactoryContext context) {
        ValueListKey valueListValue = getAnnotation(context, ValueListKey.class);
        String valueListKey = valueListValue.value();
        final BaseUri baseUri = new BaseUri("http://ENVIRONMENT-MODULE/valueLists");
        UriComponentsBuilder builder = baseUri.getUriComponentsBuilder();
        return builder.pathSegment(valueListKey).pathSegment("values").build().toUriString();
    }

}
