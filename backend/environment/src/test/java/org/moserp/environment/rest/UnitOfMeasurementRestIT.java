package org.moserp.environment.rest;

import org.junit.Test;
import org.moserp.common.rest.BaseWebIntegrationTest;
import org.moserp.environment.domain.UnitOfMeasurement;
import org.moserp.environment.repository.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class UnitOfMeasurementRestIT extends BaseWebIntegrationTest {

    @Autowired
    private CommonUtil commonUtil;

    @Test
    public void testFindByCodeSpecialChars() {
        commonUtil.createAndSaveSpecialCharUnit();
        ResponseEntity<UnitOfMeasurement> entity = restTemplate.getForEntity(testEnvironment.createRestUri("unitOfMeasurements/search/findByCode?code={unit.code}"), UnitOfMeasurement.class, "%");
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
    }

    @Test
    public void testPost(){
        UnitOfMeasurement unit = new UnitOfMeasurement("cm");
        restTemplate.postForLocation(testEnvironment.createRestUri("/" + UnitOfMeasurement.RESOURCE), unit);
    }
}
