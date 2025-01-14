package org.coresync.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemMasterDTO {
    private int itemId;
    private String itemDesc;
    private String buId;
    private int invClsCd;
    private int statusCd;
    private Double pricingCost;
    private String cwtSw;
    private String splrId;

    // Use for Meta Data
    public ItemMasterDTO(int itemId, String itemDesc) {
        this.itemId = itemId;
        this.itemDesc = itemDesc;
    }

    // Use for getting item list for Item Master view Table
    public ItemMasterDTO(int itemId, String itemDesc, String buId, int invClsCd, int statusCd, Double pricingCost, String cwtSw, String splrId) {
        this.itemId = itemId;
        this.itemDesc = itemDesc;
        this.buId = buId;
        this.invClsCd = invClsCd;
        this.statusCd = statusCd;
        this.pricingCost = pricingCost;
        this.cwtSw = cwtSw;
        this.splrId = splrId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public int getInvClsCd() {
        return invClsCd;
    }

    public void setInvClsCd(int invClsCd) {
        this.invClsCd = invClsCd;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public Double getPricingCost() {
        return pricingCost;
    }

    public void setPricingCost(Double pricingCost) {
        this.pricingCost = pricingCost;
    }

    public String getCwtSw() {
        return cwtSw;
    }

    public void setCwtSw(String cwtSw) {
        this.cwtSw = cwtSw;
    }

    public String getSplrId() {
        return splrId;
    }

    public void setSplrId(String splrId) {
        this.splrId = splrId;
    }
}
