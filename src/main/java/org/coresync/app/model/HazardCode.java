package org.coresync.app.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "hzrd_cd", schema = "inventory_mgt", catalog = "coresync")
public class HazardCode {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hzrd_cd")
    private int hzrdCd;
    @Basic
    @Column(name = "hzrd_cls_cd")
    private String hzrdClsCd;
    @Basic
    @Column(name = "hzrd_desc1")
    private String hzrdDesc1;
    @Basic
    @Column(name = "hzrd_desc2")
    private String hzrdDesc2;
    @Basic
    @Column(name = "hzrd_mtrl_pkg_cd")
    private String hzrdMtrlPkgCd;
    @Basic
    @Column(name = "hzrd_govt_mtrl_cd")
    private String hzrdGovtMtrlCd;
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

    public int getHzrdCd() {
        return hzrdCd;
    }

    public void setHzrdCd(int hzrdCd) {
        this.hzrdCd = hzrdCd;
    }

    public String getHzrdClsCd() {
        return hzrdClsCd;
    }

    public void setHzrdClsCd(String hzrdClsCd) {
        this.hzrdClsCd = hzrdClsCd;
    }

    public String getHzrdDesc1() {
        return hzrdDesc1;
    }

    public void setHzrdDesc1(String hzrdDesc1) {
        this.hzrdDesc1 = hzrdDesc1;
    }

    public String getHzrdDesc2() {
        return hzrdDesc2;
    }

    public void setHzrdDesc2(String hzrdDesc2) {
        this.hzrdDesc2 = hzrdDesc2;
    }

    public String getHzrdMtrlPkgCd() {
        return hzrdMtrlPkgCd;
    }

    public void setHzrdMtrlPkgCd(String hzrdMtrlPkgCd) {
        this.hzrdMtrlPkgCd = hzrdMtrlPkgCd;
    }

    public String getHzrdGovtMtrlCd() {
        return hzrdGovtMtrlCd;
    }

    public void setHzrdGovtMtrlCd(String hzrdGovtMtrlCd) {
        this.hzrdGovtMtrlCd = hzrdGovtMtrlCd;
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

        HazardCode that = (HazardCode) o;

        if (hzrdCd != that.hzrdCd) return false;
        if (hzrdClsCd != null ? !hzrdClsCd.equals(that.hzrdClsCd) : that.hzrdClsCd != null) return false;
        if (hzrdDesc1 != null ? !hzrdDesc1.equals(that.hzrdDesc1) : that.hzrdDesc1 != null) return false;
        if (hzrdDesc2 != null ? !hzrdDesc2.equals(that.hzrdDesc2) : that.hzrdDesc2 != null) return false;
        if (hzrdMtrlPkgCd != null ? !hzrdMtrlPkgCd.equals(that.hzrdMtrlPkgCd) : that.hzrdMtrlPkgCd != null)
            return false;
        if (hzrdGovtMtrlCd != null ? !hzrdGovtMtrlCd.equals(that.hzrdGovtMtrlCd) : that.hzrdGovtMtrlCd != null)
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
        int result = hzrdCd;
        result = 31 * result + (hzrdClsCd != null ? hzrdClsCd.hashCode() : 0);
        result = 31 * result + (hzrdDesc1 != null ? hzrdDesc1.hashCode() : 0);
        result = 31 * result + (hzrdDesc2 != null ? hzrdDesc2.hashCode() : 0);
        result = 31 * result + (hzrdMtrlPkgCd != null ? hzrdMtrlPkgCd.hashCode() : 0);
        result = 31 * result + (hzrdGovtMtrlCd != null ? hzrdGovtMtrlCd.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
