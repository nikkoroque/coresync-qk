package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.TrackItemCode;
import org.coresync.app.model.TrackItemCode$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TrackItemCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<TrackItemCode> getAllTrackItemCodes() {
        return jpaStreamer.stream(TrackItemCode.class).collect(Collectors.toList());
    }

    public Optional<TrackItemCode> getTrackItemCodeDetail(int id) {
        return jpaStreamer.stream(TrackItemCode.class).filter(TrackItemCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public TrackItemCode addTrackItemCode(TrackItemCode trackItemCode) {
        if (trackItemCode == null) {
            throw new IllegalArgumentException("Track Item Code cannot be null");
        }

        entityManager.persist(trackItemCode);
        return trackItemCode;
    }

    @Transactional
    public TrackItemCode updateTrackItemCode(TrackItemCode trackItemCode) {
        int itemCodeId = trackItemCode.getId();
        // Validate
        if (!validateTrackItemCodeExists(itemCodeId)) {
            throw new IllegalArgumentException("Track Item Code with ID " + itemCodeId + " does not exist.");
        }
        return entityManager.merge(trackItemCode);
    }

    @Transactional
    public void deleteTrackItemCode(int id) {
        TrackItemCode itemCodeId = entityManager.find(TrackItemCode.class, id);
        // Validate
        if (!validateTrackItemCodeExists(id)) {
            throw new IllegalArgumentException("Track Item Code with ID " + id + " does not exist.");
        }
        entityManager.remove(itemCodeId);
    }

    public boolean validateTrackItemCodeExists(int id) {
        return jpaStreamer.stream(TrackItemCode.class).anyMatch(TrackItemCode$.id.equal(id));
    }

    public boolean validateTrackItemCodeDuplicate(String code) {
        return jpaStreamer.stream(TrackItemCode.class).anyMatch(TrackItemCode$.code.containsIgnoreCase(code));
    }
}
