package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.HazardCode;
import org.coresync.app.model.HazardCodeDTO;
import org.coresync.app.repository.inventory.HazardCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/hazard-code")
@Tag(name = "Hazard Code Resource", description = "Hazard Code API endpoints")
public class HazardCodeResource {

    @Inject
    HazardCodeRepository hazardCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Hazard Codes", description = "Fetches all hazard codes.")
    public List<HazardCode> getHazardCodes() {
        return hazardCodeRepository.getHazardCodes();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Hazard Code Metadata", description = "Fetches metadata for hazard codes.")
    public List<HazardCodeDTO> getHazardCodeMetaData() {
        return hazardCodeRepository.getHazardCodesDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Hazard Code Detail", description = "Fetches the detail of a specific hazard code.")
    public Response getHazardCodeDetails(@PathParam("id") int id) {
        try {
            Optional<HazardCode> hazardCode = hazardCodeRepository.getHazardCodeDetail(id);
            return hazardCode
                    .map(code -> Response.ok(code).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Hazard Code not found.").build());
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
    @Operation(summary = "Create new Hazard Code", description = "Creates a new hazard code.")
    public Response createHazardCode(HazardCode hazardCode) {
        try {
            if (hazardCode == null || hazardCode.getDescription1() == null || hazardCode.getDescription1().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Hazard Code description cannot be null or empty.")
                        .build();
            }

            HazardCode createdCode = hazardCodeRepository.createHazardCode(hazardCode);
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
    @Operation(summary = "Update Hazard Code", description = "Updates an existing hazard code.")
    public Response updateHazardCode(@PathParam("id") int id, HazardCode hazardCode) {
        if (hazardCode.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Hazard Code ID must match.")
                    .build();
        }

        try {
            HazardCode updatedCode = hazardCodeRepository.updateHazardCode(hazardCode);
            return Response.ok(updatedCode).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Hazard Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Hazard Code", description = "Deletes a hazard code.")
    public Response deleteHazardCode(@PathParam("id") int id) {
        try {
            hazardCodeRepository.getHazardCodeDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Hazard Code does not exist.\"}"));

            hazardCodeRepository.deleteHazardCode(id);
            return Response.ok().entity("{\"message\":\"Hazard Code deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Hazard Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Hazard Code existence by ID", description = "Checks if a Hazard Code exists by its ID.")
    public Response validateHazardCodeExists(@PathParam("id") int id) {
        boolean exists = hazardCodeRepository.validateHazardCodeExists(id);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Hazard Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Hazard Code not found\", \"ID\":" + id + "}").build();
        }
    }
}
