package org.coresync.app.model;

import java.util.List;

public class ItemMasterMetadata {
    private List<BusinessUnitMasterDTO> businessUnits;
    private List<ManufacturerMasterDTO> manufacturers;
    private List<SupplierMasterDTO> suppliers;
    private List<ShelfLifeTypeCodeDTO> shelfLifeTypeCodes;
    private List<InventoryClassDTO> inventoryClasses;
    private List<InventoryCodeDTO> inventoryCodes;
    private List<HazardCodeDTO> hazardCodes;
    private List<StatusCodeDTO> statusCodes;
    private List<TrackItemCodeDTO> trackItemCodes;
    private List<UnitOfMeasureDTO> unitOfMeasures;
    private List<ProductCodeDTO> productCodes;

    public ItemMasterMetadata(List<BusinessUnitMasterDTO> businessUnits, List<ManufacturerMasterDTO> manufacturers, List<SupplierMasterDTO> suppliers, List<ShelfLifeTypeCodeDTO> shelfLifeTypeCodes, List<InventoryClassDTO> inventoryClasses, List<InventoryCodeDTO> inventoryCodes, List<HazardCodeDTO> hazardCodes, List<StatusCodeDTO> statusCodes, List<TrackItemCodeDTO> trackItemCodes, List<UnitOfMeasureDTO> unitOfMeasures, List<ProductCodeDTO> productCodes) {
        this.businessUnits = businessUnits;
        this.manufacturers = manufacturers;
        this.suppliers = suppliers;
        this.shelfLifeTypeCodes = shelfLifeTypeCodes;
        this.inventoryClasses = inventoryClasses;
        this.inventoryCodes = inventoryCodes;
        this.hazardCodes = hazardCodes;
        this.statusCodes = statusCodes;
        this.trackItemCodes = trackItemCodes;
        this.unitOfMeasures = unitOfMeasures;
        this.productCodes = productCodes;
    }

    public List<BusinessUnitMasterDTO> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<BusinessUnitMasterDTO> businessUnits) {
        this.businessUnits = businessUnits;
    }

    public List<ManufacturerMasterDTO> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<ManufacturerMasterDTO> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<SupplierMasterDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierMasterDTO> suppliers) {
        this.suppliers = suppliers;
    }

    public List<ShelfLifeTypeCodeDTO> getShelfLifeTypeCodes() {
        return shelfLifeTypeCodes;
    }

    public void setShelfLifeTypeCodes(List<ShelfLifeTypeCodeDTO> shelfLifeTypeCodes) {
        this.shelfLifeTypeCodes = shelfLifeTypeCodes;
    }

    public List<InventoryClassDTO> getInventoryClasses() {
        return inventoryClasses;
    }

    public void setInventoryClasses(List<InventoryClassDTO> inventoryClasses) {
        this.inventoryClasses = inventoryClasses;
    }

    public List<InventoryCodeDTO> getInventoryCodes() {
        return inventoryCodes;
    }

    public void setInventoryCodes(List<InventoryCodeDTO> inventoryCodes) {
        this.inventoryCodes = inventoryCodes;
    }

    public List<HazardCodeDTO> getHazardCodes() {
        return hazardCodes;
    }

    public void setHazardCodes(List<HazardCodeDTO> hazardCodes) {
        this.hazardCodes = hazardCodes;
    }

    public List<StatusCodeDTO> getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(List<StatusCodeDTO> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public List<TrackItemCodeDTO> getTrackItemCodes() {
        return trackItemCodes;
    }

    public void setTrackItemCodes(List<TrackItemCodeDTO> trackItemCodes) {
        this.trackItemCodes = trackItemCodes;
    }

    public List<UnitOfMeasureDTO> getUnitOfMeasures() {
        return unitOfMeasures;
    }

    public void setUnitOfMeasures(List<UnitOfMeasureDTO> unitOfMeasures) {
        this.unitOfMeasures = unitOfMeasures;
    }

    public List<ProductCodeDTO> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<ProductCodeDTO> productCodes) {
        this.productCodes = productCodes;
    }
}
