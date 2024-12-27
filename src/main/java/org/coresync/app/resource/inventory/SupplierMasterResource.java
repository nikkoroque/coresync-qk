package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.SupplierMaster;
import org.coresync.app.repository.inventory.SupplierMasterRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/supplier")
@Tag(name = "Supplier Resource", description = "Supplier Master API endpoints.")
public class SupplierMasterResource {
    @Inject
    private SupplierMasterRepository supplierMasterRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Suppliers", description = "Fetches all suppliers.")
    public List<SupplierMaster> getAllSuppliers() {
        return supplierMasterRepository.getAllSuppliers();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Supplier detail", description = "Fetches details for a specific supplier")
    public Response getSupplierDetail(@PathParam("id") int id) {
        try {
            Optional<SupplierMaster> supplierDetail = supplierMasterRepository.getSupplierDetail(id);
            return supplierDetail.map(sup -> Response.ok(sup).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Supplier with ID " + id + " does not exist").build());
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
    @Operation(summary = "Add new Supplier", description = "Creates a new Supplier with the provided details.")
    public Response addSupplier(SupplierMaster supplierMaster) {
        try {
            if (supplierMaster == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Supplier cannot be null.").build();
            }
            if (supplierMaster.getSupplierCode() == null || supplierMaster.getSupplierCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Supplier cannot be null or empty.").build();
            }

            SupplierMaster newSupplier = supplierMasterRepository.addSupplier(supplierMaster);

            return Response.status(Response.Status.CREATED).entity(newSupplier).build();
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
    @Operation(summary = "Update a Supplier", description = "Updates a track item code with the provided details.")
    public Response updateSupplier(@PathParam("id") int id, SupplierMaster supplierMaster) {
        if (supplierMaster.getId() != id) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Supplier ID must match.").build();
        }
        try {
            SupplierMaster updSup = supplierMasterRepository.updateSupplier(supplierMaster);
            return Response.ok(updSup).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Supplier: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Supplier", description = "Delete the track item code provided in the parameter.")
    public Response deleteSupplier(@PathParam("id") int id) {
        try {
            supplierMasterRepository.deleteSupplier(id);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Supplier: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/supId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Supplier existence by ID.", description = "Validates whether a Supplier with the given ID exists.")
    public Response validateSupplierExists(@PathParam("id") int id) {
        boolean isValid = supplierMasterRepository.validateSupplierExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Supplier exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Supplier not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/sup/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Supplier existence by Item Code",
            description = "Validates whether a Supplier with the given Item Code exists."
    )
    public Response validateTrackItemCodeDuplicate(@PathParam("code") String code) {
        if (code == null || code.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Supplier is invalid\"}")
                    .build();
        }

        try {
            boolean exists = supplierMasterRepository.validateSupplierDuplicate(code);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Supplier already exists.\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Supplier is available.\"}")
                    .build();
        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Supplier.\"}")
                    .build();
        }
    }
}
