package org.coresync.app.model;

public class BusinessUnitMasterDTO {
    private int id;
    private String businessUnitCode;
    private String businessUnitName;

    public BusinessUnitMasterDTO(int id, String businessUnitCode, String businessUnitName) {
        this.id = id;
        this.businessUnitCode = businessUnitCode;
        this.businessUnitName = businessUnitName;
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
}
