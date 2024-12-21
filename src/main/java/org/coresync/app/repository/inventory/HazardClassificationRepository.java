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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class HazardClassificationRepository {

    @Inject
    private JPAStreamer jpaStreamer;

    @Inject
    private EntityManager entityManager;

    private static final int PAGE_SIZE = 20;


    /**
     * Fetch all Hazard Classification.
     *
     * @return List of all Hazard Classification.
     */
    public List<HazardClassification> getAllHazardClassifications() {
        return jpaStreamer.stream(HazardClassification.class).collect(Collectors.toList());
    }

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
                case "hzrd_cls_desc":
                    comparator = Comparator.comparing(HazardClassification::getHzrdClsDesc);
                    break;
                case "hzrd_cls_cd":
                    comparator = Comparator.comparing(HazardClassification::getHzrdClsCd);
                    break;
                case "creation_date":
                    comparator = Comparator.comparing(HazardClassification::getCreationDate);
                    break;
                case "created_by_user":
                    comparator = Comparator.comparing(HazardClassification::getCreatedByUser);
                    break;
                case "last_update_date":
                    comparator = Comparator.comparing(HazardClassification::getCreatedByUser);
                    break;
                case "last_updated_by_user":
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
     * @param id the Hazard Classification ID.
     * @return Optional containing the Hazard Classification if found.
     */
    public Optional<HazardClassification> getHazardClassificationDetail(int id) {
        return jpaStreamer.stream(HazardClassification.class).filter(HazardClassification$.id.equal(id)).findFirst();
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
            throw new IllegalArgumentException("Hazard Classification with ID " + hazardClassification.getHzrdClsCd() + " does not exist.");
        }
        return entityManager.merge(hazardClassification);
    }

    /**
     * Delete a Hazard Classification by ID.
     *
     * @param id the Hazard Classification ID to delete.
     */
    @Transactional
    public void deleteHazardClassification(int id) {
        HazardClassification hazardClassification = entityManager.find(HazardClassification.class, id);
        if (hazardClassification == null) {
            throw new IllegalArgumentException("Hazard Classification with ID " + hazardClassification.getId() + " does not exist.");
        }
        entityManager.remove(hazardClassification);
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
