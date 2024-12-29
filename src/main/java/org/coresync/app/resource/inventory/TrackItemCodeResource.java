package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.TrackItemCode;
import org.coresync.app.repository.inventory.TrackItemCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/trk-itm-cd")
@Tag(name = "Track Item Code Resource", description = "Track Item Code API endpoints.")
public class TrackItemCodeResource {
    @Inject
    TrackItemCodeRepository trackItemCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Track Item Codes", description = "Fetches all codes in the database.")
    public List<TrackItemCode> getAllTrackItemCodes() {
        return trackItemCodeRepository.getAllTrackItemCodes();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Track Item Code detail", description = "Fetches details for a specific Track Item Code.")
    public Response getTrackItemCodeDetail(@PathParam("id") int id) {
        try {
            Optional<TrackItemCode> itemCodeDetail = trackItemCodeRepository.getTrackItemCodeDetail(id);
            return itemCodeDetail.map(itemCd -> Response.ok(itemCd).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Track Item Code with ID " + id + " does not exist").build());
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
    @Operation(summary = "Add new Track Item Code", description = "Creates a new Track Item Code with the provided details.")
    public Response addTrackItemCode(TrackItemCode trackItemCode) {
        try {
            if (trackItemCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Track Item Code cannot be null.").build();
            }
            if (trackItemCode.getCode() == null || trackItemCode.getCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Track Item Code cannot be null or empty.").build();
            }

            TrackItemCode newItemCode = trackItemCodeRepository.addTrackItemCode(trackItemCode);

            return Response.status(Response.Status.CREATED).entity(newItemCode).build();
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
    @Operation(summary = "Update a Track Item Code", description = "Updates a track item code with the provided details.")
    public Response updateTrackItemCode(@PathParam("id") int id, TrackItemCode trackItemCode) {
        if (trackItemCode.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Track Item Code ID must match.").build();
        }

        try {
            TrackItemCode updateItemCode = trackItemCodeRepository.updateTrackItemCode(trackItemCode);
            return Response.ok(updateItemCode).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Track Item Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Track Item Code", description = "Delete the track item code provided in the parameter.")
    public Response deleteTrackItemCode(@PathParam("id") int id) {
        try {
            trackItemCodeRepository.deleteTrackItemCode(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Track Item Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Track Item Code existence by ID.", description = "Validates whether a Track ITem Code with the given ID exists.")
    public Response validateTrackItemCodeExists(@PathParam("id") int id) {
        boolean isValid = trackItemCodeRepository.validateTrackItemCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Track Item Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Track Item Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/code/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Track Item Code existence by Item Code",
            description = "Validates whether a Track Item Code with the given Item Code exists."
    )
    public Response validateTrackItemCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Track Item Code is invalid\"}")
                    .build();
        }

        try {
            boolean exists = trackItemCodeRepository.validateTrackItemCodeDuplicate(code);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Track Item Code already exists.\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Track Item Code is available.\"}")
                    .build();
        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Track Item Code.\"}")
                    .build();
        }
    }
}
