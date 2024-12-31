import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.StatusCode;
import org.coresync.app.repository.inventory.StatusCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class StatusCodeResourceTest {
    @Inject
    StatusCodeRepository statusCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/status-code";
    }

    @Test
    void testGetStatusCodes() {
        List<StatusCode> statusCodes = statusCodeRepository.getAllStatusCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(statusCodes.size()));
    }

    @Test
    void testGetStatusCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("description", is("ACTIVE"));
    }

    @Test
    void testValidateStatusCodeExists() {
        given()
                .when()
                .get("/validate/statusId/1")
                .then()
                .statusCode(409)
                .body("message", is("Status Code exists"));
    }

    @Test
    void validateStatusCodeDuplicate() {
        given()
                .when()
                .get("/validate/status/ACTIVE")
                .then()
                .statusCode(409)
                .body("message", is("Status Code already exists"));
    }

    @Test
    void testStatusCodeLifeCycle() {
        StatusCode mockNewStatusCode = new StatusCode(0, "TESTSTAT", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        int newStatusCodeId = given()
                .contentType(ContentType.JSON)
                .body(mockNewStatusCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        StatusCode mockUpdateStatusCode = new StatusCode(newStatusCodeId, "TESTSTATS", mockNewStatusCode.getCreationDate(), mockNewStatusCode.getCreatedByUser(),OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateStatusCode)
                .when()
                .put("/" + newStatusCodeId)
                .then()
                .statusCode(200);

        given().when().delete("/" + newStatusCodeId).then().statusCode(200);
    }
}
