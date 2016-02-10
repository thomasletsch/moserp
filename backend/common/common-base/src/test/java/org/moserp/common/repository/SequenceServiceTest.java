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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moserp.MongoDbConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestConfiguration.class, MongoDbConfiguration.class })
public class SequenceServiceTest {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private TestEntityRepository testEntityRepository;

    @Test
    public void testIncrementIdInit() {
        testEntityRepository.deleteAll();
        sequenceService.resetSequence(TestEntityWithAuditInfo.class);
        String id = sequenceService.getNextIt(TestEntityWithAuditInfo.class);
        assertEquals("id", "0", id);
    }

    @Test
    public void testIncrementIdTwoTimes() {
        testEntityRepository.deleteAll();
        sequenceService.resetSequence(TestEntityWithAuditInfo.class);
        String id = sequenceService.getNextIt(TestEntityWithAuditInfo.class);
        assertEquals("id", "0", id);
        id = sequenceService.getNextIt(TestEntityWithAuditInfo.class);
        assertEquals("id", "1", id);
    }

}
