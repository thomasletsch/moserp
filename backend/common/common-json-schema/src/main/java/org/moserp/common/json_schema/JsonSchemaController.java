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

package org.moserp.common.json_schema;

import org.moserp.common.json_schema.domain.BusinessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RootResourceInformation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@BasePathAwareController
public class JsonSchemaController {

    public static final String BASE_PATH = "/schema";
    private static final String REPOSITORY = "/{repository}";

    private JsonSchemaBuilder jsonSchemaBuilder;

    @Autowired
    public JsonSchemaController(JsonSchemaBuilder jsonSchemaBuilder) {
        Assert.notNull(jsonSchemaBuilder, "ApplicationStructureBuilder must not be null!");

        this.jsonSchemaBuilder = jsonSchemaBuilder;
    }

    @RequestMapping(value = BASE_PATH + REPOSITORY, method = GET)
    public HttpEntity<BusinessEntity> schema(RootResourceInformation resourceInformation) {
        BusinessEntity schema = jsonSchemaBuilder.buildFor(resourceInformation.getDomainType());
        return new ResponseEntity<>(schema, HttpStatus.OK);
    }

}
