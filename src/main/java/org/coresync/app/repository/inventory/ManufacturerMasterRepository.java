package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.ManufacturerMaster;
import org.coresync.app.model.ManufacturerMaster$;
import org.coresync.app.model.ManufacturerMasterDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ManufacturerMasterRepository {
    @Inject
    JPAStreamer jpaStreamer;

    @Inject
    EntityManager entityManager;

    public List<ManufacturerMaster> getManufacturers() {
        return jpaStreamer.stream(ManufacturerMaster.class).collect(Collectors.toList());
    }

    public List<ManufacturerMasterDTO> getManufacturerDTOs() {
        return jpaStreamer.stream(ManufacturerMaster.class)
                .map(manufacturer -> new ManufacturerMasterDTO(manufacturer.getId(), manufacturer.getManufacturerCode(), manufacturer.getManufacturerName()))
                .collect(Collectors.toList());
    }

    public Optional<ManufacturerMaster> getManufacturerDetail(int id) {
        return jpaStreamer.stream(ManufacturerMaster.class)
                .filter(ManufacturerMaster$.id.equal(id))
                .findFirst();
    }

    @Transactional
    public ManufacturerMaster createManufacturer(ManufacturerMaster manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer cannot be null");
        }

        entityManager.persist(manufacturer);
        return manufacturer;
    }

    public boolean validateManufacturerExists(int id) {
        return jpaStreamer.stream(ManufacturerMaster.class)
                .anyMatch(ManufacturerMaster$.id.equal(id));
    }

    public boolean validateManufacturerDuplicate(String code) {
        return jpaStreamer.stream(ManufacturerMaster.class)
                .anyMatch(ManufacturerMaster$.manufacturerCode.equalIgnoreCase(code));
    }

    @Transactional
    public ManufacturerMaster updateManufacturer(ManufacturerMaster manufacturer) {
        int id = manufacturer.getId();

        if (!validateManufacturerExists(id)) {
            throw new IllegalArgumentException("Manufacturer ID " + id + " does not exist");
        }

        return entityManager.merge(manufacturer);
    }

    @Transactional
    public void deleteManufacturer(int id) {
        ManufacturerMaster manufacturer = entityManager.find(ManufacturerMaster.class, id);

        if (manufacturer == null || !validateManufacturerExists(id)) {
            throw new IllegalArgumentException("Manufacturer ID " + id + " does not exist");
        }

        entityManager.remove(manufacturer);
    }
}
