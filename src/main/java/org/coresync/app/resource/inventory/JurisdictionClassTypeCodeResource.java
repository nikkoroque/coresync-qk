package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.JurisdictionClassTypeCode;
import org.coresync.app.model.JurisdictionClassTypeCodeDTO;
import org.coresync.app.repository.inventory.JurisdictionClassTypeCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/jurisdiction-class-type-code")
@Tag(name = "Jurisdiction Class Type Code Resource", description = "Jurisdiction Class Type Code api endpoints")
public class JurisdictionClassTypeCodeResource {
    @Inject
    JurisdictionClassTypeCodeRepository jurisdictionClassTypeCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Jurisdiction Class Type Codes", description = "Fetches all Jurisdiction Class Type Codes.")
    public List<JurisdictionClassTypeCode> getJurisdictionClassTypeCodes() {
        return jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodes();
    }

    @GET
    @Path("/codes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all code names for Metadata.", description = "Fetches all code names for metadata.")
    public  List<JurisdictionClassTypeCodeDTO> getJurisdictionClassTypeCodesMetadata() {
        return jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodeDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Jurisdiction Class Type Code detail", description = "Fetches the details for a specific Jurisdiction Class Type Code.")
    public Response getJurisdictionClassTypeCodeDetail(@PathParam("id") int id) {
        try {
            Optional<JurisdictionClassTypeCode> jrsClsTypCd = jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodeDetail(id);

            return jrsClsTypCd.map(clsTyp -> Response.ok(clsTyp).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Jurisdiction Class Type Code not found.").build());
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
    @Operation(summary = "Add new Jurisdiction Class Type Code", description = "Creates a new Jurisdiction Class Type Code with the provided details.")
    public Response createJurisdictionClassTypeCode(JurisdictionClassTypeCode jurisdictionClassTypeCode) {
        try {
            if (jurisdictionClassTypeCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Jurisdiction Class Type Code cannot be null").build();
            }

            if (jurisdictionClassTypeCode.getClassTypeCode() == null || jurisdictionClassTypeCode.getClassTypeCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Jurisdiction Class Type Code cannot be null or empty.").build();
            }

            JurisdictionClassTypeCode newJrsClsTypCd = jurisdictionClassTypeCodeRepository.createJurisdictionClassTypeCode(jurisdictionClassTypeCode);

            return Response.status(Response.Status.CREATED).entity(newJrsClsTypCd).build();
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
    @Operation(summary = "Update Jurisdiction Class Type Code", description = "Updates a Jurisdiction Class Type Code.")
    public Response updateJurisdictionClassTypeCode(@PathParam("id") int id, JurisdictionClassTypeCode jurisdictionClassTypeCode) {
        if (jurisdictionClassTypeCode.getId() != id ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Jurisdiction Class Type Code ID must match.").build();
        }

        try {
            JurisdictionClassTypeCode updateJrsClsTypCd = jurisdictionClassTypeCodeRepository.updateClassTypeCode(jurisdictionClassTypeCode);
            return Response.ok(updateJrsClsTypCd).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Jurisdiction Class Type Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Jurisdiction Class Type Code", description = "Deletes the Jurisdiction Class Type Code provided in the parameter.")
    public Response deleteJurisdictionClassTypeCode(@PathParam("id") int id) {
        try {

            jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodeDetail(id).orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Jurisdiction Class Type Code does not exists \"}"));

            jurisdictionClassTypeCodeRepository.deleteJurisdictionClassTypeCode(id);
            return Response.ok().entity("{\"message\":\"Jurisdiction Class Type Code deleted successfully. \"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Jurisdiction Class Type Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Jurisdiction Class Type Code existence by ID", description = "Validates whether a Jurisdiction Class Type Code with the given ID exists.")
    public Response validateJurisdictionClassTypeCodeExists(@PathParam("id") int id) {
        boolean isValid = jurisdictionClassTypeCodeRepository.validateJurisdictionClassTypeCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Jurisdiction Class Type Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Jurisdiction Class Type Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/cd/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Jurisdiction Class Type Code existence by description",
            description = "Validates whether a Jurisdiction Class Type Code with the given Code exists."
    )
    public Response validateJurisdictionClassTypeCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Jurisdiction Class Type Code is invalid\"}")
                    .build();
        }
        try {
            boolean exists = jurisdictionClassTypeCodeRepository.validateJurisdictionClassTypeCodeDuplicate(code);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Jurisdiction Class Type Code already exists\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Jurisdiction Class Type Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Jurisdiction Class Type Code.\"}")
                    .build();
        }
    }
}
