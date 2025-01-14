package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.coresync.app.model.ItemMasterMetadata;
import org.coresync.app.repository.inventory.ItemMasterMetadataRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/item-master-form/metadata")
@Tag(name = "Item Master Metadata Resource", description = "API to fetch metadata for Item Master.")
public class ItemMasterMetadataResource {

    @Inject
    ItemMasterMetadataRepository itemMasterMetadataRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch metadata for Item Master", description = "Returns all metadata required for creating an Item Master.")
    public ItemMasterMetadata getItemMasterMetadata() {
        return itemMasterMetadataRepository.getItemMasterMetadata();
    }
}
