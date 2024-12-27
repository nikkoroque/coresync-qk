package org.coresync.app.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "supplier_master", schema = "inventory_mgt", catalog = "coresync")
public class SupplierMaster {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "supplier_code")
    private String supplierCode;
    @Basic
    @Column(name = "supplier_name")
    private String supplierName;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "state_cd")
    private String stateCd;
    @Basic
    @Column(name = "zip_cd")
    private String zipCd;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "int_sw")
    private String intSw;
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
    @Column(name = "status_cd")
    private String statusCd;
    @Basic
    @Column(name = "net_days_to_pay")
    private Integer netDaysToPay;
    @Basic
    @Column(name = "discount_percent")
    private Integer discountPercent;
    @Basic
    @Column(name = "discount_days")
    private Integer discountDays;
    @Basic
    @Column(name = "bank_account_details")
    private String bankAccountDetails;
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

    public SupplierMaster() {}
    public SupplierMaster(int id, String supplierCode, String supplierName, String address, String city, String stateCd, String zipCd, String country, String intSw, String contactNumber, String email, String taxIdentificationNumber, String statusCd, int netDaysToPay, int discountPercent, int discountDays,String bankAccountDetails, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdateDate, String lastUpdatedByUser) {
        this.id = id;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.address = address;
        this.city = city;
        this.stateCd = stateCd;
        this.zipCd = zipCd;
        this.country = country;
        this.intSw = intSw;
        this.contactNumber = contactNumber;
        this.email = email;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.statusCd = statusCd;
        this.netDaysToPay = netDaysToPay;
        this.discountPercent = discountPercent;
        this.discountDays = discountDays;
        this.bankAccountDetails =bankAccountDetails;
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

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getZipCd() {
        return zipCd;
    }

    public void setZipCd(String zipCd) {
        this.zipCd = zipCd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIntSw() {
        return intSw;
    }

    public void setIntSw(String intSw) {
        this.intSw = intSw;
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

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Integer getNetDaysToPay() {
        return netDaysToPay;
    }

    public void setNetDaysToPay(Integer netDaysToPay) {
        this.netDaysToPay = netDaysToPay;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getDiscountDays() {
        return discountDays;
    }

    public void setDiscountDays(Integer discountDays) {
        this.discountDays = discountDays;
    }

    public String getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(String bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
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

        SupplierMaster that = (SupplierMaster) o;

        if (id != that.id) return false;
        if (supplierCode != null ? !supplierCode.equals(that.supplierCode) : that.supplierCode != null) return false;
        if (supplierName != null ? !supplierName.equals(that.supplierName) : that.supplierName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (stateCd != null ? !stateCd.equals(that.stateCd) : that.stateCd != null) return false;
        if (zipCd != null ? !zipCd.equals(that.zipCd) : that.zipCd != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (intSw != null ? !intSw.equals(that.intSw) : that.intSw != null) return false;
        if (contactNumber != null ? !contactNumber.equals(that.contactNumber) : that.contactNumber != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (taxIdentificationNumber != null ? !taxIdentificationNumber.equals(that.taxIdentificationNumber) : that.taxIdentificationNumber != null)
            return false;
        if (statusCd != null ? !statusCd.equals(that.statusCd) : that.statusCd != null) return false;
        if (netDaysToPay != null ? !netDaysToPay.equals(that.netDaysToPay) : that.netDaysToPay != null) return false;
        if (discountPercent != null ? !discountPercent.equals(that.discountPercent) : that.discountPercent != null)
            return false;
        if (discountDays != null ? !discountDays.equals(that.discountDays) : that.discountDays != null) return false;
        if (bankAccountDetails != null ? !bankAccountDetails.equals(that.bankAccountDetails) : that.bankAccountDetails != null)
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
        result = 31 * result + (supplierCode != null ? supplierCode.hashCode() : 0);
        result = 31 * result + (supplierName != null ? supplierName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateCd != null ? stateCd.hashCode() : 0);
        result = 31 * result + (zipCd != null ? zipCd.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (intSw != null ? intSw.hashCode() : 0);
        result = 31 * result + (contactNumber != null ? contactNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (taxIdentificationNumber != null ? taxIdentificationNumber.hashCode() : 0);
        result = 31 * result + (statusCd != null ? statusCd.hashCode() : 0);
        result = 31 * result + (netDaysToPay != null ? netDaysToPay.hashCode() : 0);
        result = 31 * result + (discountPercent != null ? discountPercent.hashCode() : 0);
        result = 31 * result + (discountDays != null ? discountDays.hashCode() : 0);
        result = 31 * result + (bankAccountDetails != null ? bankAccountDetails.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
