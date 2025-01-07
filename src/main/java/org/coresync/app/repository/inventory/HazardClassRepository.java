package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.HazardClass;
import org.coresync.app.model.HazardClass$;
import org.coresync.app.model.HazardClassDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class HazardClassRepository {
    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    public List<HazardClass> getHazardClasses() {
        return jpaStreamer.stream(HazardClass.class).collect(Collectors.toList());
    }

    public List<HazardClassDTO> getHazardClassesDTO() {
        return jpaStreamer.stream(HazardClass.class)
                .map(hzClass -> new HazardClassDTO(hzClass.getId(), hzClass.getClassCode(), hzClass.getDescription()))
                .collect(Collectors.toList());
    }

    public Optional<HazardClass> getHazardClassDetail(int id) {
        return jpaStreamer.stream(HazardClass.class)
                .filter(HazardClass$.id.equal(id))
                .findFirst();
    }

    @Transactional
    public HazardClass createHazardClass(HazardClass hazardClass) {
        if (hazardClass == null) {
            throw new IllegalArgumentException("Hazard Class cannot be null");
        }

        entityManager.persist(hazardClass);
        return hazardClass;
    }

    public boolean validateHazardClassExists(int id) {
        return jpaStreamer.stream(HazardClass.class)
                .anyMatch(HazardClass$.id.equal(id));
    }

    public boolean validateHazardClassDuplicate(String classCode) {
        return jpaStreamer.stream(HazardClass.class)
                .anyMatch(HazardClass$.classCode.equalIgnoreCase(classCode));
    }

    @Transactional
    public HazardClass updateHazardClass(HazardClass hazardClass) {
        int id = hazardClass.getId();

        if (!validateHazardClassExists(id)) {
            throw new IllegalArgumentException("Hazard Class ID " + id + " does not exist");
        }

        return entityManager.merge(hazardClass);
    }

    @Transactional
    public void deleteHazardClass(int id) {
        HazardClass hazardClass = entityManager.find(HazardClass.class, id);

        if (hazardClass == null || !validateHazardClassExists(id)) {
            throw new IllegalArgumentException("Hazard Class ID " + id + " does not exist");
        }

        entityManager.remove(hazardClass);
    }
}
