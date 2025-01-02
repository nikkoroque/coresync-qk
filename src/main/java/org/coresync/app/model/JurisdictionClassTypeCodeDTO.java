package org.coresync.app.model;

public class JurisdictionClassTypeCodeDTO {
    private int id;
    private String classTypeCode;

    public JurisdictionClassTypeCodeDTO(int id, String classTypeCode) {
        this.id = id;
        this.classTypeCode = classTypeCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassTypeCode() {
        return classTypeCode;
    }

    public void setClassTypeCode(String classTypeCode) {
        this.classTypeCode = classTypeCode;
    }
}
