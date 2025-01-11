package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.ManufacturerMaster;
import org.coresync.app.model.ManufacturerMasterDTO;
import org.coresync.app.repository.inventory.ManufacturerMasterRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/manufacturer")
@Tag(name = "Manufacturer Master Resource", description = "Manufacturer Master API endpoints")
public class ManufacturerMasterResource {

    @Inject
    ManufacturerMasterRepository manufacturerRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Manufacturers", description = "Fetches all manufacturers.")
    public List<ManufacturerMaster> getManufacturers() {
        return manufacturerRepository.getManufacturers();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Manufacturer Metadata", description = "Fetches metadata for manufacturers.")
    public List<ManufacturerMasterDTO> getManufacturerMetaData() {
        return manufacturerRepository.getManufacturerDTOs();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Manufacturer Detail", description = "Fetches details of a specific manufacturer.")
    public Response getManufacturerDetail(@PathParam("id") int id) {
        try {
            Optional<ManufacturerMaster> manufacturer = manufacturerRepository.getManufacturerDetail(id);
            return manufacturer
                    .map(Response::ok)
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Manufacturer not found."))
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
    @Operation(summary = "Create new Manufacturer", description = "Creates a new manufacturer.")
    public Response createManufacturer(ManufacturerMaster manufacturer) {
        try {
            if (manufacturer == null || manufacturer.getManufacturerCode() == null || manufacturer.getManufacturerCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Manufacturer Code cannot be null or empty.")
                        .build();
            }

            ManufacturerMaster createdManufacturer = manufacturerRepository.createManufacturer(manufacturer);
            return Response.status(Response.Status.CREATED).entity(createdManufacturer).build();
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
    @Operation(summary = "Update Manufacturer", description = "Updates an existing manufacturer.")
    public Response updateManufacturer(@PathParam("id") int id, ManufacturerMaster manufacturer) {
        if (manufacturer.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Manufacturer ID must match.")
                    .build();
        }

        try {
            ManufacturerMaster updatedManufacturer = manufacturerRepository.updateManufacturer(manufacturer);
            return Response.ok(updatedManufacturer).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Manufacturer: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Manufacturer", description = "Deletes a manufacturer.")
    public Response deleteManufacturer(@PathParam("id") int id) {
        try {
            manufacturerRepository.getManufacturerDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Manufacturer does not exist.\"}"));

            manufacturerRepository.deleteManufacturer(id);
            return Response.ok().entity("{\"message\":\"Manufacturer deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Manufacturer: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Manufacturer existence by ID", description = "Checks if a Manufacturer exists by its ID.")
    public Response validateManufacturerExists(@PathParam("id") int id) {
        boolean exists = manufacturerRepository.validateManufacturerExists(id);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Manufacturer exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Manufacturer not found\", \"ID\":" + id + "}").build();
        }
    }

    @GET
    @Path("/validate/cd/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Manufacturer by Code", description = "Checks if a Manufacturer Code is unique.")
    public Response validateManufacturerDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Manufacturer Code is invalid.\"}")
                    .build();
        }

        boolean exists = manufacturerRepository.validateManufacturerDuplicate(code);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Manufacturer already exists.\"}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Manufacturer Code is available.\"}").build();
        }
    }
}
