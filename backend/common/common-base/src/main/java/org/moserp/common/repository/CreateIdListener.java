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

package org.moserp.common.repository;

import org.moserp.common.domain.IdentifiableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;

@Component
public class CreateIdListener implements ApplicationListener<MongoMappingEvent<?>> {

    @Autowired
    private SequenceService sequenceService;

    @Override
    public void onApplicationEvent(MongoMappingEvent<?> event) {
        if (!(event instanceof BeforeSaveEvent)) {
            return;
        }
        if (!IdentifiableEntity.class.isAssignableFrom(event.getSource().getClass())) {
            return;
        }
        IdentifiableEntity source = (IdentifiableEntity) event.getSource();
        if (source.getId() != null) {
            return;
        }
        DBObject dbObject = event.getDBObject();
        String id = sequenceService.getNextIt(event.getSource().getClass());
        source.setId(id);
        dbObject.put("_id", id);
    }

}
