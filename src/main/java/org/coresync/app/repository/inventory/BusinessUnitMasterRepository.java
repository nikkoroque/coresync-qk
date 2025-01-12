package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.BusinessUnitMaster;
import org.coresync.app.model.BusinessUnitMaster$;
import org.coresync.app.model.BusinessUnitMasterDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BusinessUnitMasterRepository {
    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    public List<BusinessUnitMaster> getBusinessUnits() {
        return jpaStreamer.stream(BusinessUnitMaster.class).collect(Collectors.toList());
    }

    public List<BusinessUnitMasterDTO> getBusinessUnitDTO() {
        return jpaStreamer.stream(BusinessUnitMaster.class)
                .map(unit -> new BusinessUnitMasterDTO(unit.getId(), unit.getBusinessUnitCode(), unit.getBusinessUnitName()))
                .collect(Collectors.toList());
    }

    public Optional<BusinessUnitMaster> getBusinessUnitDetail(int id) {
        return jpaStreamer.stream(BusinessUnitMaster.class)
                .filter(BusinessUnitMaster$.id.equal(id))
                .findFirst();
    }

    @Transactional
    public BusinessUnitMaster createBusinessUnit(BusinessUnitMaster unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Business Unit cannot be null");
        }

        entityManager.persist(unit);
        return unit;
    }

    public boolean validateBusinessUnitExists(int id) {
        return jpaStreamer.stream(BusinessUnitMaster.class)
                .anyMatch(BusinessUnitMaster$.id.equal(id));
    }

    public boolean validateBusinessUnitDuplicate(String code) {
        return jpaStreamer.stream(BusinessUnitMaster.class)
                .anyMatch(BusinessUnitMaster$.businessUnitCode.equalIgnoreCase(code));
    }

    @Transactional
    public BusinessUnitMaster updateBusinessUnit(BusinessUnitMaster unit) {
        int id = unit.getId();

        if (!validateBusinessUnitExists(id)) {
            throw new IllegalArgumentException("Business Unit ID " + id + " does not exist");
        }

        return entityManager.merge(unit);
    }

    @Transactional
    public void deleteBusinessUnit(int id) {
        BusinessUnitMaster unit = entityManager.find(BusinessUnitMaster.class, id);

        if (unit == null || !validateBusinessUnitExists(id)) {
            throw new IllegalArgumentException("Business Unit ID " + id + " does not exist");
        }

        entityManager.remove(unit);
    }
}
