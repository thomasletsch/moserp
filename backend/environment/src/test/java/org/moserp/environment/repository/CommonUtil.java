package org.moserp.environment.repository;

import org.moserp.environment.domain.UnitOfMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    @Autowired
    private UnitOfMeasurementRepository unitOfMeasurementRepository;

    public UnitOfMeasurement createAndSaveUnit() {
        UnitOfMeasurement unit = new UnitOfMeasurement("cm");
        return unitOfMeasurementRepository.save(unit);
    }

    public UnitOfMeasurement createAndSaveSpecialCharUnit() {
        UnitOfMeasurement unit = new UnitOfMeasurement("%");
        return unitOfMeasurementRepository.save(unit);
    }
}
