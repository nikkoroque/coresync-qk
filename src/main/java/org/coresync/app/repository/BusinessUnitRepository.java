package org.coresync.app.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.model.BusinessUnit$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class BusinessUnitRepository {

    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    private static final int PAGE_SIZE = 20;

    // GET ALL
    public List<BusinessUnit> getAllBusinessUnits() {
        return jpaStreamer.stream(BusinessUnit.class).collect(Collectors.toList());
    };

    // PAGINATED GET ALL
    public Stream<BusinessUnit> getPaginatedBusinessUnit(long page) {
        long zeroBasedPage = page - 1; // Convert 1-based page to 0-based
        if (zeroBasedPage < 0) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }

        return jpaStreamer.stream(BusinessUnit.class)
                .sorted(BusinessUnit$.buId)
                .skip(zeroBasedPage * PAGE_SIZE)
                .limit(PAGE_SIZE);
    }

    public Optional<BusinessUnit> getBusinessUnitDetail(int buId) {
        return jpaStreamer.stream(BusinessUnit.class).filter(BusinessUnit$.buId.equal(buId)).findFirst();
    }

    @Transactional
    public BusinessUnit addBusinessUnit(BusinessUnit businessUnit) {
        entityManager.persist(businessUnit);
        return businessUnit;
    }

    @Transactional
    public BusinessUnit updateBusinessUnit(BusinessUnit businessUnit) {
        if (entityManager.find(BusinessUnit.class, businessUnit.getBuId()) == null) {
            throw new IllegalArgumentException("BusinessUnit with ID " + businessUnit.getBuId() + " does not exist.");
        }
        return entityManager.merge(businessUnit);
    }

    @Transactional
    public void deleteBusinessUnit(int buId) {
        BusinessUnit businessUnit = entityManager.find(BusinessUnit.class, buId);
        if (businessUnit == null) {
            throw new IllegalArgumentException("BusinessUnit with ID " + buId + " does not exist.");
        }
        entityManager.remove(businessUnit); // Delete the entity
    }

    public boolean businessUnitExists(int buId) {
        return entityManager.find(BusinessUnit.class, buId) != null;
    }

}
