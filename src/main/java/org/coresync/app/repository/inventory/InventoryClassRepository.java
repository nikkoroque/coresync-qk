package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.InventoryClass;
import org.coresync.app.model.InventoryClass$;
import org.coresync.app.model.InventoryClassDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class InventoryClassRepository {
    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    public List<InventoryClass> getInventoryClasses() {
        return jpaStreamer.stream(InventoryClass.class).collect(Collectors.toList());
    }

    public List<InventoryClassDTO> getInventoryClassesDTO() {
        return jpaStreamer.stream(InventoryClass.class)
                .map(invClass -> new InventoryClassDTO(invClass.getId(), invClass.getDescription()))
                .collect(Collectors.toList());
    }

    public Optional<InventoryClass> getInventoryClassDetail(int id) {
        return jpaStreamer.stream(InventoryClass.class)
                .filter(InventoryClass$.id.equal(id))
                .findFirst();
    }

    @Transactional
    public InventoryClass createInventoryClass(InventoryClass invClass) {
        if (invClass == null) {
            throw new IllegalArgumentException("Inventory Class cannot be null");
        }

        entityManager.persist(invClass);
        return invClass;
    }

    public boolean validateInventoryClassExists(int id) {
        return jpaStreamer.stream(InventoryClass.class)
                .anyMatch(InventoryClass$.id.equal(id));
    }

    public boolean validateInventoryClassDuplicate(String description) {
        return jpaStreamer.stream(InventoryClass.class)
                .anyMatch(InventoryClass$.description.equalIgnoreCase(description));
    }

    @Transactional
    public InventoryClass updateInventoryClass(InventoryClass invClass) {
        int id = invClass.getId();

        if (!validateInventoryClassExists(id)) {
            throw new IllegalArgumentException("Inventory Class ID " + id + " does not exist");
        }

        return entityManager.merge(invClass);
    }

    @Transactional
    public void deleteInventoryClass(int id) {
        InventoryClass invClass = entityManager.find(InventoryClass.class, id);

        if (invClass == null || !validateInventoryClassExists(id)) {
            throw new IllegalArgumentException("Inventory Class ID " + id + " does not exist");
        }

        entityManager.remove(invClass);
    }
}
