package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.InventoryClass;
import org.coresync.app.model.InventoryClassDTO;
import org.coresync.app.repository.inventory.InventoryClassRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/inventory-class")
@Tag(name = "Inventory Class Resource", description = "Inventory Class API endpoints")
public class InventoryClassResource {

    @Inject
    InventoryClassRepository inventoryClassRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Inventory Classes", description = "Fetches all inventory classes.")
    public List<InventoryClass> getInventoryClasses() {
        return inventoryClassRepository.getInventoryClasses();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Inventory Class Metadata", description = "Fetches metadata for inventory classes.")
    public List<InventoryClassDTO> getInventoryClassMetaData() {
        return inventoryClassRepository.getInventoryClassesDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Inventory Class Detail", description = "Fetches the detail of a specific inventory class.")
    public Response getInventoryClassDetails(@PathParam("id") int id) {
        try {
            Optional<InventoryClass> inventoryClass = inventoryClassRepository.getInventoryClassDetail(id);
            return inventoryClass
                    .map(cls -> Response.ok(cls).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Inventory Class not found.").build());
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
    @Operation(summary = "Create new Inventory Class", description = "Creates a new inventory class.")
    public Response createInventoryClass(InventoryClass inventoryClass) {
        try {
            if (inventoryClass == null || inventoryClass.getDescription() == null || inventoryClass.getDescription().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Inventory Class description cannot be null or empty.")
                        .build();
            }

            InventoryClass createdClass = inventoryClassRepository.createInventoryClass(inventoryClass);
            return Response.status(Response.Status.CREATED).entity(createdClass).build();
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
    @Operation(summary = "Update Inventory Class", description = "Updates an existing inventory class.")
    public Response updateInventoryClass(@PathParam("id") int id, InventoryClass inventoryClass) {
        if (inventoryClass.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Inventory Class ID must match.")
                    .build();
        }

        try {
            InventoryClass updatedClass = inventoryClassRepository.updateInventoryClass(inventoryClass);
            return Response.ok(updatedClass).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Inventory Class: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Inventory Class", description = "Deletes an inventory class.")
    public Response deleteInventoryClass(@PathParam("id") int id) {
        try {
            // Check if the inventory class exists
            inventoryClassRepository.getInventoryClassDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Inventory Class does not exist.\"}"));

            // Perform the delete operation
            inventoryClassRepository.deleteInventoryClass(id);
            return Response.ok().entity("{\"message\":\"Inventory Class deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Inventory Class: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Inventory Class existence by ID", description = "Checks if an Inventory Class exists by its ID.")
    public Response validateInventoryClassExists(@PathParam("id") int id) {
        boolean exists = inventoryClassRepository.validateInventoryClassExists(id);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Inventory Class exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Inventory Class not found\", \"ID\":" + id + "}").build();
        }
    }

    @GET
    @Path("/validate/cd/{description}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Inventory Class by description", description = "Checks if an Inventory Class description is unique.")
    public Response validateInventoryClassDuplicate(@PathParam("description") String description) {
        if (description == null || description.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Inventory Class description is invalid.\"}")
                    .build();
        }

        boolean exists = inventoryClassRepository.validateInventoryClassDuplicate(description);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Inventory Class already exists.\"}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Inventory Class is available.\"}").build();
        }
    }
}
