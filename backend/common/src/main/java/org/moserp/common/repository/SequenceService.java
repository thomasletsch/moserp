package org.moserp.common.repository;

import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SequenceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public synchronized String getNextIt(Class<?> entityClass) {
        String key = entityClass.getCanonicalName();
        Sequence sequence = mongoTemplate.findById(key, Sequence.class);
        if (sequence != null) {
            sequence.increment();
        } else {
            sequence = new Sequence(key, 0L);
        }
        mongoTemplate.save(sequence);
        log.debug("Next Sequence from " + key + ": " + sequence.getCurrent());
        return sequence.getCurrent().toString();
    }

    public void resetSequence(Class<?> entityClass) {
        String key = entityClass.getCanonicalName();
        Query query = new Query(Criteria.where("_id").is(key));
        mongoTemplate.remove(query, Sequence.class);
    }

}
