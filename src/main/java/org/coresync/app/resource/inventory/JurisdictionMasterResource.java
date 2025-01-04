package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.JurisdictionMaster;
import org.coresync.app.model.JurisdictionMasterDTO;
import org.coresync.app.repository.inventory.JurisdictionMasterRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/jurisdiction")
@Tag(name = "Jurisdiction Resource", description = "Jurisdiction api endpoints")
public class JurisdictionMasterResource {
    @Inject
    JurisdictionMasterRepository jurisdictionMasterRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Jurisdictions", description = "Fetches all Jurisdiction Codes")
    public List<JurisdictionMaster> getJurisdictions() {
        return jurisdictionMasterRepository.getJurisdictions();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all code names for Metadata.", description = "Fetches all code names for metadata.")
    public List<JurisdictionMasterDTO> getJurisdictionMetaData() {
        return jurisdictionMasterRepository.getJurisdictionsDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Jurisdiction detail", description = "Fetches the detail for a specific Jurisdiction")
    public Response getJurisdictionDetails(@PathParam("id") int id) {
        try {
            Optional<JurisdictionMaster> jrs = jurisdictionMasterRepository.getJuridisctionDetail(id);

            return jrs.map(jurisdiction -> Response.ok(jurisdiction).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Jurisdiction not found.").build());
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity("Error: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error occured " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new Jurisdiction", description = "Creates a new Jurisdiction")
    public Response createJurisdiction(JurisdictionMaster jurisdiction) {
        try {
            if (jurisdiction == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Jurisdiction cannot be null.").build();
            }

            if (jurisdiction.getJurisdictionCode() == null || jurisdiction.getJurisdictionCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Jurisdiction code cannot be null or empty" +
                        ".").build();
            }

            JurisdictionMaster newJrsCd = jurisdictionMasterRepository.createJurisdiction(jurisdiction);

            return Response.status(Response.Status.CREATED).entity(newJrsCd).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity("Error: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error occurred: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update Jurisdiction", description = "Updates a Jurisdiction.")
    public Response updateJurisdiction(@PathParam("id") int id, JurisdictionMaster jurisdiction) {
        if (jurisdiction.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Jurisdiction ID must match.").build();
        }

        try {
            JurisdictionMaster updateJurisdiction = jurisdictionMasterRepository.updateJurisdiction(jurisdiction);
            return Response.ok(updateJurisdiction).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Jurisdiction: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Jurisdiction.", description = "Deletes a Jurisdiction")
    public Response deleteJurisdiction(@PathParam("id") int id) {
        try {
            // Check if the jurisdiction exists
            jurisdictionMasterRepository.getJuridisctionDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Jurisdiction does not exists\"}"));

            // Perform the delete operation
            jurisdictionMasterRepository.deleteJurisdiction(id);
            return Response.ok().entity("{\"message\":\"Jurisdiction deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Jurisdiction: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Jurisdiction existence by ID", description = "Validates whether a Jurisdiction with the given ID exists.")
    public Response validateJurisdictionExists(@PathParam("id") int id) {
        boolean isValid = jurisdictionMasterRepository.validateJurisdictionExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Jurisdiction exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Jurisdiction not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/cd/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Jurisdiction existence by description",
            description = "Validates whether a Jurisdiction with the given Code exists."
    )
    public Response validateJurisdictionDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Jurisdiction is invalid\"}")
                    .build();
        }
        try {
            boolean exists = jurisdictionMasterRepository.validateJurisdictionDuplicate(code);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Jurisdiction already exists\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Jurisdiction is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Jurisdiction.\"}")
                    .build();
        }
    }
}
