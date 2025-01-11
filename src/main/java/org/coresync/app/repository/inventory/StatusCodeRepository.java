package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.StatusCode;
import org.coresync.app.model.StatusCode$;
import org.coresync.app.model.StatusCodeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class StatusCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<StatusCode> getAllStatusCodes() {
        return jpaStreamer.stream(StatusCode.class).collect(Collectors.toList());
    }

    public List<StatusCodeDTO> getAllStatusCodeDTO() {
        return jpaStreamer.stream(StatusCode.class)
                .map(status -> new StatusCodeDTO(status.getId(), status.getDescription()))
                .collect(Collectors.toList());
    }

    public Optional<StatusCode> getStatusCodeDetail(int id) {
        return jpaStreamer.stream(StatusCode.class).filter(StatusCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public StatusCode addStatusCode(StatusCode statusCode) {
        if (statusCode == null) {
            throw new IllegalArgumentException("Status Code cannot be null");
        }

        entityManager.persist(statusCode);
        return statusCode;
    }

    @Transactional
    public StatusCode updateStatusCode(StatusCode statusCode) {
        int statusCdId = statusCode.getId();

        if (!validateStatusCodeExists(statusCdId)) {
            throw new IllegalArgumentException("Status Code ID " + statusCdId + " does not exists.");
        }

        return entityManager.merge(statusCode);
    }

    @Transactional
    public void deleteStatusCode(int id) {
        StatusCode statusCd = entityManager.find(StatusCode.class, id);

        if (!validateStatusCodeExists(id)) {
            throw new IllegalArgumentException("Status Code with ID " + id + " does not exists.");
        }

        entityManager.remove(statusCd);
    }

    public boolean validateStatusCodeExists(int id) {
        return jpaStreamer.stream(StatusCode.class).anyMatch(StatusCode$.id.equal(id));
    }

    public boolean validateStatusCodeDuplicate(String statusCd) {
        return jpaStreamer.stream(StatusCode.class).anyMatch(StatusCode$.description.equalIgnoreCase(statusCd));
    }
}
