package org.coresync.app.repository.inventory;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.coresync.app.model.TaxJurisdictionTypeCode;
import org.coresync.app.model.TaxJurisdictionTypeCode$;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaxJurisdictionTypeCodeRepository {
    @Inject
    JPAStreamer jpaStreamer;
    @Inject
    EntityManager entityManager;

    public List<TaxJurisdictionTypeCode> getAllTaxJurisdictionTypeCodes() {
        return jpaStreamer.stream(TaxJurisdictionTypeCode.class).collect(Collectors.toList());
    }

    public Optional<TaxJurisdictionTypeCode> getTaxJurisdictionTypeCodeDetail(int id) {
        return jpaStreamer.stream(TaxJurisdictionTypeCode.class).filter(TaxJurisdictionTypeCode$.id.equal(id)).findFirst();
    }

    @Transactional
    public TaxJurisdictionTypeCode addTaxJurisdictionTypeCode(TaxJurisdictionTypeCode taxJurisdictionTypeCode) {
        if (taxJurisdictionTypeCode == null) {
            throw new IllegalArgumentException("Tax Jurisdiction Type Code cannot be null");
        }

        entityManager.persist(taxJurisdictionTypeCode);
        return taxJurisdictionTypeCode;
    }

    @Transactional
    public TaxJurisdictionTypeCode updateTaxJurisdictionTypeCode(TaxJurisdictionTypeCode taxJurisdictionTypeCode) {
        int taxJrsTypCdId = taxJurisdictionTypeCode.getId();

        if (!validateTaxJurisdictionTypeCodeExists(taxJrsTypCdId)) {
            throw new IllegalArgumentException("Tax Jurisdiction Type Code " + taxJrsTypCdId + " does not exist.");
        }
        return entityManager.merge(taxJurisdictionTypeCode);
    }

    @Transactional
    public void deleteTaxJurisdictionTypeCode(int id) {
        TaxJurisdictionTypeCode taxJrsTypCd = entityManager.find(TaxJurisdictionTypeCode.class, id);

        if (!validateTaxJurisdictionTypeCodeExists(id)) {
            throw new IllegalArgumentException("Tax Jurisdiction Type Code with ID" + id + " does not exist.");
        }
        entityManager.remove(taxJrsTypCd);
    }

    public boolean validateTaxJurisdictionTypeCodeExists(int id) {
        return jpaStreamer.stream(TaxJurisdictionTypeCode.class).anyMatch(TaxJurisdictionTypeCode$.id.equal(id));
    }

    public boolean validateTaxJurisdictionTypeCodeDuplicate(String typeCode) {
        return jpaStreamer.stream(TaxJurisdictionTypeCode.class).anyMatch(TaxJurisdictionTypeCode$.jurisdictionTypeCode.equalIgnoreCase(typeCode));
    }

}
