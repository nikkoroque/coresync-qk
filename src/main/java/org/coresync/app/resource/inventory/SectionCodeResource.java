package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.SectionCode;
import org.coresync.app.repository.inventory.SectionCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/section-code")
@Tag(name = "Section Code Resouce", description = "Section Code api endpoints")
public class SectionCodeResource {
    @Inject
    SectionCodeRepository sectionCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Section Codes", description = "Fetches all Section Codes.")
    public List<SectionCode> getSectionCodes() {
        return sectionCodeRepository.getSectionCodes();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Section Code detail", description = "Fetches the details for a specific Satus Code.")
    public Response getSectionCodeDetail(@PathParam("id") int id) {
        try {
            Optional<SectionCode> sectionCd = sectionCodeRepository.getSectionCodeDetail(id);

            return sectionCd.map(section -> Response.ok(section).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Section Code not found.").build());
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
    @Operation(summary = "Add new Section Code", description = "Creates a new Section Code with the provided details.")
    public Response createSectionCode(SectionCode sectionCode) {
        try {
            if (sectionCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Section Code cannot be null").build();
            }

            if (sectionCode.getCode() == null || sectionCode.getCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Section Code cannot be null or empty.").build();
            }

            SectionCode newSectionCode = sectionCodeRepository.createSectionCode(sectionCode);

            return Response.status(Response.Status.CREATED).entity(newSectionCode).build();
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
    @Operation(summary = "Update Section Code", description = "Updates a Section Code.")
    public Response updateSectionCode(@PathParam("id") int id, SectionCode sectionCode) {
        if (sectionCode.getId() != id ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Section Code ID must match.").build();
        }

        try {
            SectionCode updateCode = sectionCodeRepository.updateSectionCode(sectionCode);
            return Response.ok(updateCode).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Section Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Section Code", description = "Deletes the Section Code provided in the parameter.")
    public Response deleteSectionCode(@PathParam("id") int id) {
        try {
            sectionCodeRepository.deleteSectionCode(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Section Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/sectionId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Section Code existence by ID", description = "Validates whether a Section Code with the given ID exists.")
    public Response validateSectionCodeExists(@PathParam("id") int id) {
        boolean isValid = sectionCodeRepository.validateSectionCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Section Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Section Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/section/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Section Code existence by description",
            description = "Validates whether a Section Code with the given Code exists."
    )
    public Response validateSectionCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Section Code is invalid\"}")
                    .build();
        }
        try {
            boolean exists = sectionCodeRepository.validateSectionCodeDuplicate(code);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Section Code already exists\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Section Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Section Code.\"}")
                    .build();
        }
    }
}
