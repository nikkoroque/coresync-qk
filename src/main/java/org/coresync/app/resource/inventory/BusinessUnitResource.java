package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.repository.inventory.BusinessUnitRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/business-unit")
@Tag(name = "Business Unit Resource", description = "API for managing business units.")
public class BusinessUnitResource {

    @Inject
    private BusinessUnitRepository businessUnitRepository;

    /**
     * Fetch all Business Units.
     *
     * @return List of all Business Units.
     */
    @GET
    @Path("/") // /api/business-unit/
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all Business Units", description = "Fetches all business units without pagination.")
    public List<BusinessUnit> getBusinessUnits() {
        return businessUnitRepository.getAllBusinessUnits();
    }

    /**
     * Fetch paginated with sort Business Units.
     * @param page
     * @param sortBy
     * @param sortOrder
     * @return List of Business Units.
     */
    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get paginated Business Units",
            description = "Fetches a specific page of business units, sorted by default or specified criteria."
    )
    public Response getPaginatedBusinessUnits(
            @PathParam("page") long page,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder) {
        List<BusinessUnit> paginatedUnits = businessUnitRepository
                .getPaginatedBusinessUnit(page, sortBy, sortOrder)
                .collect(Collectors.toList());
        return Response.ok(paginatedUnits).build();
    }


    /**
     * Filter Business Units based on multiple criteria.
     *
     * @param buId Optional Business Unit ID.
     * @param buDesc Optional description to filter Business Units.
     * @param buGln Optional GLN to filter Business Units.
     * @param buCity Optional city to filter Business Units.
     * @param buState Optional state to filter Business Units.
     * @param buZip Optional zip code to filter Business Units.
     * @param buCountry Optional country to filter Business Units.
     * @return Response containing the filtered list of Business Units.
     */
    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Filter Business Units",
            description = "Filters business units based on multiple optional criteria such as description, city, state, etc."
    )
    public Response filterBusinessUnits(
            @QueryParam("buId") Integer buId,
            @QueryParam("buDesc") String buDesc,
            @QueryParam("buGln") String buGln,
            @QueryParam("buCity") String buCity,
            @QueryParam("buState") String buState,
            @QueryParam("buZip") String buZip,
            @QueryParam("buCountry") String buCountry) {
        List<BusinessUnit> filteredUnits = businessUnitRepository
                .filterBusinessUnits(buId, buDesc, buGln, buCity, buState, buZip, buCountry)
                .collect(Collectors.toList());
        return Response.ok(filteredUnits).build();
    }


    /**
     * Fetch details of a specific Business Unit by ID.
     *
     * @param buId the Business Unit ID.
     * @return Response containing the Business Unit details or 404 status.
     */
    @GET
    @Path("/{buId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get Business Unit by ID",
            description = "Fetches detailed information for a specific business unit based on its ID."
    )
    public Response getBusinessUnitDetail(@PathParam("buId") int buId) {
        Optional<BusinessUnit> businessUnit = businessUnitRepository.getBusinessUnitDetail(buId);
        return businessUnit.map(unit -> Response.ok(unit).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Business Unit with ID " + buId + " not found.")
                        .build());
    }

    /**
     * Add a new Business Unit.
     *
     * @param businessUnit the Business Unit to add.
     *
     * @return Response containing the created Business Unit.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Add a new Business Unit",
            description = "Creates a new business unit with the provided details."
    )
    public Response addBusinessUnit(BusinessUnit businessUnit) {
        if (businessUnit.getBuDesc() == null || businessUnit.getBuDesc().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Business Unit description cannot be null or empty.")
                    .build();
        }
        BusinessUnit newBusinessUnit = businessUnitRepository.addBusinessUnit(businessUnit);
        return Response.status(Response.Status.CREATED).entity(newBusinessUnit).build();
    }

    /**
     * Update an existing Business Unit.
     *
     * @param buId the ID of the Business Unit to update.
     * @param businessUnit the Business Unit data to update.
     * @return Response containing the updated Business Unit or an error status.
     */
    @PUT
    @Path("/{buId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a Business Unit", description = "Updates a business unit with the provided details")
    public Response updateBusinessUnit(@PathParam("buId") int buId, BusinessUnit businessUnit) {
        if (businessUnit.getBuId() != buId) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and BusinessUnit ID must match.")
                    .build();
        }
        try {
            BusinessUnit updatedBusinessUnit = businessUnitRepository.updateBusinessUnit(businessUnit);
            return Response.ok(updatedBusinessUnit).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating business unit: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Delete a Business Unit by ID.
     *
     * @param buId the ID of the Business Unit to delete.
     * @return Response indicating success or failure.
     */
    @DELETE
    @Path("/{buId}")
    @Operation(summary = "Deletes a business unit", description = "Deletes the business unit provided in the parameter submitted.")
    public Response deleteBusinessUnit(@PathParam("buId") int buId) {
        try {
            businessUnitRepository.deleteBusinessUnit(buId);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting business unit: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Check if a Business Unit exists by ID.
     *
     * @param buId the Business Unit ID to check.
     * @return Response indicating if the Business Unit exists.
     */
    @GET
    @Path("/validate/{buId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Business Unit existence",
            description = "Validates whether a business unit with the given ID exists."
    )
    public Response validateBusinessUnit(@PathParam("buId") int buId) {
        boolean isValid = businessUnitRepository.businessUnitExists(buId);

        if (isValid) {
            return Response.status(Response.Status.FOUND)
                    .entity("{\"message\":\"Business Unit exists\", \"buId\":" + buId + "}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Business Unit not found\", \"buId\":" + buId + "}")
                    .build();
        }
    }

}
