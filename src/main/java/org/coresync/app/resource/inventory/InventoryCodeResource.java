package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.InventoryCode;
import org.coresync.app.model.InventoryCodeDTO;
import org.coresync.app.repository.inventory.InventoryCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/inventory-code")
@Tag(name = "Inventory Code Resource", description = "Inventory Code API endpoints")
public class InventoryCodeResource {

    @Inject
    InventoryCodeRepository inventoryCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Inventory Codes", description = "Fetches all inventory codes.")
    public List<InventoryCode> getInventoryCodes() {
        return inventoryCodeRepository.getInventoryCodes();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Inventory Code Metadata", description = "Fetches metadata for inventory codes.")
    public List<InventoryCodeDTO> getInventoryCodeMetaData() {
        return inventoryCodeRepository.getInventoryCodesDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Inventory Code Detail", description = "Fetches the detail of a specific inventory code.")
    public Response getInventoryCodeDetails(@PathParam("id") int id) {
        try {
            Optional<InventoryCode> inventoryCode = inventoryCodeRepository.getInventoryCodeDetail(id);
            return inventoryCode
                    .map(code -> Response.ok(code).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Inventory Code not found.").build());
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
    @Operation(summary = "Create new Inventory Code", description = "Creates a new inventory code.")
    public Response createInventoryCode(InventoryCode inventoryCode) {
        try {
            if (inventoryCode == null || inventoryCode.getDescription() == null || inventoryCode.getDescription().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Inventory Code description cannot be null or empty.")
                        .build();
            }

            InventoryCode createdCode = inventoryCodeRepository.createInventoryCode(inventoryCode);
            return Response.status(Response.Status.CREATED).entity(createdCode).build();
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
    @Operation(summary = "Update Inventory Code", description = "Updates an existing inventory code.")
    public Response updateInventoryCode(@PathParam("id") int id, InventoryCode inventoryCode) {
        if (inventoryCode.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Inventory Code ID must match.")
                    .build();
        }

        try {
            InventoryCode updatedCode = inventoryCodeRepository.updateInventoryCode(inventoryCode);
            return Response.ok(updatedCode).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Inventory Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Inventory Code", description = "Deletes an inventory code.")
    public Response deleteInventoryCode(@PathParam("id") int id) {
        try {
            // Check if the inventory code exists
            inventoryCodeRepository.getInventoryCodeDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("Inventory Code does not exist"));

            // Perform the delete operation
            inventoryCodeRepository.deleteInventoryCode(id);
            return Response.ok().entity("Inventory Code deleted successfully.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Inventory Code: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Inventory Code existence by ID", description = "Checks if an Inventory Code exists by its ID.")
    public Response validateInventoryCodeExists(@PathParam("id") int id) {
        boolean exists = inventoryCodeRepository.validateInventoryCodeExists(id);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Inventory Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Inventory Code not found\", \"ID\":" + id + "}").build();
        }
    }

    @GET
    @Path("/validate/cd/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Inventory Code by description", description = "Checks if an Inventory Code description is unique.")
    public Response validateInventoryCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Inventory Code description is invalid.\"}")
                    .build();
        }

        boolean exists = inventoryCodeRepository.validateInventoryCodeDuplicate(code);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Inventory Code already exists.\"}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Inventory Code is available.\"}").build();
        }
    }
}
