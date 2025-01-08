package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.HazardCode;
import org.coresync.app.model.HazardCode$;
import org.coresync.app.model.HazardCodeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class HazardCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    public List<HazardCode> getHazardCodes() {
        return jpaStreamer.stream(HazardCode.class).collect(Collectors.toList());
    }

    public List<HazardCodeDTO> getHazardCodesDTO() {
        return jpaStreamer.stream(HazardCode.class)
                .map(hzCode -> new HazardCodeDTO(hzCode.getId(), hzCode.getDescription1()))
                .collect(Collectors.toList());
    }

    public Optional<HazardCode> getHazardCodeDetail(int id) {
        return jpaStreamer.stream(HazardCode.class)
                .filter(HazardCode$.id.equal(id))
                .findFirst();
    }

    @Transactional
    public HazardCode createHazardCode(HazardCode hazardCode) {
        if (hazardCode == null) {
            throw new IllegalArgumentException("Hazard Code cannot be null");
        }

        entityManager.persist(hazardCode);
        return hazardCode;
    }

    public boolean validateHazardCodeExists(int id) {
        return jpaStreamer.stream(HazardCode.class)
                .anyMatch(HazardCode$.id.equal(id));
    }

    @Transactional
    public HazardCode updateHazardCode(HazardCode hazardCode) {
        int id = hazardCode.getId();

        if (!validateHazardCodeExists(id)) {
            throw new IllegalArgumentException("Hazard Code ID " + id + " does not exist");
        }

        return entityManager.merge(hazardCode);
    }

    @Transactional
    public void deleteHazardCode(int id) {
        HazardCode hazardCode = entityManager.find(HazardCode.class, id);

        if (hazardCode == null || !validateHazardCodeExists(id)) {
            throw new IllegalArgumentException("Hazard Code ID " + id + " does not exist");
        }

        entityManager.remove(hazardCode);
    }
}
