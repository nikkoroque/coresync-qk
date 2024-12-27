package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.SupplierMaster;
import org.coresync.app.model.SupplierMaster$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupplierMasterRepository {
    @Inject
    private JPAStreamer jpaStreamer;
    @Inject
    private EntityManager entityManager;

    public List<SupplierMaster> getAllSuppliers() {
        return jpaStreamer.stream(SupplierMaster.class).collect(Collectors.toList());
    }

    public Optional<SupplierMaster> getSupplierDetail(int id) {
        return jpaStreamer.stream(SupplierMaster.class).filter(SupplierMaster$.id.equal(id)).findFirst();
    }

    @Transactional
    public SupplierMaster addSupplier(SupplierMaster supplierMaster) {
        if (supplierMaster == null) {
            throw new IllegalArgumentException("Supplier cannot be null");
        }

        entityManager.persist(supplierMaster);
        return supplierMaster;
    }

    @Transactional
    public SupplierMaster updateSupplier(SupplierMaster supplierMaster) {
        int supId = supplierMaster.getId();

        if(!validateSupplierExists(supId)) {
            throw new IllegalArgumentException("Supplier " + supId + " does not exist.");
        }

        return entityManager.merge(supplierMaster);
    }

    @Transactional
    public void deleteSupplier(int id) {
        SupplierMaster supplier = entityManager.find(SupplierMaster.class, id);

        if (!validateSupplierExists(id)) {
            throw new IllegalArgumentException("Supplier with ID " + id + " does not exist.");
        }

        entityManager.remove(supplier);
    }

    public boolean validateSupplierExists(int id) {
        return jpaStreamer.stream(SupplierMaster.class).anyMatch(SupplierMaster$.id.equal(id));
    }

    public boolean validateSupplierDuplicate(String supCode) {
        return jpaStreamer.stream(SupplierMaster.class).anyMatch(SupplierMaster$.supplierCode.containsIgnoreCase(supCode));
    }
}
