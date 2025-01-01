package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.StatusCode;
import org.coresync.app.repository.inventory.StatusCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/status-code")
@Tag(name = "Status Code Resource", description = "Status Code api endpoints")
public class StatusCodeResource {
    @Inject
    StatusCodeRepository statusCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Status Codes", description = "Fetches all Status Codes.")
    public List<StatusCode> getAllStatusCodes() {
        return statusCodeRepository.getAllStatusCodes();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Status Code detail", description = "Fetches the details for a specific Satus Code.")
    public Response getStatusCodeDetail(@PathParam("id") int id) {
        try {
            Optional<StatusCode> statusCd = statusCodeRepository.getStatusCodeDetail(id);

            return statusCd.map(status -> Response.ok(status).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Status Code not found.").build());
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
    @Operation(summary = "Add new Status Code", description = "Creates a new Status Code with the provided details.")
    public Response addStatusCode(StatusCode statusCode) {
        try {
            if (statusCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Status Code cannot be null").build();
            }

            if (statusCode.getDescription() == null || statusCode.getDescription().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Status Code cannot be null or empty.").build();
            }

            StatusCode newStatusCode = statusCodeRepository.addStatusCode(statusCode);

            return Response.status(Response.Status.CREATED).entity(newStatusCode).build();
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
    @Operation(summary = "Update Status Code", description = "Updates a Status Code.")
    public Response updateStatusCode(@PathParam("id") int id, StatusCode statusCode) {
        if (statusCode.getId() != id ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Status Code ID must match.").build();
        }

        try {
            StatusCode updateStatus = statusCodeRepository.updateStatusCode(statusCode);
            return Response.ok(updateStatus).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Status Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Status Code", description = "Deletes the Status Code provided in the parameter.")
    public Response deleteStatusCode(@PathParam("id") int id) {
        try {
            statusCodeRepository.deleteStatusCode(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Status Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/statusId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Status Code existence by ID", description = "Validates whether a Status Code with the given ID exists.")
    public Response validateStatusCodeExists(@PathParam("id") int id) {
        boolean isValid = statusCodeRepository.validateStatusCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Status Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Status Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/status/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Status Code existence by description",
            description = "Validates whether a Status Code with the given Code exists."
    )
    public Response validateStatusCodeDuplicate(@PathParam("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Status Code is invalid\"}")
                    .build();
        }
        try {
            boolean exists = statusCodeRepository.validateStatusCodeDuplicate(name);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Status Code already exists\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Status Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Status Code.\"}")
                    .build();
        }
    }

}
