package org.coresync.app.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "inv_cls", schema = "inventory_mgt", catalog = "coresync")
public class InventoryClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "inv_cls_id")
    private int invClsId;
    @Basic
    @Column(name = "inv_cd_id")
    private int invCdId;
    @Basic
    @Column(name = "inv_cls_desc")
    private String invClsDesc;
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

    public int getInvClsId() {
        return invClsId;
    }

    public void setInvClsId(int invClsId) {
        this.invClsId = invClsId;
    }

    public int getInvCdId() {
        return invCdId;
    }

    public void setInvCdId(int invCdId) {
        this.invCdId = invCdId;
    }

    public String getInvClsDesc() {
        return invClsDesc;
    }

    public void setInvClsDesc(String invClsDesc) {
        this.invClsDesc = invClsDesc;
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

        InventoryClass that = (InventoryClass) o;

        if (invClsId != that.invClsId) return false;
        if (invCdId != that.invCdId) return false;
        if (invClsDesc != null ? !invClsDesc.equals(that.invClsDesc) : that.invClsDesc != null) return false;
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
        int result = invClsId;
        result = 31 * result + invCdId;
        result = 31 * result + (invClsDesc != null ? invClsDesc.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
