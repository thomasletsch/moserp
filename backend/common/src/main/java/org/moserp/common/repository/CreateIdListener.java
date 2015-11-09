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
