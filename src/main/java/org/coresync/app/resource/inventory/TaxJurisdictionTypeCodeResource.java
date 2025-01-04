package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.TaxJurisdictionTypeCode;
import org.coresync.app.model.TaxJurisdictionTypeCodeDTO;
import org.coresync.app.repository.inventory.TaxJurisdictionTypeCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/txjrs-typ-cd")
@Tag(name = "Tax Jurisdiction Type Code Resource", description = "Tax Jurisdiction api endpoints.")
public class TaxJurisdictionTypeCodeResource {
    @Inject
    TaxJurisdictionTypeCodeRepository taxJurisdictionTypeCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Tax Jurisdiction Type Codes", description = "Fetches all Tax Jurisdiction Type Codes in the database.")
    public List<TaxJurisdictionTypeCode> getAllTaxJurisdictionTypeCodes() {
        return taxJurisdictionTypeCodeRepository.getAllTaxJurisdictionTypeCodes();
    }

    @GET
    @Path("/type-codes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all code names for Metadata.", description = "Fetches all code names for metadata.")
    public List<TaxJurisdictionTypeCodeDTO> getTaxJurisdictionTypeCodesMetaData() {
        return taxJurisdictionTypeCodeRepository.getTaxJurisdictionTypeCodeDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Tax Judisdiction Type Code detail", description = "Fetches the details for a specific Tax Jurisdiction Type Code.")
    public Response getTaxJurisdictionTypeCodeDetail(@PathParam("id") int id) {
        try {
            Optional<TaxJurisdictionTypeCode> typeCode = taxJurisdictionTypeCodeRepository.getTaxJurisdictionTypeCodeDetail(id);
            return typeCode.map(taxType -> Response.ok(taxType).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Tax Jurisdiction Type Code not found.").build());
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
    @Operation(summary = "Add new Tax Jurisdiction Type Code", description = "Creates a new Tax Jurisdiction Type Code with the provided details.")
    public Response addTaxJurisdictioNTypeCode(TaxJurisdictionTypeCode taxJurisdictionTypeCode) {
        try {
            if (taxJurisdictionTypeCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Tax Jurisdiction Type Code cannot be null.").build();
            }

            if (taxJurisdictionTypeCode.getJurisdictionTypeCode() == null || taxJurisdictionTypeCode.getJurisdictionTypeCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Tax Jurisdiction Type Code cannot be null or empty.").build();
            }

            TaxJurisdictionTypeCode newTypeCode = taxJurisdictionTypeCodeRepository.addTaxJurisdictionTypeCode(taxJurisdictionTypeCode);

            return Response.status(Response.Status.CREATED).entity(newTypeCode).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity("Error: " + e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error occurred: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update Tax Jurisdiction Type Code", description = "Updates a tax jurisdiction type code.")
    public Response updateTaxJurisdictionTypeCode(@PathParam("id") int id, TaxJurisdictionTypeCode taxJurisdictionTypeCode) {
        if (taxJurisdictionTypeCode.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Tax Jurisdiction Type Code ID must match.").build();
        }
        try {
            TaxJurisdictionTypeCode updateTypeCode = taxJurisdictionTypeCodeRepository.updateTaxJurisdictionTypeCode(taxJurisdictionTypeCode);
            return Response.ok(updateTypeCode).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Tax Jurisdiction Type Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Tax Jurisdiction Type Code", description = "Deletes the tax jurisdiction type code provided in the parameter.")
    public Response deleteTaxJurisdictionTypeCode(@PathParam("id") int id) {
        try {
            taxJurisdictionTypeCodeRepository.getTaxJurisdictionTypeCodeDetail(id).orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Tax Jurisdiction Type Code does not exists.\"}"));

            taxJurisdictionTypeCodeRepository.deleteTaxJurisdictionTypeCode(id);
            return Response.ok().entity("{\"message\":\"Tax Jurisdiction Type Code deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Tax Jurisdiction Type Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Tax Jurisdiction Type Code existence by ID", description = "Validates whether a Tax Jurisdiction Type Code with the given ID exists.")
    public Response validateTaxJurisdictionTypeCodeExists(@PathParam("id") int id) {
        boolean isValid = taxJurisdictionTypeCodeRepository.validateTaxJurisdictionTypeCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Tax Jurisdiction Type Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Tax Jurisdiction Type Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/code/{typeCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Tax Jurisdiction Type Code existence by Jurisdiction Type Code",
            description = "Validates whether a Tax Jurisdiction Type Code with the given Code exists."
    )
    public Response validateTaxJurisdictionTypeCodeDuplicate(@PathParam("typeCode") String typeCode) {
        if (typeCode == null || typeCode.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Tax Jurisdiction Type Code is invalid\"}")
                    .build();
        }
        try {
            boolean exists = taxJurisdictionTypeCodeRepository.validateTaxJurisdictionTypeCodeDuplicate(typeCode);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Tax Jurisdiction Type Code already exists.\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Tax Jurisdiction Type Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Tax Jurisdiction Type Code.\"}")
                    .build();
        }
    }
}
