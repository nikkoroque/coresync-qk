package org.coresync.app.resource.inventory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.coresync.app.model.ProductCode;
import org.coresync.app.repository.inventory.ProductCodeRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/api/product-code")
@Tag(name = "Product Code Resource", description = "Product Code api endpoints")
public class ProductCodeResource {
    @Inject
    ProductCodeRepository productCodeRepository;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch all Product Codes", description = "Fetches all Product Codes.")
    public List<ProductCode> getProductCodes() {
        return productCodeRepository.getProductCodes();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get Product Code detail", description = "Fetches the details for a specific Product Code.")
    public Response getProductCodeDetail(@PathParam("id") int id) {
        try {
            Optional<ProductCode> productCd = productCodeRepository.getProductCodeDetail(id);

            return productCd.map(prodCd -> Response.ok(prodCd).build()).orElse(Response.status(Response.Status.NOT_FOUND).entity("Product Code not found.").build());
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
    @Operation(summary = "Add new Product Code", description = "Creates a new Product Code with the provided details.")
    public Response createProductCode(ProductCode productCode) {
        try {
            if (productCode == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Product Code cannot be null").build();
            }

            if (productCode.getSectionCode() == null || productCode.getSectionCode().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Section Code cannot be null or empty.").build();
            }

            ProductCode newProductCode = productCodeRepository.createProductCode(productCode);

            return Response.status(Response.Status.CREATED).entity(newProductCode).build();
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
    @Operation(summary = "Update Product Code", description = "Updates a Product Code.")
    public Response updateProductCode(@PathParam("id") int id, ProductCode productCode) {
        if (productCode.getId() != id ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Path ID and Product Code ID must match.").build();
        }

        try {
            ProductCode updateCode = productCodeRepository.updateProductCode(productCode);
            return Response.ok(updateCode).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating Product Code: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Product Code", description = "Deletes the Product Code provided in the parameter.")
    public Response deleteProductCode(@PathParam("id") int id) {
        try {
            productCodeRepository.getProductCodeDetail(id).orElseThrow(() -> new IllegalArgumentException("{\"message\":\"Product Code does not exists. \"}"));
            productCodeRepository.deleteProductCode(id);
            return Response.ok().entity("{\"message\":\"Product Code deleted successfully. \"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting Product Code: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/validate/productCdId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Check Product Code existence by ID", description = "Validates whether a Product Code with the given ID exists.")
    public Response validateProductCodeExists(@PathParam("id") int id) {
        boolean isValid = productCodeRepository.validateProductCodeExists(id);

        if (isValid) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\":\"Product Code exists\", \"ID\":" + id + "}")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Product Code not found\", \"ID\":" + id + "}")
                    .build();
        }
    }

    @GET
    @Path("/validate/productCd/{description}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Check Product Code existence by description",
            description = "Validates whether a Product Code with the given Code exists."
    )
    public Response validateProductCodeDuplicate(@PathParam("description") String description) {
        if (description == null || description.trim().isEmpty()) {
            // Handle invalid input
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Product Code is invalid\"}")
                    .build();
        }
        try {
            boolean exists = productCodeRepository.validateProductCodeDuplicate(description);

            if (exists) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"message\":\"Product Code already exists\"}")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"message\":\"Product Code is available.\"}")
                    .build();

        } catch (Exception e) {
            // Log the error and return a server error response
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error validating Product Code.\"}")
                    .build();
        }
    }
}
