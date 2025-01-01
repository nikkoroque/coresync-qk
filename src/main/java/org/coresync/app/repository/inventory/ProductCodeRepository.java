package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.ProductCode;
import org.coresync.app.model.ProductCode$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<ProductCode> getProductCodes() {
        return jpaStreamer.stream(ProductCode.class).collect(Collectors.toList());
    }

    public Optional<ProductCode> getProductCodeDetail(int id) {
        return jpaStreamer.stream(ProductCode.class).filter(ProductCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public ProductCode createProductCode(ProductCode productCode) {
        if (productCode == null) {
            throw new IllegalArgumentException("Product code cannot be null");
        }

        entityManager.persist(productCode);
        return productCode;
    }

    public boolean validateProductCodeExists(int id) {
        return jpaStreamer.stream(ProductCode.class).anyMatch(ProductCode$.id.equal(id));
    }

    public boolean validateProductCodeDuplicate(String productCd) {
        return jpaStreamer.stream(ProductCode.class).anyMatch(ProductCode$.description.equal(productCd));
    }

    @Transactional
    public ProductCode updateProductCode(ProductCode productCode) {
        int productCdId = productCode.getId();

        if (!validateProductCodeExists(productCdId)) {
            throw new IllegalArgumentException("Product code ID " + productCdId + " does not exist");
        }

        return entityManager.merge(productCode);
    }

    @Transactional
    public void deleteProductCode(int id) {
        ProductCode productCode = entityManager.find(ProductCode.class, id);

        if (!validateProductCodeExists(id)) {
        throw new IllegalArgumentException("Product code ID " + id + " does not exist");
        }
        entityManager.remove(productCode);
    }

}
