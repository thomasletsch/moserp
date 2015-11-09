package org.moserp.facility.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.facility.domain.Facility;

public interface FacilityRepository extends EntityRepository<Facility, String> {

    String URL = "facilities";

}
