package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.ItemMaster;
import org.coresync.app.model.ItemMasterDTO;
import org.coresync.app.repository.inventory.ItemMasterRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/item-master")
@Tag(name = "Item Master Resource", description = "Item Master API endpoints")
public class ItemMasterResource {

    @Inject
    ItemMasterRepository itemMasterRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Items", description = "Fetches all items.")
    public List<ItemMasterDTO> getItems() {
        return itemMasterRepository.getItems();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Item Metadata", description = "Fetches metadata for items.")
    public List<ItemMasterDTO> getItemMetaData() {
        return itemMasterRepository.getItemDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Item Detail", description = "Fetches the detail of a specific item.")
    public Response getItemDetails(@PathParam("id") int id) {
        try {
            Optional<ItemMaster> item = itemMasterRepository.getItemDetail(id);
            return item.map(Response::ok)
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Item not found."))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new Item", description = "Creates a new item.")
    public Response createItem(ItemMaster item) {
        try {
            if (item == null || item.getItemDescr() == null || item.getItemDescr().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Item description cannot be null or empty.")
                        .build();
            }

            ItemMaster createdItem = itemMasterRepository.createItem(item);
            return Response.status(Response.Status.CREATED).entity(createdItem).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update Item", description = "Updates an existing item.")
    public Response updateItem(@PathParam("id") int id, ItemMaster item) {
        if (item.getItemId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Item ID must match.")
                    .build();
        }

        try {
            ItemMaster updatedItem = itemMasterRepository.updateItem(item);
            return Response.ok(updatedItem).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Item: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Item", description = "Deletes an item.")
    public Response deleteItem(@PathParam("id") int id) {
        try {
            itemMasterRepository.getItemDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Item does not exist.\"}"));

            itemMasterRepository.deleteItem(id);
            return Response.ok().entity("{\"message\":\"Item deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Item: " + e.getMessage())
                    .build();
        }
    }


}
