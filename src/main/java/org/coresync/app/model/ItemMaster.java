package org.coresync.app.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "item_master", schema = "inventory_mgt", catalog = "coresync")
public class ItemMaster {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private int itemId;
    @Basic
    @Column(name = "bu_id")
    private String buId;
    @Basic
    @Column(name = "man_id")
    private String manId;
    @Basic
    @Column(name = "splr_id")
    private String splrId;
    @Basic
    @Column(name = "shelf_life_typ_cd")
    private String shelfLifeTypCd;
    @Basic
    @Column(name = "inv_cls_cd")
    private int invClsCd;
    @Basic
    @Column(name = "inv_cd")
    private int invCd;
    @Basic
    @Column(name = "hzrd_cd")
    private int hzrdCd;
    @Basic
    @Column(name = "stat_cd")
    private int statCd;
    @Basic
    @Column(name = "trk_item_cd")
    private String trkItemCd;
    @Basic
    @Column(name = "unit_uom_cd")
    private String unitUomCd;
    @Basic
    @Column(name = "prod_cd")
    private int prodCd;
    @Basic
    @Column(name = "upc")
    private Integer upc;
    @Basic
    @Column(name = "ordr_gtin")
    private String ordrGtin;
    @Basic
    @Column(name = "shp_gtin")
    private Integer shpGtin;
    @Basic
    @Column(name = "item_descr")
    private String itemDescr;
    @Basic
    @Column(name = "img_link")
    private String imgLink;
    @Basic
    @Column(name = "season_sw")
    private String seasonSw;
    @Basic
    @Column(name = "pricing_cost")
    private Double pricingCost;
    @Basic
    @Column(name = "pack_sz")
    private String packSz;
    @Basic
    @Column(name = "unit_qty")
    private Integer unitQty;
    @Basic
    @Column(name = "unit_sz")
    private Integer unitSz;
    @Basic
    @Column(name = "shp_wgt")
    private Double shpWgt;
    @Basic
    @Column(name = "net_wgt")
    private Double netWgt;
    @Basic
    @Column(name = "item_cube")
    private Double itemCube;
    @Basic
    @Column(name = "cwt_sw")
    private String cwtSw;
    @Basic
    @Column(name = "parnt_item_num")
    private Integer parntItemNum;
    @Basic
    @Column(name = "allw_rcpt_sw")
    private String allwRcptSw;
    @Basic
    @Column(name = "ti")
    private Integer ti;
    @Basic
    @Column(name = "hi")
    private Integer hi;
    @Basic
    @Column(name = "mstr_pack_fctor")
    private Integer mstrPackFctor;
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
    private OffsetDateTime creationDate;
    @Basic
    @Column(name = "created_by_user")
    private String createdByUser;
    @Basic
    @Column(name = "last_updated_date")
    private OffsetDateTime lastUpdatedDate;
    @Basic
    @Column(name = "last_updated_by_user")
    private String lastUpdatedByUser;

    public ItemMaster() {}

    public ItemMaster(int itemId, String buId, String manId, String splrId, String shelfLifeTypCd, int invClsCd, int invCd, int hzrdCd, int statCd, String trkItemCd, String unitUomCd, int prodCd, Integer upc, String ordrGtin, Integer shpGtin, String itemDescr, String imgLink, String seasonSw, Double pricingCost, String packSz, Integer unitQty, Integer unitSz, Double shpWgt, Double netWgt, Double itemCube, String cwtSw, Integer parntItemNum, String allwRcptSw, Integer ti, Integer hi, Integer mstrPackFctor, Integer ttlShelfLife, Integer rcvgShelfLife, Integer custAge, Integer custShelfLife, Integer rcvgAge, Integer safeStckDay, OffsetDateTime creationDate, String createdByUser, OffsetDateTime lastUpdatedDate, String lastUpdatedByUser) {
        this.itemId = itemId;
        this.buId = buId;
        this.manId = manId;
        this.splrId = splrId;
        this.shelfLifeTypCd = shelfLifeTypCd;
        this.invClsCd = invClsCd;
        this.invCd = invCd;
        this.hzrdCd = hzrdCd;
        this.statCd = statCd;
        this.trkItemCd = trkItemCd;
        this.unitUomCd = unitUomCd;
        this.prodCd = prodCd;
        this.upc = upc;
        this.ordrGtin = ordrGtin;
        this.shpGtin = shpGtin;
        this.itemDescr = itemDescr;
        this.imgLink = imgLink;
        this.seasonSw = seasonSw;
        this.pricingCost = pricingCost;
        this.packSz = packSz;
        this.unitQty = unitQty;
        this.unitSz = unitSz;
        this.shpWgt = shpWgt;
        this.netWgt = netWgt;
        this.itemCube = itemCube;
        this.cwtSw = cwtSw;
        this.parntItemNum = parntItemNum;
        this.allwRcptSw = allwRcptSw;
        this.ti = ti;
        this.hi = hi;
        this.mstrPackFctor = mstrPackFctor;
        this.ttlShelfLife = ttlShelfLife;
        this.rcvgShelfLife = rcvgShelfLife;
        this.custAge = custAge;
        this.custShelfLife = custShelfLife;
        this.rcvgAge = rcvgAge;
        this.safeStckDay = safeStckDay;
        this.safeStckCase = safeStckCase;
        this.creationDate = creationDate;
        this.createdByUser = createdByUser;
        this.lastUpdatedDate = lastUpdatedDate;
        this.lastUpdatedByUser = lastUpdatedByUser;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }

    public String getManId() {
        return manId;
    }

    public void setManId(String manId) {
        this.manId = manId;
    }

    public String getSplrId() {
        return splrId;
    }

    public void setSplrId(String splrId) {
        this.splrId = splrId;
    }

    public String getShelfLifeTypCd() {
        return shelfLifeTypCd;
    }

    public void setShelfLifeTypCd(String shelfLifeTypCd) {
        this.shelfLifeTypCd = shelfLifeTypCd;
    }

    public int getInvClsCd() {
        return invClsCd;
    }

    public void setInvClsCd(int invClsCd) {
        this.invClsCd = invClsCd;
    }

    public int getInvCd() {
        return invCd;
    }

    public void setInvCd(int invCd) {
        this.invCd = invCd;
    }

    public int getHzrdCd() {
        return hzrdCd;
    }

    public void setHzrdCd(int hzrdCd) {
        this.hzrdCd = hzrdCd;
    }

    public int getStatCd() {
        return statCd;
    }

    public void setStatCd(int statCd) {
        this.statCd = statCd;
    }

    public String getTrkItemCd() {
        return trkItemCd;
    }

    public void setTrkItemCd(String trkItemCd) {
        this.trkItemCd = trkItemCd;
    }

    public String getUnitUomCd() {
        return unitUomCd;
    }

    public void setUnitUomCd(String unitUomCd) {
        this.unitUomCd = unitUomCd;
    }

    public int getProdCd() {
        return prodCd;
    }

    public void setProdCd(int prodCd) {
        this.prodCd = prodCd;
    }

    public Integer getUpc() {
        return upc;
    }

    public void setUpc(Integer upc) {
        this.upc = upc;
    }

    public String getOrdrGtin() {
        return ordrGtin;
    }

    public void setOrdrGtin(String ordrGtin) {
        this.ordrGtin = ordrGtin;
    }

    public Integer getShpGtin() {
        return shpGtin;
    }

    public void setShpGtin(Integer shpGtin) {
        this.shpGtin = shpGtin;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getSeasonSw() {
        return seasonSw;
    }

    public void setSeasonSw(String seasonSw) {
        this.seasonSw = seasonSw;
    }

    public Double getPricingCost() {
        return pricingCost;
    }

    public void setPricingCost(Double pricingCost) {
        this.pricingCost = pricingCost;
    }

    public String getPackSz() {
        return packSz;
    }

    public void setPackSz(String packSz) {
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

    public Double getItemCube() {
        return itemCube;
    }

    public void setItemCube(Double itemCube) {
        this.itemCube = itemCube;
    }

    public String getCwtSw() {
        return cwtSw;
    }

    public void setCwtSw(String cwtSw) {
        this.cwtSw = cwtSw;
    }

    public Integer getParntItemNum() {
        return parntItemNum;
    }

    public void setParntItemNum(Integer parntItemNum) {
        this.parntItemNum = parntItemNum;
    }

    public String getAllwRcptSw() {
        return allwRcptSw;
    }

    public void setAllwRcptSw(String allwRcptSw) {
        this.allwRcptSw = allwRcptSw;
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

    public Integer getMstrPackFctor() {
        return mstrPackFctor;
    }

    public void setMstrPackFctor(Integer mstrPackFctor) {
        this.mstrPackFctor = mstrPackFctor;
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

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public OffsetDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(OffsetDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
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

        ItemMaster that = (ItemMaster) o;

        if (itemId != that.itemId) return false;
        if (invClsCd != that.invClsCd) return false;
        if (invCd != that.invCd) return false;
        if (hzrdCd != that.hzrdCd) return false;
        if (statCd != that.statCd) return false;
        if (prodCd != that.prodCd) return false;
        if (buId != null ? !buId.equals(that.buId) : that.buId != null) return false;
        if (manId != null ? !manId.equals(that.manId) : that.manId != null) return false;
        if (splrId != null ? !splrId.equals(that.splrId) : that.splrId != null) return false;
        if (shelfLifeTypCd != null ? !shelfLifeTypCd.equals(that.shelfLifeTypCd) : that.shelfLifeTypCd != null)
            return false;
        if (trkItemCd != null ? !trkItemCd.equals(that.trkItemCd) : that.trkItemCd != null) return false;
        if (unitUomCd != null ? !unitUomCd.equals(that.unitUomCd) : that.unitUomCd != null) return false;
        if (upc != null ? !upc.equals(that.upc) : that.upc != null) return false;
        if (ordrGtin != null ? !ordrGtin.equals(that.ordrGtin) : that.ordrGtin != null) return false;
        if (shpGtin != null ? !shpGtin.equals(that.shpGtin) : that.shpGtin != null) return false;
        if (itemDescr != null ? !itemDescr.equals(that.itemDescr) : that.itemDescr != null) return false;
        if (imgLink != null ? !imgLink.equals(that.imgLink) : that.imgLink != null) return false;
        if (seasonSw != null ? !seasonSw.equals(that.seasonSw) : that.seasonSw != null) return false;
        if (pricingCost != null ? !pricingCost.equals(that.pricingCost) : that.pricingCost != null) return false;
        if (packSz != null ? !packSz.equals(that.packSz) : that.packSz != null) return false;
        if (unitQty != null ? !unitQty.equals(that.unitQty) : that.unitQty != null) return false;
        if (unitSz != null ? !unitSz.equals(that.unitSz) : that.unitSz != null) return false;
        if (shpWgt != null ? !shpWgt.equals(that.shpWgt) : that.shpWgt != null) return false;
        if (netWgt != null ? !netWgt.equals(that.netWgt) : that.netWgt != null) return false;
        if (itemCube != null ? !itemCube.equals(that.itemCube) : that.itemCube != null) return false;
        if (cwtSw != null ? !cwtSw.equals(that.cwtSw) : that.cwtSw != null) return false;
        if (parntItemNum != null ? !parntItemNum.equals(that.parntItemNum) : that.parntItemNum != null) return false;
        if (allwRcptSw != null ? !allwRcptSw.equals(that.allwRcptSw) : that.allwRcptSw != null) return false;
        if (ti != null ? !ti.equals(that.ti) : that.ti != null) return false;
        if (hi != null ? !hi.equals(that.hi) : that.hi != null) return false;
        if (mstrPackFctor != null ? !mstrPackFctor.equals(that.mstrPackFctor) : that.mstrPackFctor != null)
            return false;
        if (ttlShelfLife != null ? !ttlShelfLife.equals(that.ttlShelfLife) : that.ttlShelfLife != null) return false;
        if (rcvgShelfLife != null ? !rcvgShelfLife.equals(that.rcvgShelfLife) : that.rcvgShelfLife != null)
            return false;
        if (custAge != null ? !custAge.equals(that.custAge) : that.custAge != null) return false;
        if (custShelfLife != null ? !custShelfLife.equals(that.custShelfLife) : that.custShelfLife != null)
            return false;
        if (rcvgAge != null ? !rcvgAge.equals(that.rcvgAge) : that.rcvgAge != null) return false;
        if (safeStckDay != null ? !safeStckDay.equals(that.safeStckDay) : that.safeStckDay != null) return false;
        if (safeStckCase != null ? !safeStckCase.equals(that.safeStckCase) : that.safeStckCase != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (createdByUser != null ? !createdByUser.equals(that.createdByUser) : that.createdByUser != null)
            return false;
        if (lastUpdatedDate != null ? !lastUpdatedDate.equals(that.lastUpdatedDate) : that.lastUpdatedDate != null)
            return false;
        if (lastUpdatedByUser != null ? !lastUpdatedByUser.equals(that.lastUpdatedByUser) : that.lastUpdatedByUser != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + (buId != null ? buId.hashCode() : 0);
        result = 31 * result + (manId != null ? manId.hashCode() : 0);
        result = 31 * result + (splrId != null ? splrId.hashCode() : 0);
        result = 31 * result + (shelfLifeTypCd != null ? shelfLifeTypCd.hashCode() : 0);
        result = 31 * result + invClsCd;
        result = 31 * result + invCd;
        result = 31 * result + hzrdCd;
        result = 31 * result + statCd;
        result = 31 * result + (trkItemCd != null ? trkItemCd.hashCode() : 0);
        result = 31 * result + (unitUomCd != null ? unitUomCd.hashCode() : 0);
        result = 31 * result + prodCd;
        result = 31 * result + (upc != null ? upc.hashCode() : 0);
        result = 31 * result + (ordrGtin != null ? ordrGtin.hashCode() : 0);
        result = 31 * result + (shpGtin != null ? shpGtin.hashCode() : 0);
        result = 31 * result + (itemDescr != null ? itemDescr.hashCode() : 0);
        result = 31 * result + (imgLink != null ? imgLink.hashCode() : 0);
        result = 31 * result + (seasonSw != null ? seasonSw.hashCode() : 0);
        result = 31 * result + (pricingCost != null ? pricingCost.hashCode() : 0);
        result = 31 * result + (packSz != null ? packSz.hashCode() : 0);
        result = 31 * result + (unitQty != null ? unitQty.hashCode() : 0);
        result = 31 * result + (unitSz != null ? unitSz.hashCode() : 0);
        result = 31 * result + (shpWgt != null ? shpWgt.hashCode() : 0);
        result = 31 * result + (netWgt != null ? netWgt.hashCode() : 0);
        result = 31 * result + (itemCube != null ? itemCube.hashCode() : 0);
        result = 31 * result + (cwtSw != null ? cwtSw.hashCode() : 0);
        result = 31 * result + (parntItemNum != null ? parntItemNum.hashCode() : 0);
        result = 31 * result + (allwRcptSw != null ? allwRcptSw.hashCode() : 0);
        result = 31 * result + (ti != null ? ti.hashCode() : 0);
        result = 31 * result + (hi != null ? hi.hashCode() : 0);
        result = 31 * result + (mstrPackFctor != null ? mstrPackFctor.hashCode() : 0);
        result = 31 * result + (ttlShelfLife != null ? ttlShelfLife.hashCode() : 0);
        result = 31 * result + (rcvgShelfLife != null ? rcvgShelfLife.hashCode() : 0);
        result = 31 * result + (custAge != null ? custAge.hashCode() : 0);
        result = 31 * result + (custShelfLife != null ? custShelfLife.hashCode() : 0);
        result = 31 * result + (rcvgAge != null ? rcvgAge.hashCode() : 0);
        result = 31 * result + (safeStckDay != null ? safeStckDay.hashCode() : 0);
        result = 31 * result + (safeStckCase != null ? safeStckCase.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (createdByUser != null ? createdByUser.hashCode() : 0);
        result = 31 * result + (lastUpdatedDate != null ? lastUpdatedDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedByUser != null ? lastUpdatedByUser.hashCode() : 0);
        return result;
    }
}
