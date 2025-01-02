package org.coresync.app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "jurisdiction_master", schema = "inventory_mgt", catalog = "coresync")
public class JurisdictionMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('inventory_mgt.jurisdiction_master_id_seq'")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "jurisdiction_code", nullable = false, length = 10)
    private String jurisdictionCode;

    @Column(name = "jurisdiction_name", nullable = false, length = 100)
    private String jurisdictionName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "jurisdiction_tax_type_code", nullable = false, referencedColumnName = "jurisdiction_type_code")
    private TaxJurisdictionTypeCode jurisdictionTaxTypeCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "jurisdiction_class_type_code", nullable = false, referencedColumnName = "class_type_code")
    private JurisdictionClassTypeCode jurisdictionClassTypeCode;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @ColumnDefault("SYSTEM")
    @Column(name = "created_by_user", nullable = false, length = 10)
    private String createdByUser;

    @Column(name = "last_update_date", nullable = false)
    private Instant lastUpdateDate;

    @ColumnDefault("SYSTEM")
    @Column(name = "last_updated_by_user", nullable = false, length = 10)
    private String lastUpdatedByUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJurisdictionCode() {
        return jurisdictionCode;
    }

    public void setJurisdictionCode(String jurisdictionCode) {
        this.jurisdictionCode = jurisdictionCode;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
    }

    public TaxJurisdictionTypeCode getJurisdictionTaxTypeCode() {
        return jurisdictionTaxTypeCode;
    }

    public void setJurisdictionTaxTypeCode(TaxJurisdictionTypeCode jurisdictionTaxTypeCode) {
        this.jurisdictionTaxTypeCode = jurisdictionTaxTypeCode;
    }

    public JurisdictionClassTypeCode getJurisdictionClassTypeCode() {
        return jurisdictionClassTypeCode;
    }

    public void setJurisdictionClassTypeCode(JurisdictionClassTypeCode jurisdictionClassTypeCode) {
        this.jurisdictionClassTypeCode = jurisdictionClassTypeCode;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdatedByUser() {
        return lastUpdatedByUser;
    }

    public void setLastUpdatedByUser(String lastUpdatedByUser) {
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

}