import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.InventoryClass;
import org.coresync.app.repository.inventory.InventoryClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class InventoryClassResourceTest {

    @Inject
    InventoryClassRepository inventoryClassRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/inventory-class";
    }

    @Test
    void testGetInventoryClasses() {
        List<InventoryClass> inventoryClasses = inventoryClassRepository.getInventoryClasses();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(inventoryClasses.size()));
    }

    @Test
    void testGetInventoryClassDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("description", is("Precut-TBones"));
    }

    @Test
    void testValidateInventoryClassExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Inventory Class exists"));
    }

    @Test
    void testValidateInventoryClassDuplicate() {
        given()
                .when()
                .get("/validate/cd/Precut-TBones")
                .then()
                .statusCode(409)
                .body("message", is("Inventory Class already exists."));
    }

    @Test
    void testInventoryClassLifeCycle() {
        InventoryClass mockInventoryClass = new InventoryClass();
        mockInventoryClass.setInventoryCodeId(2);
        mockInventoryClass.setDescription("Beek Steak");
        mockInventoryClass.setCreationDate(OffsetDateTime.now());
        mockInventoryClass.setCreatedByUser("QKDEV");
        mockInventoryClass.setLastUpdateDate(OffsetDateTime.now());
        mockInventoryClass.setLastUpdatedByUser("QKDEV");

        // Create a new Inventory Class
        int newInventoryClassId = given()
                .contentType(ContentType.JSON)
                .body(mockInventoryClass)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the Inventory Class
        InventoryClass mockUpdateInventoryClass = new InventoryClass();
        mockUpdateInventoryClass.setId(newInventoryClassId);
        mockUpdateInventoryClass.setInventoryCodeId(2);
        mockUpdateInventoryClass.setDescription("Updated Class");
        mockUpdateInventoryClass.setCreationDate(mockInventoryClass.getCreationDate());
        mockUpdateInventoryClass.setCreatedByUser(mockInventoryClass.getCreatedByUser());
        mockUpdateInventoryClass.setLastUpdateDate(OffsetDateTime.now());
        mockUpdateInventoryClass.setLastUpdatedByUser("QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateInventoryClass)
                .when()
                .put("/" + newInventoryClassId)
                .then()
                .statusCode(200);

        // Delete the Inventory Class
        given()
                .when()
                .delete("/" + newInventoryClassId)
                .then()
                .statusCode(200);
    }
}
