package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.model.BusinessUnit$;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class BusinessUnitRepository {

    @Inject
    private JPAStreamer jpaStreamer;

    @Inject
    private EntityManager entityManager;

    private static final int PAGE_SIZE = 100;

    public class PaginatedResult<T> {
        private List<T> data;
        private long totalItems;

        public PaginatedResult(List<T> data, long totalItems) {
            this.data = data;
            this.totalItems = totalItems;
        }

        public List<T> getData() {
            return data;
        }

        public long getTotalItems() {
            return totalItems;
        }
    }

    /**
     * Fetch paginated Business Units with sorting.
     *
     * @param page the 1-based page number.
     * @param sortBy the field to sort by (nullable, defaults to buId).
     * @param sortOrder the sort order: ASC or DESC (nullable, defaults to ASC).
     * @return Stream of paginated and sorted Business Units.
     */
    public PaginatedResult<BusinessUnit> getPaginatedBusinessUnit(long page, String sortBy, String sortOrder) {
        validatePageNumber(page);
        long offset = (page - 1) * PAGE_SIZE;

        // Default sorting: sort by buId in ascending order
        Comparator<BusinessUnit> comparator = Comparator.comparing(BusinessUnit::getBuId);

        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "buid":
                    comparator = Comparator.comparing(BusinessUnit::getBuId);
                    break;
                case "budesc":
                    comparator = Comparator.comparing(BusinessUnit::getBuDesc);
                    break;
                case "buzip":
                    comparator = Comparator.comparing(BusinessUnit::getBuZip);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
            }
        }

        // Handle descending order
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        // Fetch paginated data
        List<BusinessUnit> paginatedData = jpaStreamer.stream(BusinessUnit.class)
                .sorted(comparator)
                .skip(offset)
                .limit(PAGE_SIZE)
                .collect(Collectors.toList());

        // Fetch total count
        long totalItems = jpaStreamer.stream(BusinessUnit.class).count();

        return new PaginatedResult<>(paginatedData, totalItems);
    }

    /**
     * Filter Business Units based on multiple criteria.
     *
     * @param buId Filter by Business Unit ID (nullable).
     * @param buDesc Filter by Business Unit description (nullable).
     * @param buGln Filter by GLN (nullable).
     * @param buCity Filter by city (nullable).
     * @param buState Filter by state (nullable).
     * @param buZip Filter by zip code (nullable).
     * @param buCountry Filter by country (nullable).
     * @return Stream of filtered Business Units.
     */
    public Stream<BusinessUnit> filterBusinessUnits(
            Integer buId,
            String buDesc,
            String buGln,
            String buCity,
            String buState,
            String buZip,
            String buCountry
    ) {
        return jpaStreamer.stream(BusinessUnit.class)
                .filter(buId != null ? BusinessUnit$.buId.equal(buId) : e -> true)
                .filter(buDesc != null ? BusinessUnit$.buDesc.containsIgnoreCase(buDesc) :e -> true)
                .filter(buGln != null ? BusinessUnit$.buGln.containsIgnoreCase(buGln) : e -> true)
                .filter(buCity != null ? BusinessUnit$.buCity.containsIgnoreCase(buCity) : e -> true)
                .filter(buState != null ? BusinessUnit$.buState.containsIgnoreCase(buState) : e -> true)
                .filter(buZip != null ? BusinessUnit$.buZip.contains(buZip) : e -> true)
                .filter(buCountry != null ? BusinessUnit$.buCountry.containsIgnoreCase(buCountry) : e -> true);
    }

    /**
     * Fetch Business Unit details by ID.
     *
     * @param buId the Business Unit ID.
     * @return Optional containing the Business Unit if found.
     */
    public Optional<BusinessUnit> getBusinessUnitDetail(int buId) {
        return jpaStreamer.stream(BusinessUnit.class)
                .filter(BusinessUnit$.buId.equal(buId))
                .findFirst();
    }

    /**
     * Add a new Business Unit.
     *
     * @param businessUnit the Business Unit to add.
     * @return The persisted Business Unit.
     */
    @Transactional
    public BusinessUnit addBusinessUnit(BusinessUnit businessUnit) {
        entityManager.persist(businessUnit);
        return businessUnit;
    }

    /**
     * Update an existing Business Unit.
     *
     * @param businessUnit the Business Unit to update.
     * @return The updated Business Unit.
     */
    @Transactional
    public BusinessUnit updateBusinessUnit(BusinessUnit businessUnit) {
        if (!businessUnitExists(businessUnit.getBuId())) {
            throw new IllegalArgumentException("Business Unit with ID " + businessUnit.getBuId() + " does not exist.");
        }
        return entityManager.merge(businessUnit);
    }

    /**
     * Delete a Business Unit by ID.
     *
     * @param buId the Business Unit ID to delete.
     */
    @Transactional
    public void deleteBusinessUnit(int buId) {
        BusinessUnit businessUnit = entityManager.find(BusinessUnit.class, buId);
        if (businessUnit == null) {
            throw new IllegalArgumentException("Business Unit with ID " + buId + " does not exist.");
        }
        entityManager.remove(businessUnit);
    }

    /**
     * Check if a Business Unit exists by ID.
     *
     * @param buId the Business Unit ID.
     * @return true if the Business Unit exists, false otherwise.
     */
    public boolean businessUnitExists(int buId) {
        return jpaStreamer.stream(BusinessUnit.class).anyMatch(BusinessUnit$.buId.equal(buId));
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
