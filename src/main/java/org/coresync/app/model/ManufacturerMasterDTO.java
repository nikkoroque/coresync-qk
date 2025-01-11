package org.coresync.app.model;

public class ManufacturerMasterDTO {
    private int id;
    private String manufacturerCode;
    private String manufacturerName;

    public ManufacturerMasterDTO(int id, String manufacturerCode, String manufacturerName) {
        this.id = id;
        this.manufacturerCode = manufacturerCode;
        this.manufacturerName = manufacturerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}
