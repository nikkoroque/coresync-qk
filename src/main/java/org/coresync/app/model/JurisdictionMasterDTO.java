package org.coresync.app.model;

public class JurisdictionMasterDTO {
    private int id;
    private String jurisdictionCode;
    private String jurisdictionName;
    private String jurisdictionTaxTypeCode;
    private String jurisdictionClassTypeCode;

    public JurisdictionMasterDTO(int id, String jurisdictionCode, String jurisdictionName, String jurisdictionTaxTypeCode, String jurisdictionClassTypeCode) {
        this.id = id;
        this.jurisdictionCode = jurisdictionCode;
        this.jurisdictionName = jurisdictionName;
        this.jurisdictionTaxTypeCode = jurisdictionTaxTypeCode;
        this.jurisdictionClassTypeCode = jurisdictionClassTypeCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJurisdictionCode() {
        return jurisdictionCode;
    }

    public void setJurisdictionCode(String jurisdictionCode) {
        this.jurisdictionCode = jurisdictionCode;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
    }

    public String getJurisdictionTaxTypeCode() {
        return jurisdictionTaxTypeCode;
    }

    public void setJurisdictionTaxTypeCode(String jurisdictionTaxTypeCode) {
        this.jurisdictionTaxTypeCode = jurisdictionTaxTypeCode;
    }

    public String getJurisdictionClassTypeCode() {
        return jurisdictionClassTypeCode;
    }

    public void setJurisdictionClassTypeCode(String jurisdictionClassTypeCode) {
        this.jurisdictionClassTypeCode = jurisdictionClassTypeCode;
    }
}
