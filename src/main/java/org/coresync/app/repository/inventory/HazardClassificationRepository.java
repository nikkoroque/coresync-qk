package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.HazardClassification;
import org.coresync.app.model.HazardClassification$;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class HazardClassificationRepository {

    @Inject
    private JPAStreamer jpaStreamer;

    @Inject
    private EntityManager entityManager;

    private static final int PAGE_SIZE = 20;

    /**
     * Fetch paginated Hazard Classifications with sorting.
     *
     * @param page the 1-based page number.
     * @param sortBy the field to sort by (nullable, defaults to Id).
     * @param sortOrder the sort order: ASC or DESC (nullable, defaults to ASC).
     * @return Stream of paginated and sorted Hazard Classifications.
     */
    public Stream<HazardClassification> getPaginatedHazardClassification(long page, String sortBy, String sortOrder) {
        validatePageNumber(page);
        long offset = (page - 1) * PAGE_SIZE;

        Comparator<HazardClassification> comparator = Comparator.comparing(HazardClassification::getId);

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "id":
                    comparator = Comparator.comparing(HazardClassification::getId);
                    break;
                case "hzrdclscd":
                    comparator = Comparator.comparing(HazardClassification::getHzrdClsCd);
                    break;
                case "hzrdclsdesc":
                    comparator = Comparator.comparing(HazardClassification::getHzrdClsDesc);
                    break;
                case "creationdate":
                    comparator = Comparator.comparing(HazardClassification::getCreationDate);
                    break;
                case "createdbyuser":
                    comparator = Comparator.comparing(HazardClassification::getCreatedByUser);
                    break;
                case "lastupdatedate":
                    comparator = Comparator.comparing(HazardClassification::getLastUpdateDate);
                    break;
                case "lastupdatedbyuser":
                    comparator = Comparator.comparing(HazardClassification::getLastUpdatedByUser);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
            }
        }

        // Handle descending order
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        return  jpaStreamer.stream(HazardClassification.class).sorted(comparator).skip(offset).limit(PAGE_SIZE);
    }

    /**
     * Filter Hazard Classification based on multiple criteria.
     *
     * @param id Filter by Hazard Classification ID (nullable).
     * @param hzrdClsCd Filter by Hazard Classification Code.
     * @param hzrdClsDesc Filter by Hazard Classification description (nullable).
     * @param creationDate Filter by creation date.
     * @param createdByUser Filter by user who created the record.
     * @param lastUpdateDate Filter by last update date.
     * @param lastUpdatedByUser Filter by user who last updated the record.
     * @return Stream of filtered Hazard Classifications.
     */
    public Stream<HazardClassification> filterHazardClassification(
            Integer id,
            String hzrdClsCd,
            String hzrdClsDesc,
            Timestamp creationDate,
            String createdByUser,
            Timestamp lastUpdateDate,
            String lastUpdatedByUser
    ) {
        return jpaStreamer.stream(HazardClassification.class)
                .filter(id != null ? HazardClassification$.id.equal(id) : e -> true)
                .filter(hzrdClsCd != null ? HazardClassification$.hzrdClsCd.containsIgnoreCase(hzrdClsCd) : e -> true)
                .filter(hzrdClsDesc != null ? HazardClassification$.hzrdClsDesc.containsIgnoreCase(hzrdClsDesc) : e -> true)
                .filter(creationDate != null ?
                        HazardClassification$.creationDate.greaterOrEqual(creationDate)
                                .and(HazardClassification$.creationDate.lessThan(new Timestamp(creationDate.getTime() + 86400000))) // Add 1 day in milliseconds
                        : e -> true)
                .filter(createdByUser != null ? HazardClassification$.createdByUser.containsIgnoreCase(createdByUser) : e -> true)
                .filter(lastUpdateDate != null ?
                        HazardClassification$.lastUpdateDate.greaterOrEqual(lastUpdateDate)
                                .and(HazardClassification$.lastUpdateDate.lessThan(new Timestamp(lastUpdateDate.getTime() + 86400000)))
                        : e -> true)
                .filter(lastUpdatedByUser != null ? HazardClassification$.lastUpdatedByUser.containsIgnoreCase(lastUpdatedByUser) : e -> true);
    }


    /**
     * Fetch Hazard Classification details by ID.
     *
     * @param hzdClsCd the Hazard Classification ID.
     * @return Optional containing the Hazard Classification if found.
     */
    public Optional<HazardClassification> getHazardClassificationDetail(String hzdClsCd) {
        return jpaStreamer.stream(HazardClassification.class).filter(HazardClassification$.hzrdClsCd.equal(hzdClsCd)).findFirst();
    }

    /**
     * Add a new Hazard Classification.
     *
     * @param hazardClassification the Hazard Classification to add.
     * @return The persisted Hazard Classification.
     */
    @Transactional
    public HazardClassification addHazardClassification(HazardClassification hazardClassification) {
        entityManager.persist(hazardClassification);
        return hazardClassification;
    }

    /**
     * Update an existing Hazard Classification.
     *
     * @param hazardClassification the Hazard Classification to update.
     * @return The updated Hazard Classification.
     */
    @Transactional
    public HazardClassification updateHazardClassification(HazardClassification hazardClassification) {
        if (!hazardClassificationExists(hazardClassification.getHzrdClsCd())) {
            throw new IllegalArgumentException("Hazard Classification Code: " + hazardClassification.getHzrdClsCd() + " does not exist.");
        }
        return entityManager.merge(hazardClassification);
    }

    /**
     * Delete a Hazard Classification by Code.
     *
     * @param hzrdClsCd the Hazard Classification Code to delete.
     */
    @Transactional
    public void deleteHazardClassification(String hzrdClsCd) {
        // Fetch the entity using hzrdClsCd
        HazardClassification entity = jpaStreamer.stream(HazardClassification.class)
                .filter(HazardClassification$.hzrdClsCd.equal(hzrdClsCd))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Hazard Classification not found"));

        // Ensure the entity is managed before removing
        HazardClassification managedEntity = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }



    /**
     * Check if a Hazard Classification exists by hzrdClsCd using JPAStreamer.
     *
     * @param hzrdClsCd the Hazard Classification hzrdClsCd.
     * @return true if the Hazard Classification exists, false otherwise.
     */
    public boolean hazardClassificationExists(String hzrdClsCd) {
        return jpaStreamer.stream(HazardClassification.class)
                .anyMatch(HazardClassification$.hzrdClsCd.equal(hzrdClsCd));
    }

    /**
     * Validate the page number for pagination.
     *
     * @param page the page number to validate.
     */
    private void validatePageNumber(long page) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than or equal to 1");
        }
    }

}
