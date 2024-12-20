package org.coresync.app.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.repository.BusinessUnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/bu")
public class BusinessUnitResource {

    @Inject
    BusinessUnitRepository businessUnitRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessUnit> getBusinessUnits() {
        return businessUnitRepository.getAllBusinessUnits();
    }

    // Pagination
    @GET
    @Path("/page/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessUnit> getPaginatedBusinessUnits(@PathParam("page") long page) {
        return businessUnitRepository.getPaginatedBusinessUnit(page)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{buId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBusinessUnitDetail(@PathParam("buId") int buId) {
        Optional<BusinessUnit> businessUnit = businessUnitRepository.getBusinessUnitDetail(buId);

        if (businessUnit.isPresent()) {
            return Response.ok(businessUnit.get()).build(); // Return the BusinessUnit as JSON
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Business Unit with ID " + buId + " not found.")
                    .build(); // Return 404 if not found
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBusinessUnit(BusinessUnit businessUnit) {
        if (businessUnit.getBuDesc() == null || businessUnit.getBuDesc().isEmpty()) {
            throw new IllegalArgumentException("Business Unit description cannot be null or empty");
        }

        BusinessUnit newBusinessUnit = businessUnitRepository.addBusinessUnit(businessUnit);

        return Response.status(Response.Status.CREATED).entity(newBusinessUnit).build();
    }

    @PUT
    @Path("/{buId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBusinessUnit(@PathParam("buId") int buId, BusinessUnit businessUnit) {
        try {
            if (businessUnit.getBuId() != buId) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Path ID and BusinessUnit ID must match.")
                        .build();
            }
            BusinessUnit updatedBusinessUnit = businessUnitRepository.updateBusinessUnit(businessUnit);
            return Response.ok(updatedBusinessUnit).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating business unit: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{buId}")
    public Response deleteBusinessUnit(@PathParam("buId") int buId) {
        try {
            businessUnitRepository.deleteBusinessUnit(buId);
            return Response.status(Response.Status.OK).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build(); // 404 Not Found
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting business unit: " + e.getMessage())
                    .build(); // 500 Internal Server Error
        }
    }

    @GET
    @Path("/exists/{buId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateBusinessUnitExists(@PathParam("buId") int buId) {
        boolean exists = businessUnitRepository.businessUnitExists(buId);
        if (exists) {
            return Response.status(Response.Status.FOUND).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
