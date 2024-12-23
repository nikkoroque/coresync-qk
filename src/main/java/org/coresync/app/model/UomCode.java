package org.coresync.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.coresync.app.util.TimestampDeserializer;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Entity
@Table(name = "uom_cd", schema = "inventory_mgt", catalog = "coresync")
public class UomCode {
    @Id
    @Column(name = "uom_cd")
    private String uomCd;
    @Basic
    @Column(name = "uom_cd_desc")
    private String uomCdDesc;
    @Basic
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Creation Date in 'yyyy-MM-dd HH:mm:ss' format", example = "2024-12-20 15:30:00")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp creationDate;
    @Basic
    @Column(name = "created_by_user")
    private String createdByUser;
    @Basic
    @Column(name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Creation Date in 'yyyy-MM-dd HH:mm:ss' format", example = "2024-12-20 15:30:00")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp lastUpdateDate;
    @Basic
    @Column(name = "last_updated_by_user")
    private String lastUpdatedByUser;

    public String getUomCd() {
        return uomCd;
    }

    public void setUomCd(String uomCd) {
        this.uomCd = uomCd;
    }

    public String getUomCdDesc() {
        return uomCdDesc;
    }

    public void setUomCdDesc(String uomCdDesc) {
        this.uomCdDesc = uomCdDesc;
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

        UomCode uomCode = (UomCode) o;

        if (uomCd != null ? !uomCd.equals(uomCode.uomCd) : uomCode.uomCd != null) return false;
        if (uomCdDesc != null ? !uomCdDesc.equals(uomCode.uomCdDesc) : uomCode.uomCdDesc != null) return false;
        if (creationDate != null ? !creationDate.equals(uomCode.creationDate) : uomCode.creationDate != null)
            return false;
        if (createdByUser != null ? !createdByUser.equals(uomCode.createdByUser) : uomCode.createdByUser != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(uomCode.lastUpdateDate) : uomCode.lastUpdateDate != null)
            return false;
        if (lastUpdatedByUser != null ? !lastUpdatedByUser.equals(uomCode.lastUpdatedByUser) : uomCode.lastUpdatedByUser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uomCd != null ? uomCd.hashCode() : 0;
        result = 31 * result + (uomCdDesc != null ? uomCdDesc.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
