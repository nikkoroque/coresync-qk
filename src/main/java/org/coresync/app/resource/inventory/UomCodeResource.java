package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.UomCode;
import org.coresync.app.repository.inventory.UomCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/uom")
@Tag(name = "UOM Code Resource", description = "API for managing uom codes.")
public class UomCodeResource {
    @Inject
    private UomCodeRepository uomCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all UOM Codes", description = "Fetches all UOM codes in the database.")
    public List<UomCode> getUomCodes() {
        return uomCodeRepository.getAllUomCodes();
    }

    @GET
    @Path("/{uomCd}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get UOM Code Detail", description = "Fetches the details for a specific UOM Code.")
    public Response getUomCodeDetail(@PathParam("uomCd") String uomCd) {
        Optional<UomCode> uomCode = uomCodeRepository.getUomCodeDetail(uomCd);
        return uomCode.map(code -> Response.ok(code). build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("UOM Code with ID " + uomCd + " not found.").build());
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add new UOM Code", description = "Creates a new UOM Code with the provided details.")
    public Response addUomCode(UomCode uomCode) {
        try {
            UomCode newUomCode = uomCodeRepository.addUomCode(uomCode);
            return Response.status(Response.Status.CREATED).entity(newUomCode).build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Error: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{uomCd}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a UOM Code", description = "Updates a UOM Code with the provided details.")
    public Response updateUomCode(@PathParam("uomCd") String uomCd, UomCode uomCode) {
        if (!uomCode.getUomCd().equals(uomCd)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and UOM ID must match.")
                    .build();
        }
        try {
            UomCode updatedUomCode = uomCodeRepository.updateUomCode(uomCode);
            return Response.ok(updatedUomCode).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating UOM Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{uomCd}")
    @Operation(summary = "Deletes a UOM Code", description = "Deletes the uom code provided in the parameter submitted.")
    public Response deleteBusinessUnit(@PathParam("uomCd") String uomCd) {
        try {
            uomCodeRepository.deleteUomCode(uomCd);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting uom code: " + e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/validate/{uomCd}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check UOM Code existence",
            description = "Validates whether a UOM code with the given ID exists."
    )
    public Response validateUomCode(@PathParam("uomCd") String uomCd) {
        boolean isValid = uomCodeRepository.uomCodeExists(uomCd);

        if (isValid) {
            return Response.status(Response.Status.FOUND)
                    .entity("{\"message\":\"UOM Code exists\", \"buId\":" + uomCd + "}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"UOM Code not found\", \"buId\":" + uomCd + "}")
                    .build();
        }
    }
}
