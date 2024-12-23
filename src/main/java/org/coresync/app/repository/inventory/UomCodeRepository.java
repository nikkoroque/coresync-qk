package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.UomCode;
import org.coresync.app.model.UomCode$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UomCodeRepository {

    @Inject
    private JPAStreamer jpaStreamer;
    @Inject
    private EntityManager entityManager;

    public List<UomCode> getAllUomCodes() {
        return jpaStreamer.stream(UomCode.class).collect(Collectors.toList());
    }

    public Optional<UomCode> getUomCodeDetail(String uomCd) {
        return jpaStreamer.stream(UomCode.class).filter(UomCode$.uomCd.equal(uomCd)).findFirst();
    }

    @Transactional
    public UomCode addUomCode(UomCode uomCode) {
        entityManager.persist(uomCode);
        return uomCode;
    }

    @Transactional
    public UomCode updateUomCode(UomCode uomCode) {
        if (!uomCodeExists(uomCode.getUomCd())) {
            throw new IllegalArgumentException("UOM Code with ID " + uomCode.getUomCd() + " does not exist.");
        }
        return entityManager.merge(uomCode);
    }

    @Transactional
    public void deleteUomCode(String uomCd) {
        // Find the entity by its primary key
        UomCode uomCode = entityManager.find(UomCode.class, uomCd);

        if (uomCode == null) {
            throw new IllegalArgumentException("UOM Code with ID " + uomCd + " does not exist.");
        }

        // Remove the entity
        entityManager.remove(uomCode);
    }


    public boolean uomCodeExists(String uomCd) {
        return jpaStreamer.stream(UomCode.class).anyMatch(UomCode$.uomCd.equal(uomCd));
    }
}
