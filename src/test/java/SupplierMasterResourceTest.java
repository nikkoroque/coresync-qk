import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.SupplierMaster;
import org.coresync.app.repository.inventory.SupplierMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SupplierMasterResourceTest {
    @Inject
    private SupplierMasterRepository supplierMasterRepository;
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/supplier";
    }

    @Test
    void testGetAllSuppliers() {
        List<SupplierMaster> suppliers = supplierMasterRepository.getAllSuppliers();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(suppliers.size()));
    }

    @Test
    void testGetSupplierDetail() {
        given()
                .when()
                .get("/2")
                .then()
                .statusCode(200)
                .body("id", is(2))
                .body("supplierCode", is("111"));
    }

    @Test
    void testValidateSupplierExists() {
        given()
                .when()
                .get("/validate/supId/2")
                .then()
                .statusCode(409)
                .body("message", is("Supplier exists"));
    }

    @Test
    void testValidateSupplierCodeDuplicate() {
        given()
                .when()
                .get("/validate/sup/111")
                .then()
                .statusCode(409)
                .body("message", is("Supplier already exists."));
    }

    @Test
    void testSupplierLifeCycle() {
        SupplierMaster mockNewSupplier = new SupplierMaster(0, "999", "Kraft Corp", "Gardena CA", "Gardena", "CA", "90234-09829", "USA", "Y", "18009584665", "email@email.com", "458658245SC", "15496875461231", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        int newSupplierId = given()
                .contentType(ContentType.JSON)
                .body(mockNewSupplier)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        SupplierMaster mockUpdateSupplier = new SupplierMaster(newSupplierId, "999", "Kraft Inc", "Gardena CA", "Gardena", "CA", "90234-09829", "USA", "Y", "18009584665", "email@email.com", "458658245SC", "15496875461231", mockNewSupplier.getCreationDate(), mockNewSupplier.getCreatedByUser(),OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateSupplier)
                .when()
                .put("/" + newSupplierId)
                .then()
                .statusCode(200);

        given().when().delete("/" + newSupplierId).then().statusCode(200);
    }
}
