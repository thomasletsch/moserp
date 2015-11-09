package org.moserp.facility.rest;

import org.junit.Before;
import org.junit.Test;
import org.moserp.common.rest.BaseWebIntegrationTest;
import org.moserp.facility.domain.Facility;
import org.moserp.facility.repository.FacilityRepository;
import org.moserp.facility.repository.FacilityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.Assert.assertNotNull;

public class FacilityTest extends BaseWebIntegrationTest {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityUtil facilityUtil;

    @Before
    public void cleanRepository() {
        facilityUtil.setup();
    }

    @Test
    public void createFacility() {
        URI facilityUri = restTemplate.postForLocation(testEnvironment.createRestUri("/facilities"),
                facilityUtil.createFacility());
        assertNotNull("facilityUri", facilityUri);
        assertNotNull(facilityRepository.findOne(facilityUtil.getFacilityIdFromUri(facilityUri.toString())));
    }

    @Test
    public void retrieveFacility() {
        ResponseEntity<Object> response = restTemplate.postForEntity(testEnvironment.createRestUri("/facilities"),
                facilityUtil.createFacility(), null);
        String facilityUri = response.getHeaders().getLocation().toString();
        Facility facility = restTemplate.getForEntity(facilityUri, Facility.class).getBody();
        assertNotNull("facility", facility);
    }

}
