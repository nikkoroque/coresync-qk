import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.HazardClass;
import org.coresync.app.repository.inventory.HazardClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HazardClassResourceTest {

    @Inject
    HazardClassRepository hazardClassRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/hazard-class";
    }

    @Test
    void testGetHazardClasses() {
        List<HazardClass> hazardClasses = hazardClassRepository.getHazardClasses();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(hazardClasses.size()));
    }

    @Test
    void testGetHazardClassDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("classCode", is("Class 1"))
                .body("description", is("Explosives"));
    }

    @Test
    void testValidateHazardClassExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Hazard Class exists"));
    }

    @Test
    void testValidateHazardClassDuplicate() {
        given()
                .when()
                .get("/validate/cd/Class 1")
                .then()
                .statusCode(409)
                .body("message", is("Hazard Class already exists."));
    }

    @Test
    void testHazardClassLifeCycle() {
        HazardClass mockHazardClass = new HazardClass(0, "HAZ3", "Test Hazard", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV");

        // Create a new Hazard Class
        int newHazardClassId = given()
                .contentType(ContentType.JSON)
                .body(mockHazardClass)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the Hazard Class
        HazardClass mockUpdateHazardClass = new HazardClass(newHazardClassId, "HAZ3", "Updated Hazard", mockHazardClass.getCreationDate(), mockHazardClass.getCreatedByUser(), OffsetDateTime.now(), "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateHazardClass)
                .when()
                .put("/" + newHazardClassId)
                .then()
                .statusCode(200);

        // Delete the Hazard Class
        given()
                .when()
                .delete("/" + newHazardClassId)
                .then()
                .statusCode(200);
    }
}
