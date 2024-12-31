package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.SectionCode;
import org.coresync.app.model.SectionCode$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SectionCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<SectionCode> getSectionCodes() {
        return jpaStreamer.stream(SectionCode.class).collect(Collectors.toList());
    }

    public Optional<SectionCode> getSectionCodeDetail(int id) {
        return jpaStreamer.stream(SectionCode.class).filter(SectionCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public SectionCode createSectionCode(SectionCode sectionCode) {
        if (sectionCode == null) {
            throw new IllegalArgumentException("Section Code cannot be null");
        }

        entityManager.persist(sectionCode);
        return sectionCode;
    }

    @Transactional
    public SectionCode updateSectionCode(SectionCode sectionCode) {
        int sectionCdId = sectionCode.getId();

        if (!validateSectionCodeExists(sectionCdId)) {
            throw new IllegalArgumentException("Section Code ID " + sectionCode + " does not exist");
        }

        return entityManager.merge(sectionCode);
    }

    @Transactional
    public void deleteSectionCode(int id) {
        SectionCode sectionCd = entityManager.find(SectionCode.class, id);

        if (!validateSectionCodeExists(id)) {
            throw new IllegalArgumentException("Section Code ID " + id + " does not exist");
        }

        entityManager.remove(sectionCd);
    }


    public boolean validateSectionCodeExists(int id) {
        return jpaStreamer.stream(SectionCode.class).anyMatch(SectionCode$.id.equal(id));
    }

    public boolean validateSectionCodeDuplicate(String sectionCd) {
        return jpaStreamer.stream(SectionCode.class).anyMatch(SectionCode$.code.containsIgnoreCase(sectionCd));
    }
}
