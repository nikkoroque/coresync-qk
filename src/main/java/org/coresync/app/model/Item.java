package org.coresync.app.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "item_master", schema = "inventory_mgt", catalog = "coresync")
public class Item {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private int itemId;
    @Basic
    @Column(name = "item_desc")
    private String itemDesc;
    @Basic
    @Column(name = "img_link")
    private String imgLink;
    @Basic
    @Column(name = "upc")
    private String upc;
    @Basic
    @Column(name = "ordr_gtin")
    private String ordrGtin;
    @Basic
    @Column(name = "shp_gtin")
    private String shpGtin;
    @Basic
    @Column(name = "inv_cls_cd")
    private Integer invClsCd;
    @Basic
    @Column(name = "inv_cd")
    private Integer invCd;
    @Basic
    @Column(name = "stat_cd")
    private Integer statCd;
    @Basic
    @Column(name = "pricing_cost")
    private Double pricingCost;
    @Basic
    @Column(name = "pack_sz")
    private Integer packSz;
    @Basic
    @Column(name = "unit_qty")
    private Integer unitQty;
    @Basic
    @Column(name = "unit_sz")
    private Integer unitSz;
    @Basic
    @Column(name = "unit_uom_cd")
    private String unitUomCd;
    @Basic
    @Column(name = "ti")
    private Integer ti;
    @Basic
    @Column(name = "hi")
    private Integer hi;
    @Basic
    @Column(name = "item_cube")
    private Double itemCube;
    @Basic
    @Column(name = "shp_wgt")
    private Double shpWgt;
    @Basic
    @Column(name = "net_wgt")
    private Double netWgt;
    @Basic
    @Column(name = "season_sw")
    private String seasonSw;
    @Basic
    @Column(name = "cwt_sw")
    private String cwtSw;
    @Basic
    @Column(name = "allw_rcpt_sw")
    private String allwRcptSw;
    @Basic
    @Column(name = "prod_cd")
    private String prodCd;
    @Basic
    @Column(name = "trk_itm_cd")
    private String trkItmCd;
    @Basic
    @Column(name = "hzrd_cd")
    private Integer hzrdCd;
    @Basic
    @Column(name = "man_id")
    private Integer manId;
    @Basic
    @Column(name = "splr_id")
    private Integer splrId;
    @Basic
    @Column(name = "shelf_life_typ_cd")
    private String shelfLifeTypCd;
    @Basic
    @Column(name = "ttl_shelf_life")
    private Integer ttlShelfLife;
    @Basic
    @Column(name = "rcvg_shelf_life")
    private Integer rcvgShelfLife;
    @Basic
    @Column(name = "cust_age")
    private Integer custAge;
    @Basic
    @Column(name = "cust_shelf_life")
    private Integer custShelfLife;
    @Basic
    @Column(name = "rcvg_age")
    private Integer rcvgAge;
    @Basic
    @Column(name = "safe_stck_day")
    private Integer safeStckDay;
    @Basic
    @Column(name = "safe_stck_case")
    private Integer safeStckCase;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationDate;
    @Basic
    @Column(name = "created_by_user")
    private String createdByUser;
    @Basic
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;
    @Basic
    @Column(name = "last_updated_by_user")
    private String lastUpdatedByUser;

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

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getOrdrGtin() {
        return ordrGtin;
    }

    public void setOrdrGtin(String ordrGtin) {
        this.ordrGtin = ordrGtin;
    }

    public String getShpGtin() {
        return shpGtin;
    }

    public void setShpGtin(String shpGtin) {
        this.shpGtin = shpGtin;
    }

    public Integer getInvClsCd() {
        return invClsCd;
    }

    public void setInvClsCd(Integer invClsCd) {
        this.invClsCd = invClsCd;
    }

    public Integer getInvCd() {
        return invCd;
    }

    public void setInvCd(Integer invCd) {
        this.invCd = invCd;
    }

    public Integer getStatCd() {
        return statCd;
    }

    public void setStatCd(Integer statCd) {
        this.statCd = statCd;
    }

    public Double getPricingCost() {
        return pricingCost;
    }

    public void setPricingCost(Double pricingCost) {
        this.pricingCost = pricingCost;
    }

    public Integer getPackSz() {
        return packSz;
    }

    public void setPackSz(Integer packSz) {
        this.packSz = packSz;
    }

    public Integer getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(Integer unitQty) {
        this.unitQty = unitQty;
    }

    public Integer getUnitSz() {
        return unitSz;
    }

    public void setUnitSz(Integer unitSz) {
        this.unitSz = unitSz;
    }

    public String getUnitUomCd() {
        return unitUomCd;
    }

    public void setUnitUomCd(String unitUomCd) {
        this.unitUomCd = unitUomCd;
    }

    public Integer getTi() {
        return ti;
    }

    public void setTi(Integer ti) {
        this.ti = ti;
    }

    public Integer getHi() {
        return hi;
    }

    public void setHi(Integer hi) {
        this.hi = hi;
    }

    public Double getItemCube() {
        return itemCube;
    }

    public void setItemCube(Double itemCube) {
        this.itemCube = itemCube;
    }

    public Double getShpWgt() {
        return shpWgt;
    }

    public void setShpWgt(Double shpWgt) {
        this.shpWgt = shpWgt;
    }

    public Double getNetWgt() {
        return netWgt;
    }

    public void setNetWgt(Double netWgt) {
        this.netWgt = netWgt;
    }

    public String getSeasonSw() {
        return seasonSw;
    }

    public void setSeasonSw(String seasonSw) {
        this.seasonSw = seasonSw;
    }

    public String getCwtSw() {
        return cwtSw;
    }

    public void setCwtSw(String cwtSw) {
        this.cwtSw = cwtSw;
    }

    public String getAllwRcptSw() {
        return allwRcptSw;
    }

    public void setAllwRcptSw(String allwRcptSw) {
        this.allwRcptSw = allwRcptSw;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getTrkItmCd() {
        return trkItmCd;
    }

    public void setTrkItmCd(String trkItmCd) {
        this.trkItmCd = trkItmCd;
    }

    public Integer getHzrdCd() {
        return hzrdCd;
    }

    public void setHzrdCd(Integer hzrdCd) {
        this.hzrdCd = hzrdCd;
    }

    public Integer getManId() {
        return manId;
    }

    public void setManId(Integer manId) {
        this.manId = manId;
    }

    public Integer getSplrId() {
        return splrId;
    }

    public void setSplrId(Integer splrId) {
        this.splrId = splrId;
    }

    public String getShelfLifeTypCd() {
        return shelfLifeTypCd;
    }

    public void setShelfLifeTypCd(String shelfLifeTypCd) {
        this.shelfLifeTypCd = shelfLifeTypCd;
    }

    public Integer getTtlShelfLife() {
        return ttlShelfLife;
    }

    public void setTtlShelfLife(Integer ttlShelfLife) {
        this.ttlShelfLife = ttlShelfLife;
    }

    public Integer getRcvgShelfLife() {
        return rcvgShelfLife;
    }

    public void setRcvgShelfLife(Integer rcvgShelfLife) {
        this.rcvgShelfLife = rcvgShelfLife;
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public Integer getCustShelfLife() {
        return custShelfLife;
    }

    public void setCustShelfLife(Integer custShelfLife) {
        this.custShelfLife = custShelfLife;
    }

    public Integer getRcvgAge() {
        return rcvgAge;
    }

    public void setRcvgAge(Integer rcvgAge) {
        this.rcvgAge = rcvgAge;
    }

    public Integer getSafeStckDay() {
        return safeStckDay;
    }

    public void setSafeStckDay(Integer safeStckDay) {
        this.safeStckDay = safeStckDay;
    }

    public Integer getSafeStckCase() {
        return safeStckCase;
    }

    public void setSafeStckCase(Integer safeStckCase) {
        this.safeStckCase = safeStckCase;
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

        Item item = (Item) o;

        if (itemId != item.itemId) return false;
        if (itemDesc != null ? !itemDesc.equals(item.itemDesc) : item.itemDesc != null) return false;
        if (imgLink != null ? !imgLink.equals(item.imgLink) : item.imgLink != null) return false;
        if (upc != null ? !upc.equals(item.upc) : item.upc != null) return false;
        if (ordrGtin != null ? !ordrGtin.equals(item.ordrGtin) : item.ordrGtin != null) return false;
        if (shpGtin != null ? !shpGtin.equals(item.shpGtin) : item.shpGtin != null) return false;
        if (invClsCd != null ? !invClsCd.equals(item.invClsCd) : item.invClsCd != null) return false;
        if (invCd != null ? !invCd.equals(item.invCd) : item.invCd != null) return false;
        if (statCd != null ? !statCd.equals(item.statCd) : item.statCd != null) return false;
        if (pricingCost != null ? !pricingCost.equals(item.pricingCost) : item.pricingCost != null) return false;
        if (packSz != null ? !packSz.equals(item.packSz) : item.packSz != null) return false;
        if (unitQty != null ? !unitQty.equals(item.unitQty) : item.unitQty != null) return false;
        if (unitSz != null ? !unitSz.equals(item.unitSz) : item.unitSz != null) return false;
        if (unitUomCd != null ? !unitUomCd.equals(item.unitUomCd) : item.unitUomCd != null) return false;
        if (ti != null ? !ti.equals(item.ti) : item.ti != null) return false;
        if (hi != null ? !hi.equals(item.hi) : item.hi != null) return false;
        if (itemCube != null ? !itemCube.equals(item.itemCube) : item.itemCube != null) return false;
        if (shpWgt != null ? !shpWgt.equals(item.shpWgt) : item.shpWgt != null) return false;
        if (netWgt != null ? !netWgt.equals(item.netWgt) : item.netWgt != null) return false;
        if (seasonSw != null ? !seasonSw.equals(item.seasonSw) : item.seasonSw != null) return false;
        if (cwtSw != null ? !cwtSw.equals(item.cwtSw) : item.cwtSw != null) return false;
        if (allwRcptSw != null ? !allwRcptSw.equals(item.allwRcptSw) : item.allwRcptSw != null) return false;
        if (prodCd != null ? !prodCd.equals(item.prodCd) : item.prodCd != null) return false;
        if (trkItmCd != null ? !trkItmCd.equals(item.trkItmCd) : item.trkItmCd != null) return false;
        if (hzrdCd != null ? !hzrdCd.equals(item.hzrdCd) : item.hzrdCd != null) return false;
        if (manId != null ? !manId.equals(item.manId) : item.manId != null) return false;
        if (splrId != null ? !splrId.equals(item.splrId) : item.splrId != null) return false;
        if (shelfLifeTypCd != null ? !shelfLifeTypCd.equals(item.shelfLifeTypCd) : item.shelfLifeTypCd != null)
            return false;
        if (ttlShelfLife != null ? !ttlShelfLife.equals(item.ttlShelfLife) : item.ttlShelfLife != null) return false;
        if (rcvgShelfLife != null ? !rcvgShelfLife.equals(item.rcvgShelfLife) : item.rcvgShelfLife != null)
            return false;
        if (custAge != null ? !custAge.equals(item.custAge) : item.custAge != null) return false;
        if (custShelfLife != null ? !custShelfLife.equals(item.custShelfLife) : item.custShelfLife != null)
            return false;
        if (rcvgAge != null ? !rcvgAge.equals(item.rcvgAge) : item.rcvgAge != null) return false;
        if (safeStckDay != null ? !safeStckDay.equals(item.safeStckDay) : item.safeStckDay != null) return false;
        if (safeStckCase != null ? !safeStckCase.equals(item.safeStckCase) : item.safeStckCase != null) return false;
        if (creationDate != null ? !creationDate.equals(item.creationDate) : item.creationDate != null) return false;
        if (createdByUser != null ? !createdByUser.equals(item.createdByUser) : item.createdByUser != null)
            return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(item.lastUpdateDate) : item.lastUpdateDate != null)
            return false;
        if (lastUpdatedByUser != null ? !lastUpdatedByUser.equals(item.lastUpdatedByUser) : item.lastUpdatedByUser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + (itemDesc != null ? itemDesc.hashCode() : 0);
        result = 31 * result + (imgLink != null ? imgLink.hashCode() : 0);
        result = 31 * result + (upc != null ? upc.hashCode() : 0);
        result = 31 * result + (ordrGtin != null ? ordrGtin.hashCode() : 0);
        result = 31 * result + (shpGtin != null ? shpGtin.hashCode() : 0);
        result = 31 * result + (invClsCd != null ? invClsCd.hashCode() : 0);
        result = 31 * result + (invCd != null ? invCd.hashCode() : 0);
        result = 31 * result + (statCd != null ? statCd.hashCode() : 0);
        result = 31 * result + (pricingCost != null ? pricingCost.hashCode() : 0);
        result = 31 * result + (packSz != null ? packSz.hashCode() : 0);
        result = 31 * result + (unitQty != null ? unitQty.hashCode() : 0);
        result = 31 * result + (unitSz != null ? unitSz.hashCode() : 0);
        result = 31 * result + (unitUomCd != null ? unitUomCd.hashCode() : 0);
        result = 31 * result + (ti != null ? ti.hashCode() : 0);
        result = 31 * result + (hi != null ? hi.hashCode() : 0);
        result = 31 * result + (itemCube != null ? itemCube.hashCode() : 0);
        result = 31 * result + (shpWgt != null ? shpWgt.hashCode() : 0);
        result = 31 * result + (netWgt != null ? netWgt.hashCode() : 0);
        result = 31 * result + (seasonSw != null ? seasonSw.hashCode() : 0);
        result = 31 * result + (cwtSw != null ? cwtSw.hashCode() : 0);
        result = 31 * result + (allwRcptSw != null ? allwRcptSw.hashCode() : 0);
        result = 31 * result + (prodCd != null ? prodCd.hashCode() : 0);
        result = 31 * result + (trkItmCd != null ? trkItmCd.hashCode() : 0);
        result = 31 * result + (hzrdCd != null ? hzrdCd.hashCode() : 0);
        result = 31 * result + (manId != null ? manId.hashCode() : 0);
        result = 31 * result + (splrId != null ? splrId.hashCode() : 0);
        result = 31 * result + (shelfLifeTypCd != null ? shelfLifeTypCd.hashCode() : 0);
        result = 31 * result + (ttlShelfLife != null ? ttlShelfLife.hashCode() : 0);
        result = 31 * result + (rcvgShelfLife != null ? rcvgShelfLife.hashCode() : 0);
        result = 31 * result + (custAge != null ? custAge.hashCode() : 0);
        result = 31 * result + (custShelfLife != null ? custShelfLife.hashCode() : 0);
        result = 31 * result + (rcvgAge != null ? rcvgAge.hashCode() : 0);
        result = 31 * result + (safeStckDay != null ? safeStckDay.hashCode() : 0);
        result = 31 * result + (safeStckCase != null ? safeStckCase.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
