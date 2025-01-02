package org.coresync.app.model;

public class TaxJurisdictionTypeCodeDTO {
    private int id;
    private String jurisdictionTypeCode;

    public TaxJurisdictionTypeCodeDTO(int id, String jurisdictionTypeCode) {
        this.id = id;
        this.jurisdictionTypeCode = jurisdictionTypeCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJurisdictionTypeCode() {
        return jurisdictionTypeCode;
    }

    public void setJurisdictionTypeCode(String jurisdictionTypeCode) {
        this.jurisdictionTypeCode = jurisdictionTypeCode;
    }
}
