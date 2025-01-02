package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.JurisdictionClassTypeCode;
import org.coresync.app.model.JurisdictionClassTypeCode$;
import org.coresync.app.model.JurisdictionClassTypeCodeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class JurisdictionClassTypeCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<JurisdictionClassTypeCode> getJurisdictionClassTypeCodes() {
        return jpaStreamer.stream(JurisdictionClassTypeCode.class).collect(Collectors.toList());
    }

    public List<JurisdictionClassTypeCodeDTO> getJurisdictionClassTypeCodeDTO() {
        return jpaStreamer.stream(JurisdictionClassTypeCode.class).map(classType -> new JurisdictionClassTypeCodeDTO(classType.getId(),
                classType.getClassTypeCode())).collect(Collectors.toList());
    }

    public Optional<JurisdictionClassTypeCode> getJurisdictionClassTypeCodeDetail(int id) {
        return jpaStreamer.stream(JurisdictionClassTypeCode.class).filter(JurisdictionClassTypeCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public JurisdictionClassTypeCode createJurisdictionClassTypeCode(JurisdictionClassTypeCode jurisdictionClassTypeCode) {
        if (jurisdictionClassTypeCode == null) {
            throw new IllegalArgumentException("Jurisdiction Class Type Code cannot be null");
        }

        entityManager.persist(jurisdictionClassTypeCode);
        return jurisdictionClassTypeCode;
    }

    public boolean validateJurisdictionClassTypeCodeExists(int id) {
        return jpaStreamer.stream(JurisdictionClassTypeCode.class).anyMatch(JurisdictionClassTypeCode$.id.equal(id));
    }

    public boolean validateJurisdictionClassTypeCodeDuplicate(String clsTypCd) {
        return jpaStreamer.stream(JurisdictionClassTypeCode.class).anyMatch(JurisdictionClassTypeCode$.classTypeCode.equalIgnoreCase(clsTypCd));
    }

    @Transactional
    public JurisdictionClassTypeCode updateClassTypeCode(JurisdictionClassTypeCode jurisdictionClassTypeCode) {
        int jrsCdId = jurisdictionClassTypeCode.getId();

        if (!validateJurisdictionClassTypeCodeExists(jrsCdId)) {
            throw new IllegalArgumentException("Jurisdiction Class Type Code ID " + jrsCdId +" does not exist");
        }
        return entityManager.merge(jurisdictionClassTypeCode);
    }

    @Transactional
    public void deleteJurisdictionClassTypeCode(int id) {
        JurisdictionClassTypeCode jrsClsTypCdId = entityManager.find(JurisdictionClassTypeCode.class, id);

        if (!validateJurisdictionClassTypeCodeExists(jrsClsTypCdId.getId())) {
            throw new IllegalArgumentException("Jurisdiction Class Type Code ID " + id + " does not exist");
        }

        entityManager.remove(jrsClsTypCdId);
    }
}
