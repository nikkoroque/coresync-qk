package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.PaginatedResult;
import org.coresync.app.model.UnitOfMeasure;
import org.coresync.app.model.UnitOfMeasureDTO;
import org.coresync.app.repository.inventory.UomRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/uom")
@Tag(name = "Unit of Measure Resource", description = "Unit of measure api endpoints.")
public class UomResource {

    @Inject
    UomRepository uomRepository;

    /**
     * Fetches all UOM codes in the database.
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all UOM Codes", description = "Fetches all UOM codes in the database.")
    public List<UnitOfMeasure> getAllUomCodes() {
        return uomRepository.getAllUomCodes();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all UOM Code Metadata", description = "Fetches metadata for uom codes.")
    public List<UnitOfMeasureDTO> getUomCodeMetadata() {
        return uomRepository.getUomCodesDTO();
    }

    /**
     * Fetches the details for a specific UOM Code.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get UOM Code Detail", description = "Fetches the details for a specific UOM Code.")
    public Response getUomCodeDetail(@PathParam("id") int id) {
       try {
           Optional<UnitOfMeasure> unitCode = uomRepository.getUomCodeDetail(id);
           return unitCode.map(uom -> Response.ok(uom).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("UOM Code not found.").build());
       } catch (IllegalArgumentException e) {
           return Response.status(Response.Status.CONFLICT).entity("Error: " + e.getMessage()).build();
       } catch (Exception e) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error occured " + e.getMessage()).build();
       }
    }

    /**
     * Add a new Unit of Measure.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add new UOM Code", description = "Creates a new UOM Code with the provided details.")
    public Response addUomCode(UnitOfMeasure unitOfMeasure) {
        // Validation
        try {
            if (unitOfMeasure == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("UnitOfMeasure cannot be null.").build();
            }
            if (unitOfMeasure.getCode() == null || unitOfMeasure.getCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("UOM Code cannot be null or empty.").build();
            }

            // Add the UOM Code via the repository
            UnitOfMeasure newUomCode = uomRepository.addUomCode(unitOfMeasure);

            return Response.status(Response.Status.CREATED)
                    .entity(newUomCode)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity("Error: " + e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unexpected error occurred: " + e.getMessage()).build();
        }
    }


    /**
     * Update an existing Unit of Measure.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a UOM Code", description = "Updates a unit of measure with the provided details.")
    public Response updateUomCode(@PathParam("id") int id, UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and UOM Id must match.").build();
        }
        try {
            UnitOfMeasure updateCode = uomRepository.updateUomCode(unitOfMeasure);
            return Response.ok(updateCode).build();
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
    @Path("/{id}")
    @Operation(summary = "Deletes a Unit of Measurement", description = "Deletes the unit code provided in the parameter submitted.")
    public Response deleteUomCode(@PathParam("id") int id) {
        try {
            uomRepository.getUomCodeDetail(id).orElseThrow(() -> new IllegalArgumentException("{\"message\":\"UOM Code deleted successfully.\"}"));

            uomRepository.deleteUomCode(id);
            return Response.ok().entity("{\"message\":\"UOM Code deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting UOM code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check UOM Code existence by UOM ID",
            description = "Validates whether a UOM Code with the given ID exists."
    )
    public Response validateUomCodeExists(@PathParam("id") int id) {
        boolean isValid = uomRepository.validateUomCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"UOM Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"UOM Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/code/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check UOM Code existence by UOM Code",
            description = "Validates whether a UOM Code with the given Code exists."
    )
    public Response validateUomCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"UOM Code is invalid\"}")
                    .build();
        }

        try {
            boolean exists = uomRepository.validateUomCodeDuplicate(code);

            if (exists) {
                // UOM Code already exists
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"UOM Code already exists.\"}")
                        .build();
            }

            // UOM Code is available
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"UOM Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating UOM Code.\"}")
                    .build();
        }
    }


    // TODO: Future release pagination
    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Get paginated UOM Codes",
            description = "Fetches a specific page of UOM Codes, sorted by default or specified criteria."
    )
    public Response getPaginatedUomCodes(@PathParam("page") long page, @QueryParam("sortBy") String sortBy, @QueryParam("sortOrder") String sortOrder) {
        PaginatedResult<UnitOfMeasure> result = uomRepository.getPaginatedUomCodes(page, sortBy, sortOrder);

        // Create Response
        Map<String, Object> response = new HashMap<>();
        response.put("data", result.getData());
        response.put("totalItems", result.getTotalItems());

        return Response.ok(response).build();
    }

    // TODO: Future release filter
    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Filter Unit of Measurement",
            description = "Filters Unit of Measurement based on multiple optional criteria such as description and code."
    )
    public Response filterUomCodes(
            @QueryParam("code") String code,
            @QueryParam("description") String description,
            @QueryParam("creationDate") String creationDate,
            @QueryParam("createdByUser") String createdByUser,
            @QueryParam("lastUpdateDate") String lastUpdateDate,
            @QueryParam("lastUpdatedByUser") String lastUpdatedByUser
    ) {
        // Parse OffsetDateTime from the query parameters
        OffsetDateTime parsedCreationDate = null;
        OffsetDateTime parsedLastUpdateDate = null;

        try {
            if (creationDate != null) {
                parsedCreationDate = OffsetDateTime.parse(creationDate);
            }
            if (lastUpdateDate != null) {
                parsedLastUpdateDate = OffsetDateTime.parse(lastUpdateDate);
            }
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format for creationDate or lastUpdateDate. Expected ISO-8601 format.")
                    .build();
        }

        List<UnitOfMeasure> filteredCodes = uomRepository
                .filterUomCodes(code, description, parsedCreationDate, createdByUser, parsedLastUpdateDate, lastUpdatedByUser)
                .collect(Collectors.toList());

        return Response.ok(filteredCodes).build();
    }
}
