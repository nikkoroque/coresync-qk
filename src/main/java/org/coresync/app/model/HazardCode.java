package org.coresync.app.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "hazard_code", schema = "inventory_mgt", catalog = "coresync")
public class HazardCode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "hazard_class_id")
    private int hazardClassId;
    @Basic
    @Column(name = "description_1")
    private String description1;
    @Basic
    @Column(name = "description_2")
    private String description2;
    @Basic
    @Column(name = "material_package_code")
    private String materialPackageCode;
    @Basic
    @Column(name = "govt_material_code")
    private String govtMaterialCode;
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

    public HazardCode() {}

    public HazardCode(int id, int hazardClassId, String description1, String description2, String materialPackageCode, String govtMaterialCode, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdateDate, String lastUpdatedByUser) {
        this.id = id;
        this.hazardClassId = hazardClassId;
        this.description1 = description1;
        this.description2 = description2;
        this.materialPackageCode = materialPackageCode;
        this.govtMaterialCode = govtMaterialCode;
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

    public int getHazardClassId() {
        return hazardClassId;
    }

    public void setHazardClassId(int hazardClassId) {
        this.hazardClassId = hazardClassId;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getMaterialPackageCode() {
        return materialPackageCode;
    }

    public void setMaterialPackageCode(String materialPackageCode) {
        this.materialPackageCode = materialPackageCode;
    }

    public String getGovtMaterialCode() {
        return govtMaterialCode;
    }

    public void setGovtMaterialCode(String govtMaterialCode) {
        this.govtMaterialCode = govtMaterialCode;
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

        HazardCode that = (HazardCode) o;

        if (id != that.id) return false;
        if (hazardClassId != that.hazardClassId) return false;
        if (description1 != null ? !description1.equals(that.description1) : that.description1 != null) return false;
        if (description2 != null ? !description2.equals(that.description2) : that.description2 != null) return false;
        if (materialPackageCode != null ? !materialPackageCode.equals(that.materialPackageCode) : that.materialPackageCode != null)
            return false;
        if (govtMaterialCode != null ? !govtMaterialCode.equals(that.govtMaterialCode) : that.govtMaterialCode != null)
            return false;
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
        result = 31 * result + hazardClassId;
        result = 31 * result + (description1 != null ? description1.hashCode() : 0);
        result = 31 * result + (description2 != null ? description2.hashCode() : 0);
        result = 31 * result + (materialPackageCode != null ? materialPackageCode.hashCode() : 0);
        result = 31 * result + (govtMaterialCode != null ? govtMaterialCode.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
