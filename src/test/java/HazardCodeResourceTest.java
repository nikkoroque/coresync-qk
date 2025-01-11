import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.HazardCode;
import org.coresync.app.repository.inventory.HazardCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HazardCodeResourceTest {

    @Inject
    HazardCodeRepository hazardCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/hazard-code";
    }

    @Test
    void testGetHazardCodes() {
        List<HazardCode> hazardCodes = hazardCodeRepository.getHazardCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(hazardCodes.size()));
    }

    @Test
    void testGetHazardCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("description1", is("Alcohol"));
    }

    @Test
    void testValidateHazardCodeExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Hazard Code exists"));
    }

    @Test
    void testHazardCodeLifeCycle() {
        HazardCode mockHazardCode = new HazardCode(0, 1, "Desc 1", "Desc 2", "MP-001", "GOV-123",
                OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV");

        // Create a new Hazard Code
        int newHazardCodeId = given()
                .contentType(ContentType.JSON)
                .body(mockHazardCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the Hazard Code
        HazardCode mockUpdateHazardCode = new HazardCode(newHazardCodeId, 1, "Updated Desc 1", "Updated Desc 2", "MP" +
                "-001", "GOV-123", mockHazardCode.getCreationDate(), mockHazardCode.getCreatedByUser(), OffsetDateTime.now(), "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateHazardCode)
                .when()
                .put("/" + newHazardCodeId)
                .then()
                .statusCode(200);

        // Delete the Hazard Code
        given()
                .when()
                .delete("/" + newHazardCodeId)
                .then()
                .statusCode(200);
    }
}
