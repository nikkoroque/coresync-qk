package org.coresync.app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Table(name = "jurisdiction_master", schema = "inventory_mgt", catalog = "coresync")
public class JurisdictionMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "jurisdiction_code", nullable = false, length = 10)
    private String jurisdictionCode;

    @Column(name = "jurisdiction_name", nullable = false, length = 100)
    private String jurisdictionName;

    @Column(name = "jurisdiction_tax_type_code", nullable = false)
    private String jurisdictionTaxTypeCode;

    @Column(name = "jurisdiction_class_type_code", nullable = false)
    private String jurisdictionClassTypeCode;

    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime creationDate;

    @ColumnDefault("SYSTEM")
    @Column(name = "created_by_user", nullable = false, length = 10)
    private String createdByUser;

    @Column(name = "last_update_date", nullable = false)
    private OffsetDateTime lastUpdateDate;

    @ColumnDefault("SYSTEM")
    @Column(name = "last_updated_by_user", nullable = false, length = 10)
    private String lastUpdatedByUser;

    public JurisdictionMaster(Integer id, String jurisdictionCode, String jurisdictionName, String jurisdictionTaxTypeCode, String jurisdictionClassTypeCode, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdateDate, String lastUpdatedByUser) {
        this.id = id;
        this.jurisdictionCode = jurisdictionCode;
        this.jurisdictionName = jurisdictionName;
        this.jurisdictionTaxTypeCode = jurisdictionTaxTypeCode;
        this.jurisdictionClassTypeCode = jurisdictionClassTypeCode;
        this.creationDate = creationDate;
        this.createdByUser = createdByUser;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

    public JurisdictionMaster() {

    }

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

    public String getJurisdictionTaxTypeCode() {
        return jurisdictionTaxTypeCode;
    }

    public void setJurisdictionTaxTypeCode(String jurisdictionTaxTypeCode) {
        this.jurisdictionTaxTypeCode = jurisdictionTaxTypeCode;
    }

    public String getJurisdictionClassTypeCode() {
        return jurisdictionClassTypeCode;
    }

    public void setJurisdictionClassTypeCode(String jurisdictionClassTypeCode) {
        this.jurisdictionClassTypeCode = jurisdictionClassTypeCode;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public OffsetDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(OffsetDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdatedByUser() {
        return lastUpdatedByUser;
    }

    public void setLastUpdatedByUser(String lastUpdatedByUser) {
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

}