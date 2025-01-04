package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.InventoryCode;
import org.coresync.app.model.InventoryCode$;
import org.coresync.app.model.InventoryCodeDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventoryCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<InventoryCode> getInventoryCodes() {
        return jpaStreamer.stream(InventoryCode.class).collect(Collectors.toList());
    }

    public List<InventoryCodeDTO> getInventoryCodesDTO() {
        return jpaStreamer.stream(InventoryCode.class).map(inv -> new InventoryCodeDTO(inv.getId(), inv.getDescription())).collect(Collectors.toList());
    }

    public Optional<InventoryCode> getInventoryCodeDetail(int id) {
        return jpaStreamer.stream(InventoryCode.class).filter(InventoryCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public InventoryCode createInventoryCode(InventoryCode invCode) {
        if (invCode == null) {
            throw new IllegalArgumentException("Inventory Code cannot be null");
        }

        entityManager.persist(invCode);
        return invCode;
    }

    public boolean validateInventoryCodeExists(int id) {
        return jpaStreamer.stream(InventoryCode.class).anyMatch(InventoryCode$.id.equal(id));
    }

    public boolean validateInventoryCodeDuplicate(String code) {
        return jpaStreamer.stream(InventoryCode.class).anyMatch(InventoryCode$.description.equalIgnoreCase(code));
    }

    @Transactional
    public InventoryCode updateInventoryCode(InventoryCode invCode) {
        int id = invCode.getId();

        if (!validateInventoryCodeExists(id)) {
            throw new IllegalArgumentException("Inventory Code ID" + id + " does not exist");
        }

        return entityManager.merge(invCode);
    }

    @Transactional
    public void deleteInventoryCode(int id) {
        InventoryCode invCode = entityManager.find(InventoryCode.class, id);

        if (!validateInventoryCodeExists(invCode.getId())) {
            throw new IllegalArgumentException("Inventory Code ID" + id + " does not exist");
        }

        entityManager.remove(invCode);
    }
}
