package org.coresync.app.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tax_jurisdiction_type_code", schema = "inventory_mgt", catalog = "coresync")
public class TaxJurisdictionTypeCode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "jurisdiction_type_code")
    private String jurisdictionTypeCode;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "creation_date")
    private OffsetDateTime creationDate;
    @Basic
    @Column(name = "created_by_user")
    private String createdByUser;
    @Basic
    @Column(name = "last_update_date")
    private OffsetDateTime lastUpdateDate;
    @Basic
    @Column(name = "last_updated_by_user")
    private String lastUpdatedByUser;

    public TaxJurisdictionTypeCode() {

    }

    public TaxJurisdictionTypeCode(int id, String jurisdictionTypeCode, String description, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdateDate, String lastUpdatedByUser) {
        this.id = id;
        this.jurisdictionTypeCode = jurisdictionTypeCode;
        this.description = description;
        this.creationDate = creationDate;
        this.createdByUser = createdByUser;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJurisdictionTypeCode() {
        return jurisdictionTypeCode;
    }

    public void setJurisdictionTypeCode(String jurisdictionTypeCode) {
        this.jurisdictionTypeCode = jurisdictionTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxJurisdictionTypeCode that = (TaxJurisdictionTypeCode) o;

        if (id != that.id) return false;
        if (jurisdictionTypeCode != null ? !jurisdictionTypeCode.equals(that.jurisdictionTypeCode) : that.jurisdictionTypeCode != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (createdByUser != null ? !createdByUser.equals(that.createdByUser) : that.createdByUser != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(that.lastUpdateDate) : that.lastUpdateDate != null)
            return false;
        if (lastUpdatedByUser != null ? !lastUpdatedByUser.equals(that.lastUpdatedByUser) : that.lastUpdatedByUser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (jurisdictionTypeCode != null ? jurisdictionTypeCode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
