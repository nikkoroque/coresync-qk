package org.coresync.app.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "prod_cd", schema = "inventory_mgt", catalog = "coresync")
public class ProductCode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "prod_cd")
    private String prodCd;
    @Basic
    @Column(name = "section_cd")
    private String sectionCd;
    @Basic
    @Column(name = "prod_cd_desc")
    private String prodCdDesc;
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

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getSectionCd() {
        return sectionCd;
    }

    public void setSectionCd(String sectionCd) {
        this.sectionCd = sectionCd;
    }

    public String getProdCdDesc() {
        return prodCdDesc;
    }

    public void setProdCdDesc(String prodCdDesc) {
        this.prodCdDesc = prodCdDesc;
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

        ProductCode that = (ProductCode) o;

        if (prodCd != null ? !prodCd.equals(that.prodCd) : that.prodCd != null) return false;
        if (sectionCd != null ? !sectionCd.equals(that.sectionCd) : that.sectionCd != null) return false;
        if (prodCdDesc != null ? !prodCdDesc.equals(that.prodCdDesc) : that.prodCdDesc != null) return false;
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
        int result = prodCd != null ? prodCd.hashCode() : 0;
        result = 31 * result + (sectionCd != null ? sectionCd.hashCode() : 0);
        result = 31 * result + (prodCdDesc != null ? prodCdDesc.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
