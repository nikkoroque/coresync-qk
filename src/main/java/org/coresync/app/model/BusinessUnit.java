package org.coresync.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bu_master", schema = "inventory_mgt", catalog = "coresync")
public class BusinessUnit {
    @Id
    @Column(name = "bu_id")
    private int buId;
    @Basic
    @Column(name = "bu_desc")
    private String buDesc;
    @Basic
    @Column(name = "bu_gln")
    private String buGln;
    @Basic
    @Column(name = "bu_add1")
    private String buAdd1;
    @Basic
    @Column(name = "bu_add2")
    private String buAdd2;
    @Basic
    @Column(name = "bu_city")
    private String buCity;
    @Basic
    @Column(name = "bu_state")
    private String buState;
    @Basic
    @Column(name = "bu_zip")
    private String buZip;
    @Basic
    @Column(name = "bu_country")
    private String buCountry;

    // No-argument constructor (required by JPA)
    public BusinessUnit() {
    }

    // Constructor with arguments for testing or convenience
    public BusinessUnit(Integer buId, String buDesc, String buGln, String buCity, String buState, String buZip, String buCountry) {
        this.buId = buId;
        this.buDesc = buDesc;
        this.buGln = buGln;
        this.buCity = buCity;
        this.buState = buState;
        this.buZip = buZip;
        this.buCountry = buCountry;
    }

    public BusinessUnit(Integer buId, String buDesc, String buGln, String buAdd1, String buAdd2, String buCity, String buState, String buZip, String buCountry) {
        this.buId = buId;
        this.buDesc = buDesc;
        this.buGln = buGln;
        this.buAdd1 = buAdd1;
        this.buAdd2 = buAdd2;
        this.buCity = buCity;
        this.buState = buState;
        this.buZip = buZip;
        this.buCountry = buCountry;
    }

    public int getBuId() {
        return buId;
    }

    public void setBuId(int buId) {
        this.buId = buId;
    }

    public String getBuDesc() {
        return buDesc;
    }

    public void setBuDesc(String buDesc) {
        this.buDesc = buDesc;
    }

    public String getBuGln() {
        return buGln;
    }

    public void setBuGln(String buGln) {
        this.buGln = buGln;
    }

    public String getBuAdd1() {
        return buAdd1;
    }

    public void setBuAdd1(String buAdd1) {
        this.buAdd1 = buAdd1;
    }

    public String getBuAdd2() {
        return buAdd2;
    }

    public void setBuAdd2(String buAdd2) {
        this.buAdd2 = buAdd2;
    }

    public String getBuCity() {
        return buCity;
    }

    public void setBuCity(String buCity) {
        this.buCity = buCity;
    }

    public String getBuState() {
        return buState;
    }

    public void setBuState(String buState) {
        this.buState = buState;
    }

    public String getBuZip() {
        return buZip;
    }

    public void setBuZip(String buZip) {
        this.buZip = buZip;
    }

    public String getBuCountry() {
        return buCountry;
    }

    public void setBuCountry(String buCountry) {
        this.buCountry = buCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessUnit that = (BusinessUnit) o;

        if (buId != that.buId) return false;
        if (buDesc != null ? !buDesc.equals(that.buDesc) : that.buDesc != null) return false;
        if (buGln != null ? !buGln.equals(that.buGln) : that.buGln != null) return false;
        if (buAdd1 != null ? !buAdd1.equals(that.buAdd1) : that.buAdd1 != null) return false;
        if (buAdd2 != null ? !buAdd2.equals(that.buAdd2) : that.buAdd2 != null) return false;
        if (buCity != null ? !buCity.equals(that.buCity) : that.buCity != null) return false;
        if (buState != null ? !buState.equals(that.buState) : that.buState != null) return false;
        if (buZip != null ? !buZip.equals(that.buZip) : that.buZip != null) return false;
        if (buCountry != null ? !buCountry.equals(that.buCountry) : that.buCountry != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = buId;
        result = 31 * result + (buDesc != null ? buDesc.hashCode() : 0);
        result = 31 * result + (buGln != null ? buGln.hashCode() : 0);
        result = 31 * result + (buAdd1 != null ? buAdd1.hashCode() : 0);
        result = 31 * result + (buAdd2 != null ? buAdd2.hashCode() : 0);
        result = 31 * result + (buCity != null ? buCity.hashCode() : 0);
        result = 31 * result + (buState != null ? buState.hashCode() : 0);
        result = 31 * result + (buZip != null ? buZip.hashCode() : 0);
        result = 31 * result + (buCountry != null ? buCountry.hashCode() : 0);
        return result;
    }
}
