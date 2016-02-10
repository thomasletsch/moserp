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
