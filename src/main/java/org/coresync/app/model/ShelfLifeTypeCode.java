package org.coresync.app.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "shelf_life_typ_cd", schema = "inventory_mgt", catalog = "coresync")
public class ShelfLifeTypeCode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shelf_life_typ_cd")
    private String shelfLifeTypCd;
    @Basic
    @Column(name = "shelf_life_typ_cd_desc")
    private String shelfLifeTypCdDesc;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Basic
    @Column(name = "created_by_user")
    private String createdByUser;
    @Basic
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;
    @Basic
    @Column(name = "last_updated_by_user")
    private String lastUpdatedByUser;

    public String getShelfLifeTypCd() {
        return shelfLifeTypCd;
    }

    public void setShelfLifeTypCd(String shelfLifeTypCd) {
        this.shelfLifeTypCd = shelfLifeTypCd;
    }

    public String getShelfLifeTypCdDesc() {
        return shelfLifeTypCdDesc;
    }

    public void setShelfLifeTypCdDesc(String shelfLifeTypCdDesc) {
        this.shelfLifeTypCdDesc = shelfLifeTypCdDesc;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
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

        ShelfLifeTypeCode that = (ShelfLifeTypeCode) o;

        if (shelfLifeTypCd != null ? !shelfLifeTypCd.equals(that.shelfLifeTypCd) : that.shelfLifeTypCd != null)
            return false;
        if (shelfLifeTypCdDesc != null ? !shelfLifeTypCdDesc.equals(that.shelfLifeTypCdDesc) : that.shelfLifeTypCdDesc != null)
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
        int result = shelfLifeTypCd != null ? shelfLifeTypCd.hashCode() : 0;
        result = 31 * result + (shelfLifeTypCdDesc != null ? shelfLifeTypCdDesc.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
