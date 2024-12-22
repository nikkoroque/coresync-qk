package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.HazardClassification;
import org.coresync.app.repository.inventory.HazardClassificationRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/hazard-classification")
@Tag(name = "Hazard Classification Resource", description = "API for managing hazard classification.")
public class HazardClassificationResource {

    @Inject
    private HazardClassificationRepository hazardClassificationRepository;

    /**
     * Fetch paginated with sort Hazard Classifications.
     * @param page
     * @param sortBy
     * @param sortOrder
     * @return List of Hazard Classifications.
     */
    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get paginated Hazard Classifications", description = "Fetches a specific page of hazard classifications, sorted by default or specific criteria.")
    public Response getPaginatedBusinessUnits(
            @PathParam("page") long page,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder) {
        List<HazardClassification> paginatedClasses = hazardClassificationRepository.getPaginatedHazardClassification(page, sortBy, sortOrder).collect(Collectors.toList());
        return Response.ok(paginatedClasses).build();
    }

    /**
     * Filter Hazard Classification based on multiple criteria.
     *
     * @param id Filter by Hazard Classification ID (nullable).
     * @param hzrdClsCd Filter by Hazard Classification Code.
     * @param hzrdClsDesc Filter by Hazard Classification description (nullable).
     * @param creationDateParam Filter by creation date.
     * @param createdByUser Filter by user who created the record.
     * @param lastUpdateDateParam Filter by last update date.
     * @param lastUpdatedByUser Filter by user who last updated the record.
     * @return Stream of filtered Hazard Classifications.
     */
    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Filter Hazard Classifications",
            description = "Filter hazard classifications on multiple optional criteria such as description, class code, class, creation date, created by, last updated date, and last updated by user"
    )
    public Response filterHazardClassifications(
            @QueryParam("id") Integer id,
            @QueryParam("hzrdClsCd") String hzrdClsCd,
            @QueryParam("hzrdClsDesc") String hzrdClsDesc,
            @QueryParam("creationDate") String creationDateParam,
            @QueryParam("createdByUser") String createdByUser,
            @QueryParam("lastUpdateDate") String lastUpdateDateParam,
            @QueryParam("lastUpdatedByUser") String lastUpdatedByUser
    ) {
        Timestamp creationDate = null;
        Timestamp lastUpdateDate = null;

        // Parse creationDate
        try {
            if (creationDateParam != null) {
                creationDate = Timestamp.valueOf(creationDateParam);
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid format for creationDate. Expected format: 'yyyy-MM-dd HH:mm:ss'")
                    .build();
        }

        // Parse lastUpdateDate
        try {
            if (lastUpdateDateParam != null) {
                lastUpdateDate = Timestamp.valueOf(lastUpdateDateParam);
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid format for lastUpdateDate. Expected format: 'yyyy-MM-dd HH:mm:ss'")
                    .build();
        }

        // Fetch filtered data
        List<HazardClassification> filteredClassifications = hazardClassificationRepository
                .filterHazardClassification(id, hzrdClsCd, hzrdClsDesc, creationDate, createdByUser, lastUpdateDate, lastUpdatedByUser)
                .collect(Collectors.toList());

        // Return response
        return Response.ok(filteredClassifications).build();
    }

    /**
     * Fetch details of a specific Hazard Classification by ID.
     *
     * @param hzrdClsCd the Hazard Classification ID.
     * @return Response containing the Hazard Classification details or 404 status.
     */
    @GET
    @Path("/{hzrdClsCd}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Hazard Classification Details", description = "Fetches detailed information for a specific hazard classification based on its ID.")
    public Response getHazardClassificationDetail(@PathParam("hzrdClsCd") String hzrdClsCd) {
        Optional<HazardClassification> hazardClassification = hazardClassificationRepository.getHazardClassificationDetail(hzrdClsCd);
        return hazardClassification.map(hzrd -> Response.ok(hzrd).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Hazard Classification Code: " + hzrdClsCd + " not found.").build());
    }

    /**
     * Add a new Hazard Classification.
     *
     * @param hazardClassification the Hazard Classification to add.
     *
     * @return Response containing the created Hazard Classification.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a new Hazard Classification", description = "Creates a new hazard classification.")
    public Response addHazardClassification(HazardClassification hazardClassification) {
        if (hazardClassification.getHzrdClsCd() == null || hazardClassification.getHzrdClsCd().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Hazard Classification Code cannot be null or empty.").build();
        }
        HazardClassification newHazardClassification = hazardClassificationRepository.addHazardClassification(hazardClassification);
        return Response.status(Response.Status.CREATED).entity(newHazardClassification).build();
    }

    /**
     * Update an existing Hazard Classification.
     *
     * @param hzrdClsCd the ID of the Hazard Classification to update.
     * @param hazardClassification the Hazard Classification data to update.
     * @return Response containing the updated Hazard Classification or an error status.
     */
    @PUT
    @Path("/{hzrdClsCd}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates a Hazard Classification", description = "Updates a hazard classification with the provided details")
    public Response updateHazardClassification(@PathParam("hzrdClsCd") String hzrdClsCd, HazardClassification hazardClassification) {
        if (!hazardClassification.getHzrdClsCd().equals(hzrdClsCd)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Hazard Classification Code must match.")
                    .build();
        }

        try {
            // Fetch existing entity to ensure it exists
            HazardClassification existing = hazardClassificationRepository.getHazardClassificationDetail(hzrdClsCd)
                    .orElseThrow(() -> new IllegalArgumentException("Hazard Classification with Code " + hzrdClsCd + " not found."));

            // Copy non-updatable fields from the existing entity
            hazardClassification.setId(existing.getId()); // Ensure the primary key is set
            hazardClassification.setCreationDate(existing.getCreationDate());
            hazardClassification.setCreatedByUser(existing.getCreatedByUser());

            // Perform the update
            HazardClassification updated = hazardClassificationRepository.updateHazardClassification(hazardClassification);

            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating hazard classification: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Delete a Hazard Classification by ID.
     *
     * @param hzrdClsCd the ID of the Hazard Classification to delete.
     * @return Response indicating success or failure.
     */
    @DELETE
    @Path("/{hzrdClsCd}")
    @Operation(summary = "Deletes a hazard classification", description = "Deletes the hazard classification provided in the parameter submitted.")
    public Response deleteHazardClassification(@PathParam("hzrdClsCd") String hzrdClsCd) {
        try {
            hazardClassificationRepository.deleteHazardClassification(hzrdClsCd);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting hazard classification: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/validate/{hzrdClsCd}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Hazard Classification Class Code is valid")
    public Response validateHazardClassificationId(@PathParam("hzrdClsCd") String hzrdClsCd) {
        boolean exists = hazardClassificationRepository.hazardClassificationExists(hzrdClsCd);

        if (exists) {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Hazard Classification exists\", \"Class Code\":\"" + hzrdClsCd + "\"}")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\":\"Hazard Classification not found\", \"Class Code\":\"" + hzrdClsCd + "\"}")
                    .build();
        }
    }



}
