package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.ShelfLifeTypeCode;
import org.coresync.app.model.ShelfLifeTypeCodeDTO;
import org.coresync.app.repository.inventory.ShelfLifeTypeCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/shelf-life-type-code")
@Tag(name = "Shelf Life Type Code Resource", description = "Shelf Life Type Code api endpoints")
public class ShelfLifeTypeCodeResource {

    @Inject
    ShelfLifeTypeCodeRepository shelfLifeTypeCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Shelf Life Type Codes", description = "Fetches all Shelf Life Type Codes.")
    public List<ShelfLifeTypeCode> getShelfLifeTypeCodes() {
        return shelfLifeTypeCodeRepository.getShelfLifeTypeCodes();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all code names for Metadata.", description = "Fetches all code names for metadata.")
    public  List<ShelfLifeTypeCodeDTO> getShelfLifeTypeCodesNames() {
        return shelfLifeTypeCodeRepository.getShelfLifeTypeCodeDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Shelf Life Type Code detail", description = "Fetches the details for a specific Shelf Life Type Code.")
    public Response getShelfLifeTypeCodeDetail(@PathParam("id") int id) {
        try {
            Optional<ShelfLifeTypeCode> shlfLfTypCd = shelfLifeTypeCodeRepository.getShelfLifeTypeCodeDetail(id);

            return shlfLfTypCd.map(sltCd -> Response.ok(sltCd).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Shelf Life Type Code not found.").build());
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
    @Operation(summary = "Add new Shelf Life Type Code", description = "Creates a new Shelf Life Type Code with the provided details.")
    public Response createShelfLifeTypeCode(ShelfLifeTypeCode shelfLifeTypeCode) {
        try {
            if (shelfLifeTypeCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Shelf Life Type Code cannot be null").build();
            }

            if (shelfLifeTypeCode.getCode() == null || shelfLifeTypeCode.getCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Shelf Life Type Code cannot be null or empty.").build();
            }

            ShelfLifeTypeCode newShelfLifeTypeCode = shelfLifeTypeCodeRepository.createShelfLifeTypeCode(shelfLifeTypeCode);

            return Response.status(Response.Status.CREATED).entity(newShelfLifeTypeCode).build();
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
    @Operation(summary = "Update Shelf Life Type Code", description = "Updates a Shelf Life Type Code.")
    public Response updateShelfLifeTypeCode(@PathParam("id") int id, ShelfLifeTypeCode shelfLifeTypeCode) {
        if (shelfLifeTypeCode.getId() != id ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Shelf Life Type Code ID must match.").build();
        }

        try {
            ShelfLifeTypeCode updateSltCd = shelfLifeTypeCodeRepository.updateShelfLifeTypeCode(shelfLifeTypeCode);
            return Response.ok(updateSltCd).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Shelf Life Type Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Shelf Life Type Code", description = "Deletes the Shelf Life Type Code provided in the parameter.")
    public Response deleteShelfLifeTypeCode(@PathParam("id") int id) {
        try {
            shelfLifeTypeCodeRepository.getShelfLifeTypeCodeDetail(id).orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Shelf Life Type Code does not exists.\"}"));

            shelfLifeTypeCodeRepository.deleteShelfLifeTypeCode(id);
            return Response.ok().entity("{\"message\":\"Shelf Life Type Code deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Shelf Life Type Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/sltCdId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Shelf Life Type Code existence by ID", description = "Validates whether a Shelf Life Type Code with the given ID exists.")
    public Response validateShelfLifeTypeCodeExists(@PathParam("id") int id) {
        boolean isValid = shelfLifeTypeCodeRepository.validateShelfLifeTypeCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Shelf Life Type Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Shelf Life Type Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/sltCd/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Shelf Life Type Code existence by description",
            description = "Validates whether a Shelf Life Type Code with the given Code exists."
    )
    public Response validateShelfLifeTypeCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Shelf Life Type Code is invalid\"}")
                    .build();
        }
        try {
            boolean exists = shelfLifeTypeCodeRepository.validateShelfLifeTypeCodeDuplicate(code);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Shelf Life Type Code already exists\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Shelf Life Type Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Shelf Life Type Code.\"}")
                    .build();
        }
    }
}
