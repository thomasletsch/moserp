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
