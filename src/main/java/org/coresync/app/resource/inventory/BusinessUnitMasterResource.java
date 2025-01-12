package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.BusinessUnitMaster;
import org.coresync.app.model.BusinessUnitMasterDTO;
import org.coresync.app.repository.inventory.BusinessUnitMasterRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/business-unit")
@Tag(name = "Business Unit Master Resource", description = "Business Unit Master API endpoints")
public class BusinessUnitMasterResource {

    @Inject
    BusinessUnitMasterRepository businessUnitRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Business Units", description = "Fetches all business units.")
    public List<BusinessUnitMaster> getBusinessUnits() {
        return businessUnitRepository.getBusinessUnits();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Business Unit Metadata", description = "Fetches metadata for business units.")
    public List<BusinessUnitMasterDTO> getBusinessUnitMetaData() {
        return businessUnitRepository.getBusinessUnitDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Business Unit Detail", description = "Fetches details of a specific business unit.")
    public Response getBusinessUnitDetail(@PathParam("id") int id) {
        try {
            Optional<BusinessUnitMaster> unit = businessUnitRepository.getBusinessUnitDetail(id);
            return unit.map(Response::ok)
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Business Unit not found."))
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
    @Operation(summary = "Create new Business Unit", description = "Creates a new business unit.")
    public Response createBusinessUnit(BusinessUnitMaster unit) {
        try {
            if (unit == null || unit.getBusinessUnitCode() == null || unit.getBusinessUnitCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Business Unit Code cannot be null or empty.")
                        .build();
            }

            BusinessUnitMaster createdUnit = businessUnitRepository.createBusinessUnit(unit);
            return Response.status(Response.Status.CREATED).entity(createdUnit).build();
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
    @Operation(summary = "Update Business Unit", description = "Updates an existing business unit.")
    public Response updateBusinessUnit(@PathParam("id") int id, BusinessUnitMaster unit) {
        if (unit.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Business Unit ID must match.")
                    .build();
        }

        try {
            BusinessUnitMaster updatedUnit = businessUnitRepository.updateBusinessUnit(unit);
            return Response.ok(updatedUnit).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Business Unit: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Business Unit", description = "Deletes a business unit.")
    public Response deleteBusinessUnit(@PathParam("id") int id) {
        try {
            businessUnitRepository.getBusinessUnitDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Business Unit does not exist.\"}"));

            businessUnitRepository.deleteBusinessUnit(id);
            return Response.ok().entity("{\"message\":\"Business Unit deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Business Unit: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Business Unit existence by ID", description = "Checks if a Business Unit exists by its ID.")
    public Response validateBusinessUnitExists(@PathParam("id") int id) {
        boolean exists = businessUnitRepository.validateBusinessUnitExists(id);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Business Unit exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Business Unit not found\", \"ID\":" + id + "}").build();
        }
    }

    @GET
    @Path("/validate/cd/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Business Unit by Code", description = "Checks if a Business Unit Code is unique.")
    public Response validateBusinessUnitDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Business Unit Code is invalid.\"}")
                    .build();
        }

        boolean exists = businessUnitRepository.validateBusinessUnitDuplicate(code);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Business Unit already exists.\"}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Business Unit Code is available.\"}").build();
        }
    }
}
