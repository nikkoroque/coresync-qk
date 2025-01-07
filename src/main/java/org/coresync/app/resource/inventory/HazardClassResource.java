package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.HazardClass;
import org.coresync.app.model.HazardClassDTO;
import org.coresync.app.repository.inventory.HazardClassRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/hazard-class")
@Tag(name = "Hazard Class Resource", description = "Hazard Class API endpoints")
public class HazardClassResource {

    @Inject
    HazardClassRepository hazardClassRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Hazard Classes", description = "Fetches all hazard classes.")
    public List<HazardClass> getHazardClasses() {
        return hazardClassRepository.getHazardClasses();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Hazard Class Metadata", description = "Fetches metadata for hazard classes.")
    public List<HazardClassDTO> getHazardClassMetaData() {
        return hazardClassRepository.getHazardClassesDTO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch Hazard Class Detail", description = "Fetches the detail of a specific hazard class.")
    public Response getHazardClassDetails(@PathParam("id") int id) {
        try {
            Optional<HazardClass> hazardClass = hazardClassRepository.getHazardClassDetail(id);
            return hazardClass
                    .map(cls -> Response.ok(cls).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).entity("Hazard Class not found.").build());
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
    @Operation(summary = "Create new Hazard Class", description = "Creates a new hazard class.")
    public Response createHazardClass(HazardClass hazardClass) {
        try {
            if (hazardClass == null || hazardClass.getClassCode() == null || hazardClass.getClassCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Hazard Class code cannot be null or empty.")
                        .build();
            }

            HazardClass createdClass = hazardClassRepository.createHazardClass(hazardClass);
            return Response.status(Response.Status.CREATED).entity(createdClass).build();
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
    @Operation(summary = "Update Hazard Class", description = "Updates an existing hazard class.")
    public Response updateHazardClass(@PathParam("id") int id, HazardClass hazardClass) {
        if (hazardClass.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Path ID and Hazard Class ID must match.")
                    .build();
        }

        try {
            HazardClass updatedClass = hazardClassRepository.updateHazardClass(hazardClass);
            return Response.ok(updatedClass).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Hazard Class: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete Hazard Class", description = "Deletes a hazard class.")
    public Response deleteHazardClass(@PathParam("id") int id) {
        try {
            // Check if the hazard class exists
            hazardClassRepository.getHazardClassDetail(id)
                    .orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Hazard Class does not exist.\"}"));

            // Perform the delete operation
            hazardClassRepository.deleteHazardClass(id);
            return Response.ok().entity("{\"message\":\"Hazard Class deleted successfully.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Hazard Class: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Hazard Class existence by ID", description = "Checks if a Hazard Class exists by its ID.")
    public Response validateHazardClassExists(@PathParam("id") int id) {
        boolean exists = hazardClassRepository.validateHazardClassExists(id);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Hazard Class exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Hazard Class not found\", \"ID\":" + id + "}").build();
        }
    }

    @GET
    @Path("/validate/cd/{classCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Validate Hazard Class by code", description = "Checks if a Hazard Class code is unique.")
    public Response validateHazardClassDuplicate(@PathParam("classCode") String classCode) {
        if (classCode == null || classCode.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Hazard Class code is invalid.\"}")
                    .build();
        }

        boolean exists = hazardClassRepository.validateHazardClassDuplicate(classCode);
        if (exists) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Hazard Class already exists.\"}")
                    .build();
        } else {
            return Response.ok("{\"message\":\"Hazard Class is available.\"}").build();
        }
    }
}
