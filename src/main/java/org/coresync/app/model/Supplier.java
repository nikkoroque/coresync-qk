package org.coresync.app.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "splr_master", schema = "inventory_mgt", catalog = "coresync")
public class Supplier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "splr_id")
    private int splrId;
    @Basic
    @Column(name = "splr_name")
    private String splrName;
    @Basic
    @Column(name = "splr_pmry_phone")
    private String splrPmryPhone;
    @Basic
    @Column(name = "splr_email")
    private String splrEmail;
    @Basic
    @Column(name = "splr_add1")
    private String splrAdd1;
    @Basic
    @Column(name = "splr_add2")
    private String splrAdd2;
    @Basic
    @Column(name = "splr_city")
    private String splrCity;
    @Basic
    @Column(name = "splr_state")
    private String splrState;
    @Basic
    @Column(name = "splr_zip")
    private String splrZip;
    @Basic
    @Column(name = "splr_country")
    private String splrCountry;
    @Basic
    @Column(name = "splr_int_sw")
    private String splrIntSw;
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

    public int getSplrId() {
        return splrId;
    }

    public void setSplrId(int splrId) {
        this.splrId = splrId;
    }

    public String getSplrName() {
        return splrName;
    }

    public void setSplrName(String splrName) {
        this.splrName = splrName;
    }

    public String getSplrPmryPhone() {
        return splrPmryPhone;
    }

    public void setSplrPmryPhone(String splrPmryPhone) {
        this.splrPmryPhone = splrPmryPhone;
    }

    public String getSplrEmail() {
        return splrEmail;
    }

    public void setSplrEmail(String splrEmail) {
        this.splrEmail = splrEmail;
    }

    public String getSplrAdd1() {
        return splrAdd1;
    }

    public void setSplrAdd1(String splrAdd1) {
        this.splrAdd1 = splrAdd1;
    }

    public String getSplrAdd2() {
        return splrAdd2;
    }

    public void setSplrAdd2(String splrAdd2) {
        this.splrAdd2 = splrAdd2;
    }

    public String getSplrCity() {
        return splrCity;
    }

    public void setSplrCity(String splrCity) {
        this.splrCity = splrCity;
    }

    public String getSplrState() {
        return splrState;
    }

    public void setSplrState(String splrState) {
        this.splrState = splrState;
    }

    public String getSplrZip() {
        return splrZip;
    }

    public void setSplrZip(String splrZip) {
        this.splrZip = splrZip;
    }

    public String getSplrCountry() {
        return splrCountry;
    }

    public void setSplrCountry(String splrCountry) {
        this.splrCountry = splrCountry;
    }

    public String getSplrIntSw() {
        return splrIntSw;
    }

    public void setSplrIntSw(String splrIntSw) {
        this.splrIntSw = splrIntSw;
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

        Supplier supplier = (Supplier) o;

        if (splrId != supplier.splrId) return false;
        if (splrName != null ? !splrName.equals(supplier.splrName) : supplier.splrName != null) return false;
        if (splrPmryPhone != null ? !splrPmryPhone.equals(supplier.splrPmryPhone) : supplier.splrPmryPhone != null)
            return false;
        if (splrEmail != null ? !splrEmail.equals(supplier.splrEmail) : supplier.splrEmail != null) return false;
        if (splrAdd1 != null ? !splrAdd1.equals(supplier.splrAdd1) : supplier.splrAdd1 != null) return false;
        if (splrAdd2 != null ? !splrAdd2.equals(supplier.splrAdd2) : supplier.splrAdd2 != null) return false;
        if (splrCity != null ? !splrCity.equals(supplier.splrCity) : supplier.splrCity != null) return false;
        if (splrState != null ? !splrState.equals(supplier.splrState) : supplier.splrState != null) return false;
        if (splrZip != null ? !splrZip.equals(supplier.splrZip) : supplier.splrZip != null) return false;
        if (splrCountry != null ? !splrCountry.equals(supplier.splrCountry) : supplier.splrCountry != null)
            return false;
        if (splrIntSw != null ? !splrIntSw.equals(supplier.splrIntSw) : supplier.splrIntSw != null) return false;
        if (creationDate != null ? !creationDate.equals(supplier.creationDate) : supplier.creationDate != null)
            return false;
        if (createdByUser != null ? !createdByUser.equals(supplier.createdByUser) : supplier.createdByUser != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(supplier.lastUpdateDate) : supplier.lastUpdateDate != null)
            return false;
        if (lastUpdatedByUser != null ? !lastUpdatedByUser.equals(supplier.lastUpdatedByUser) : supplier.lastUpdatedByUser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = splrId;
        result = 31 * result + (splrName != null ? splrName.hashCode() : 0);
        result = 31 * result + (splrPmryPhone != null ? splrPmryPhone.hashCode() : 0);
        result = 31 * result + (splrEmail != null ? splrEmail.hashCode() : 0);
        result = 31 * result + (splrAdd1 != null ? splrAdd1.hashCode() : 0);
        result = 31 * result + (splrAdd2 != null ? splrAdd2.hashCode() : 0);
        result = 31 * result + (splrCity != null ? splrCity.hashCode() : 0);
        result = 31 * result + (splrState != null ? splrState.hashCode() : 0);
        result = 31 * result + (splrZip != null ? splrZip.hashCode() : 0);
        result = 31 * result + (splrCountry != null ? splrCountry.hashCode() : 0);
        result = 31 * result + (splrIntSw != null ? splrIntSw.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
