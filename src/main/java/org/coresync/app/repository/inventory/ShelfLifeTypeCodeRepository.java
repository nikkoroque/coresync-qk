package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.ShelfLifeTypeCode;
import org.coresync.app.model.ShelfLifeTypeCode$;
import org.coresync.app.model.ShelfLifeTypeCodeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShelfLifeTypeCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<ShelfLifeTypeCode> getShelfLifeTypeCodes() {
        return jpaStreamer.stream(ShelfLifeTypeCode.class).collect(Collectors.toList());
    }

    public List<ShelfLifeTypeCodeDTO> getShelfLifeTypeCodeNames() {
        return jpaStreamer.stream(ShelfLifeTypeCode.class).map(name -> new ShelfLifeTypeCodeDTO(name.getId(), name.getCode(), name.getDescription())).collect(Collectors.toList());
    }

    public Optional<ShelfLifeTypeCode> getShelfLifeTypeCodeDetail(int id) {
        return jpaStreamer.stream(ShelfLifeTypeCode.class).filter(ShelfLifeTypeCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public ShelfLifeTypeCode createShelfLifeTypeCode(ShelfLifeTypeCode shelfLifeTypeCode) {
        if (shelfLifeTypeCode == null) {
            throw new IllegalArgumentException("Shelf Life Type Code cannot be null");
        }

        entityManager.persist(shelfLifeTypeCode);
        return shelfLifeTypeCode;
    }

    public boolean validateShelfLifeTypeCodeExists(int id) {
        return jpaStreamer.stream(ShelfLifeTypeCode.class).anyMatch(ShelfLifeTypeCode$.id.equal(id));
    }

    public boolean validateShelfLifeTypeCodeDuplicate(String sltCd) {
        return jpaStreamer.stream(ShelfLifeTypeCode.class).anyMatch(ShelfLifeTypeCode$.code.equalIgnoreCase(sltCd));
    }

    @Transactional
    public ShelfLifeTypeCode updateShelfLifeTypeCode(ShelfLifeTypeCode shelfLifeTypeCode) {
        int sltCdId = shelfLifeTypeCode.getId();

        if (!validateShelfLifeTypeCodeExists(sltCdId)) {
            throw new IllegalArgumentException("Shelf Life Type Code ID " + sltCdId + " does not exist");
        }

        return entityManager.merge(shelfLifeTypeCode);
    }

    @Transactional
    public  void deleteShelfLifeTypeCode(int id) {
        ShelfLifeTypeCode sltCd = entityManager.find(ShelfLifeTypeCode.class, id);

        if (!validateShelfLifeTypeCodeExists(sltCd.getId())) {
            throw new IllegalArgumentException("Shelf Life Type Code ID " + id + " does not exist");
        }

        entityManager.remove(sltCd);
    }
}
