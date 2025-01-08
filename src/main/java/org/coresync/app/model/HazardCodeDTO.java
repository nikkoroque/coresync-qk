package org.coresync.app.model;

public class HazardCodeDTO {
    private int id;
    private String description1;

    public HazardCodeDTO(int id, String description1) {
        this.id = id;
        this.description1 = description1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }
}
