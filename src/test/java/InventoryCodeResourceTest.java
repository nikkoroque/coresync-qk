import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.InventoryCode;
import org.coresync.app.repository.inventory.InventoryCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class InventoryCodeResourceTest {

    @Inject
    InventoryCodeRepository inventoryCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/inventory-code";
    }

    @Test
    void testGetInventoryCodes() {
        List<InventoryCode> inventoryCodes = inventoryCodeRepository.getInventoryCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(inventoryCodes.size()));
    }

    @Test
    void testGetInventoryCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("description", is("Cereal"));
    }

    @Test
    void testValidateInventoryCodeExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Inventory Code exists"));
    }

    @Test
    void testValidateInventoryCodeDuplicate() {
        given()
                .when()
                .get("/validate/cd/Beef")
                .then()
                .statusCode(409)
                .body("message", is("Inventory Code already exists."));
    }

    @Test
    void testInventoryCodeLifeCycle() {
        InventoryCode mockInventoryCode = new InventoryCode();
        mockInventoryCode.setDescription("Test");
        mockInventoryCode.setCreationDate(OffsetDateTime.now());
        mockInventoryCode.setCreatedByUser("QKDEV");
        mockInventoryCode.setLastUpdateDate(OffsetDateTime.now());
        mockInventoryCode.setLastUpdatedByUser("QKDEV");

        // Create a new Inventory Code
        int newInventoryCodeId = given()
                .contentType(ContentType.JSON)
                .body(mockInventoryCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the Inventory Code
        InventoryCode mockUpdateInventoryCode = new InventoryCode();
        mockUpdateInventoryCode.setId(newInventoryCodeId);
        mockUpdateInventoryCode.setDescription("Updated");
        mockUpdateInventoryCode.setCreationDate(mockInventoryCode.getCreationDate());
        mockUpdateInventoryCode.setCreatedByUser(mockInventoryCode.getCreatedByUser());
        mockUpdateInventoryCode.setLastUpdateDate(OffsetDateTime.now());
        mockUpdateInventoryCode.setLastUpdatedByUser("QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateInventoryCode)
                .when()
                .put("/" + newInventoryCodeId)
                .then()
                .statusCode(200);

        // Delete the Inventory Code
        given()
                .when()
                .delete("/" + newInventoryCodeId)
                .then()
                .statusCode(200);
    }
}
