import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.ManufacturerMaster;
import org.coresync.app.repository.inventory.ManufacturerMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ManufacturerMasterResourceTest {

    @Inject
    ManufacturerMasterRepository manufacturerRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/manufacturer";
    }

    @Test
    void testGetManufacturers() {
        List<ManufacturerMaster> manufacturers = manufacturerRepository.getManufacturers();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(manufacturers.size()));
    }

    @Test
    void testGetManufacturerDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("manufacturerCode", is("K001"))
                .body("manufacturerName", is("Kraft"));
    }

    @Test
    void testValidateManufacturerExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Manufacturer exists"));
    }

    @Test
    void testValidateManufacturerDuplicate() {
        given()
                .when()
                .get("/validate/cd/K001")
                .then()
                .statusCode(409)
                .body("message", is("Manufacturer already exists."));
    }

    @Test
    void testManufacturerLifeCycle() {
        ManufacturerMaster mockManufacturer = new ManufacturerMaster();
        mockManufacturer.setManufacturerCode("MFG002");
        mockManufacturer.setManufacturerName("Test Manufacturer 2");
        mockManufacturer.setAddress("456 New St");
        mockManufacturer.setContactNumber("987-654-3210");
        mockManufacturer.setEmail("test2@manufacturer.com");
        mockManufacturer.setCreationDate(OffsetDateTime.now());
        mockManufacturer.setCreatedByUser("QKDEV");
        mockManufacturer.setLastUpdateDate(OffsetDateTime.now());
        mockManufacturer.setLastUpdatedByUser("QKDEV");

        // Create a new Manufacturer
        int newManufacturerId = given()
                .contentType(ContentType.JSON)
                .body(mockManufacturer)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the Manufacturer
        mockManufacturer.setId(newManufacturerId);
        mockManufacturer.setManufacturerName("Updated Manufacturer");

        given()
                .contentType(ContentType.JSON)
                .body(mockManufacturer)
                .when()
                .put("/" + newManufacturerId)
                .then()
                .statusCode(200);

        // Delete the Manufacturer
        given()
                .when()
                .delete("/" + newManufacturerId)
                .then()
                .statusCode(200);
    }
}
