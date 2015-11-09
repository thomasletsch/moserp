package org.moserp.facility.repository;

import org.moserp.common.rest.TestEnvironment;
import org.moserp.facility.domain.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import java.util.Map;

@Component
public class FacilityUtil {

    private static final String FACILITY_NAME = "Zentrale";
    private static final String MOBILE_FACILITY_NAME = "Car 1";

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private TestEnvironment testEnvironment;

    public void setup() {
        facilityRepository.deleteAll();
    }

    public Facility setupOneFacility() {
        facilityRepository.deleteAll();
        Facility saved = facilityRepository.save(createFacility());
        return saved;
    }

    public Facility createAndSaveMobileFacility() {
        Facility saved = facilityRepository.save(createMobileFacility());
        return saved;
    }

    public Facility createFacility() {
        return new Facility(FACILITY_NAME, "WAREHOUSE");
    }

    public Facility createMobileFacility() {
        return new Facility(MOBILE_FACILITY_NAME, "MOBILE_INVENTORY");
    }

    public String getFacilityIdFromUri(String facilityUri) {
        UriTemplate uriTemplate = new UriTemplate("/facilities/{facilityId}");
        Map<String, String> variables = uriTemplate.match(facilityUri);
        String facilityId = variables.get("facilityId");
        return facilityId;
    }

    public String getUri(Facility facility) {
        return testEnvironment.createRestUri("facilities/" + facility.getId());
    }
    public Link getLink(Facility facility, String rel) {
        return new Link(getUri(facility), rel);
    }
}
