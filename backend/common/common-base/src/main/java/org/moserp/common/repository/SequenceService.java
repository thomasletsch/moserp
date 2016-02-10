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
