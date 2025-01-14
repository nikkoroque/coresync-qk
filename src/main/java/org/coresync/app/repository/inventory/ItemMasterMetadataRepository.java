package org.coresync.app.repository.inventory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.coresync.app.model.*;

import java.util.List;

@ApplicationScoped
public class ItemMasterMetadataRepository {

    @Inject
    BusinessUnitMasterRepository businessUnitMasterRepository;

    @Inject
    ManufacturerMasterRepository manufacturerMasterRepository;

    @Inject
    SupplierMasterRepository supplierMasterRepository;

    @Inject
    ShelfLifeTypeCodeRepository shelfLifeTypeCodeRepository;

    @Inject
    InventoryClassRepository inventoryClassRepository;

    @Inject
    InventoryCodeRepository inventoryCodeRepository;

    @Inject
    HazardCodeRepository hazardCodeRepository;

    @Inject
    StatusCodeRepository statusCodeRepository;

    @Inject
    TrackItemCodeRepository trackItemCodeRepository;

    @Inject
    UomRepository unitOfMeasureRepository;

    @Inject
    ProductCodeRepository productCodeRepository;

    public ItemMasterMetadata getItemMasterMetadata() {
        return new ItemMasterMetadata(
                businessUnitMasterRepository.getBusinessUnitDTO(),
                manufacturerMasterRepository.getManufacturerDTOs(),
                supplierMasterRepository.getAllSupplierDTO(),
                shelfLifeTypeCodeRepository.getShelfLifeTypeCodeDTO(),
                inventoryClassRepository.getInventoryClassesDTO(),
                inventoryCodeRepository.getInventoryCodesDTO(),
                hazardCodeRepository.getHazardCodesDTO(),
                statusCodeRepository.getAllStatusCodeDTO(),
                trackItemCodeRepository.getAllTrackItemCodeDTO(),
                unitOfMeasureRepository.getUomCodesDTO(),
                productCodeRepository.getProductCodesDTO()
        );
    }
}
