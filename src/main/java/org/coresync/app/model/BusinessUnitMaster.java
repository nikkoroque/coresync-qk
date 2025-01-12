package org.coresync.app.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "business_unit_master", schema = "inventory_mgt", catalog = "coresync")
public class BusinessUnitMaster {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "business_unit_code")
    private String businessUnitCode;
    @Basic
    @Column(name = "business_unit_name")
    private String businessUnitName;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "contact_number")
    private String contactNumber;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "tax_identification_number")
    private String taxIdentificationNumber;
    @Basic
    @Column(name = "jurisdiction_id")
    private int jurisdictionId;
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
    @Basic
    @Column(name = "city", length = 100)
    private String city;
    @Basic
    @Column(name = "state_cd", length = 100)
    private String stateCd;
    @Basic
    @Column(name = "zip_cd", length = 20)
    private String zipCd;
    @Basic
    @Column(name = "country", length = 20)
    private String country;
    @Basic
    @Column(name = "int_sw", length = 1)
    private String intSw;
    @Basic
    @Column(name = "status_cd", length = 20)
    private String statusCd;

    public BusinessUnitMaster() {};

    public BusinessUnitMaster(int id, String businessUnitCode, String businessUnitName, String address, String contactNumber, String email, String taxIdentificationNumber, int jurisdictionId, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdateDate, String lastUpdatedByUser, String city, String stateCd, String zipCd, String country, String intSw, String statusCd) {
        this.id = id;
        this.businessUnitCode = businessUnitCode;
        this.businessUnitName = businessUnitName;
        this.address = address;
        this.city = city;
        this.stateCd = stateCd;
        this.zipCd = zipCd;
        this.country = country;
        this.intSw = intSw;
        this.statusCd = statusCd;
        this.contactNumber = contactNumber;
        this.email = email;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.jurisdictionId = jurisdictionId;
        this.creationDate = creationDate;
        this.createdByUser = createdByUser;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getIntSw() {
        return intSw;
    }

    public void setIntSw(String intSw) {
        this.intSw = intSw;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCd() {
        return zipCd;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusinessUnitCode() {
        return businessUnitCode;
    }

    public void setBusinessUnitCode(String businessUnitCode) {
        this.businessUnitCode = businessUnitCode;
    }

    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public int getJurisdictionId() {
        return jurisdictionId;
    }

    public void setJurisdictionId(int jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
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

        BusinessUnitMaster that = (BusinessUnitMaster) o;

        if (id != that.id) return false;
        if (jurisdictionId != that.jurisdictionId) return false;
        if (businessUnitCode != null ? !businessUnitCode.equals(that.businessUnitCode) : that.businessUnitCode != null)
            return false;
        if (businessUnitName != null ? !businessUnitName.equals(that.businessUnitName) : that.businessUnitName != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (contactNumber != null ? !contactNumber.equals(that.contactNumber) : that.contactNumber != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (taxIdentificationNumber != null ? !taxIdentificationNumber.equals(that.taxIdentificationNumber) : that.taxIdentificationNumber != null)
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
        result = 31 * result + (businessUnitCode != null ? businessUnitCode.hashCode() : 0);
        result = 31 * result + (businessUnitName != null ? businessUnitName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contactNumber != null ? contactNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (taxIdentificationNumber != null ? taxIdentificationNumber.hashCode() : 0);
        result = 31 * result + jurisdictionId;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
