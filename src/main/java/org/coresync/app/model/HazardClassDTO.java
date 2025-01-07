package org.coresync.app.model;

public class HazardClassDTO {
    private int id;
    private String classCode;
    private String description;

    public HazardClassDTO(int id, String classCode, String description) {
        this.id = id;
        this.classCode = classCode;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
