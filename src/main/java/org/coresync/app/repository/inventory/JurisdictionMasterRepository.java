package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.JurisdictionMaster;
import org.coresync.app.model.JurisdictionMaster$;
import org.coresync.app.model.JurisdictionMasterDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class JurisdictionMasterRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<JurisdictionMaster> getJurisdictions() {
        return jpaStreamer.stream(JurisdictionMaster.class).collect(Collectors.toList());
    }

    public List<JurisdictionMasterDTO> getJurisdictionsDTO() {
        return jpaStreamer.stream(JurisdictionMaster.class).map(jrs -> new JurisdictionMasterDTO(jrs.getId(),
                jrs.getJurisdictionCode(), jrs.getJurisdictionName(), jrs.getJurisdictionTaxTypeCode(), jrs.getJurisdictionClassTypeCode())).collect(Collectors.toList());
    }

    public Optional<JurisdictionMaster> getJuridisctionDetail(int id) {
        return jpaStreamer.stream(JurisdictionMaster.class).filter(JurisdictionMaster$.id.equal(id)).findFirst();
    }

    @Transactional
    public JurisdictionMaster createJurisdiction(JurisdictionMaster jurisdictionMaster) {
        if (jurisdictionMaster == null) {
            throw new IllegalArgumentException("Jurisdiction master cannot be null");
        }

        entityManager.persist(jurisdictionMaster);
        return jurisdictionMaster;
    }

    public boolean validateJurisdictionExists(int id) {
        return jpaStreamer.stream(JurisdictionMaster.class).anyMatch(JurisdictionMaster$.id.equal(id));
    }

    public boolean validateJurisdictionDuplicate(String jrsCd) {
        return jpaStreamer.stream(JurisdictionMaster.class).anyMatch(JurisdictionMaster$.jurisdictionCode.equalIgnoreCase(jrsCd));
    }

    @Transactional
    public JurisdictionMaster updateJurisdiction(JurisdictionMaster jurisdictionMaster) {
        int jrsId = jurisdictionMaster.getId();

        if (!validateJurisdictionExists(jrsId)) {
            throw new IllegalArgumentException("Jurisdiction ID " + jrsId +" does not exist");
        }

        return entityManager.merge(jurisdictionMaster);
    }

    @Transactional
    public void deleteJurisdiction(int id) {
        JurisdictionMaster jrsId = entityManager.find(JurisdictionMaster.class, id);

        if (!validateJurisdictionExists(jrsId.getId())) {
            throw new IllegalArgumentException("Jurisdiction ID " + id +" does not exist");
        }

        entityManager.remove(jrsId);
    }
}
