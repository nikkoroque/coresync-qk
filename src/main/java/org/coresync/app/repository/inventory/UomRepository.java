package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.PaginatedResult;
import org.coresync.app.model.UnitOfMeasure;
import org.coresync.app.model.UnitOfMeasure$;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UomRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;
    // TODO: For future release pagination
    private static final int PAGE_SIZE = 20;

    public List<UnitOfMeasure> getAllUomCodes() {
        return jpaStreamer.stream(UnitOfMeasure.class).collect(Collectors.toList());
    }

    public Optional<UnitOfMeasure> getUomCodeDetail(int id) {
        return jpaStreamer.stream(UnitOfMeasure.class).filter(UnitOfMeasure$.id.equal(id)).findFirst();
    }

    @Transactional
    public UnitOfMeasure addUomCode(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            throw new IllegalArgumentException("Unit Of Measure cannot be null");
        }
        entityManager.persist(unitOfMeasure);
        return unitOfMeasure;
    }


    @Transactional
    public UnitOfMeasure updateUomCode(UnitOfMeasure unitOfMeasure) {
        int uomId = unitOfMeasure.getId();
        //  Validation
        if (!validateUomCodeExists(uomId)) {
            throw new IllegalArgumentException("UOM Code with ID " + uomId + " does not exist.");
        }
        return entityManager.merge(unitOfMeasure);
    };

    @Transactional
    public void deleteUomCode(int id) {
        UnitOfMeasure uomCode = entityManager.find(UnitOfMeasure.class, id);
        // Validation
        if (!validateUomCodeExists(id)) {
            throw new IllegalArgumentException("UOM Code with ID " + id + " does not exist.");
        }
        entityManager.remove(uomCode);
    };

    public boolean validateUomCodeExists(int id) {
        return jpaStreamer.stream(UnitOfMeasure.class).anyMatch(UnitOfMeasure$.id.equal(id));
    };

    public boolean validateUomCodeDuplicate(String code) {
        return jpaStreamer.stream(UnitOfMeasure.class).anyMatch(UnitOfMeasure$.code.containsIgnoreCase(code));
    };

    // TODO: Future release pagination
    public PaginatedResult<UnitOfMeasure> getPaginatedUomCodes(long page, String sortBy, String sortORder) {
        long offset = (page - 1) * PAGE_SIZE;

        // Default sorting: sort by buId in ascending order
        Comparator<UnitOfMeasure> comparator = Comparator.comparing(UnitOfMeasure::getId);
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "code":
                    comparator = Comparator.comparing(UnitOfMeasure::getCode);
                    break;
                case "description":
                    comparator = Comparator.comparing(UnitOfMeasure::getDescription);
                    break;
                case "creationdate":
                    comparator = Comparator.comparing(UnitOfMeasure::getCreationDate);
                    break;
                case "createdbyuser":
                    comparator = Comparator.comparing(UnitOfMeasure::getCreatedByUser);
                    break;
                case "lastupdatedate":
                    comparator = Comparator.comparing(UnitOfMeasure::getLastUpdateDate);
                    break;
                case "lastupdatedbyuser":
                    comparator = Comparator.comparing(UnitOfMeasure::getLastUpdatedByUser);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sort by field: " + sortBy);
            }
        }

        // Handle descending
        if("desc".equalsIgnoreCase(sortORder)) {
            comparator = comparator.reversed();
        }

        // Fetch paginated data
        List<UnitOfMeasure> paginatedData = jpaStreamer.stream(UnitOfMeasure.class).sorted(comparator).skip(offset).limit(PAGE_SIZE).collect(Collectors.toList());

        // Fetch total count
        long totalItems = jpaStreamer.stream(UnitOfMeasure.class).count();

        return new PaginatedResult<>(paginatedData, totalItems);
    }

    // TODO: Future release filter
    public Stream<UnitOfMeasure> filterUomCodes(String code, String description, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdateDate, String lastUpdatedByUser) {
        return jpaStreamer.stream(UnitOfMeasure.class)
                .filter(code != null ? UnitOfMeasure$.code.containsIgnoreCase(code) : e -> true)
                .filter(description != null ? UnitOfMeasure$.description.containsIgnoreCase(description) : e -> true)
                .filter(creationDate != null ?
                        UnitOfMeasure$.creationDate.greaterOrEqual(creationDate)
                                .and(UnitOfMeasure$.creationDate.lessThan(creationDate.plusDays(1))) : e -> true)
                .filter(createdByUser != null ? UnitOfMeasure$.createdByUser.containsIgnoreCase(createdByUser) : e -> true)
                .filter(lastUpdateDate != null ?
                        UnitOfMeasure$.lastUpdateDate.greaterOrEqual(lastUpdateDate)
                                .and(UnitOfMeasure$.lastUpdateDate.lessThan(lastUpdateDate.plusDays(1))) : e -> true)
                .filter(lastUpdatedByUser != null ? UnitOfMeasure$.lastUpdatedByUser.containsIgnoreCase(lastUpdatedByUser) : e -> true);
    }

}
