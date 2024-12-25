import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.UnitOfMeasure;
import org.coresync.app.repository.inventory.UomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class UomResourceTest {

    @Inject
    private UomRepository uomRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/uom";
    }

    @Test
    void testGetAllUomCodes() {
        // Arrange
        List<UnitOfMeasure> uomCodes = uomRepository.getAllUomCodes();

        // Act & Assert
        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(uomCodes.size()));
    }

    @Test
    void testGetUomCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("code", is("EA"));
    }

    @Test
    void testValidateUomCodeExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("UOM Code exists"));
    }

    @Test
    void testValidateUomCodeDuplicate() {
        given()
                .when()
                .get("/validate/code/EA")
                .then()
                .statusCode(409)
                .body("message", is("UOM Code already exists."));
    }

    @Test
    void testUomLifeCycle() {
        // Step 1: Add new UOM Code
        UnitOfMeasure newUom = new UnitOfMeasure(
                0,
                "OZ",
                "Ounce",
                OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), // Valid ISO-8601 format with offset
                "QKTADEV",
                OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),
                "QKTADEV"
        );

        int newUomId = given()
                .contentType(ContentType.JSON)
                .body(newUom)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id"); // Capture the auto-generated ID

        // Step 2: Update UOM Code
        UnitOfMeasure updateUom = new UnitOfMeasure(
                newUomId, // Use the captured ID
                "OZ",                 // Updated code
                "Ounces",             // Updated description
                newUom.getCreationDate(),   // Preserve original created date
                newUom.getCreatedByUser(),  // Preserve original created by
                OffsetDateTime.parse("2024-12-20T15:30:00Z"), // Updated modified date with offset
                "QKTUDEV"                         // Updated modified by
        );

        given()
                .contentType(ContentType.JSON)
                .body(updateUom)
                .when()
                .put("/" + newUomId) // Use the captured ID for update
                .then()
                .statusCode(200);

        // Step 3: Delete UOM Code
        given()
                .when()
                .delete("/" + newUomId) // Use the captured ID for deletion
                .then()
                .statusCode(200);
    }
}
