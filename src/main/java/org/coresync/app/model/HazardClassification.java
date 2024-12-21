package org.coresync.app.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.coresync.app.util.TimestampDeserializer;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Entity
@Table(name = "hzrd_cls", schema = "inventory_mgt", catalog = "coresync")
public class HazardClassification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "hzrd_cls_cd")
    private String hzrdClsCd;
    @Basic
    @Column(name = "hzrd_cls_desc")
    private String hzrdClsDesc;
    @Basic
    @Column(name = "creation_date")
    @Schema(description = "Creation Date in 'yyyy-MM-dd HH:mm:ss' format", example = "2024-12-20 15:30:00")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp creationDate;
    @Basic
    @Column(name = "created_by_user")
    private String createdByUser;
    @Basic
    @Column(name = "last_update_date")
    @Schema(description = "Last Update Date in 'yyyy-MM-dd HH:mm:ss' format", example = "2024-12-20 15:30:00")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp lastUpdateDate;
    @Basic
    @Column(name = "last_updated_by_user")
    private String lastUpdatedByUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHzrdClsCd() {
        return hzrdClsCd;
    }

    public void setHzrdClsCd(String hzrdClsCd) {
        this.hzrdClsCd = hzrdClsCd;
    }

    public String getHzrdClsDesc() {
        return hzrdClsDesc;
    }

    public void setHzrdClsDesc(String hzrdClsDesc) {
        this.hzrdClsDesc = hzrdClsDesc;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
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

        HazardClassification that = (HazardClassification) o;

        if (id != that.id) return false;
        if (hzrdClsCd != null ? !hzrdClsCd.equals(that.hzrdClsCd) : that.hzrdClsCd != null) return false;
        if (hzrdClsDesc != null ? !hzrdClsDesc.equals(that.hzrdClsDesc) : that.hzrdClsDesc != null) return false;
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
        result = 31 * result + (hzrdClsCd != null ? hzrdClsCd.hashCode() : 0);
        result = 31 * result + (hzrdClsDesc != null ? hzrdClsDesc.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
