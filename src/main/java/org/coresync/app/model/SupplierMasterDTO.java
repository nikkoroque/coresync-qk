package org.coresync.app.model;

public class SupplierMasterDTO {
    private int id;
    private String supplierCode;
    private String supplierName;

    public SupplierMasterDTO(int id, String supplierCode, String supplierName) {
        this.id = id;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
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
}
