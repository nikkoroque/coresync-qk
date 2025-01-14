import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.ItemMaster;
import org.coresync.app.model.ItemMasterDTO;
import org.coresync.app.repository.inventory.ItemMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ItemMasterResourceTest {

    @Inject
    ItemMasterRepository itemMasterRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/item-master";
    }

    @Test
    void testGetItems() {
        List<ItemMasterDTO> items = itemMasterRepository.getItems();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(items.size()));
    }

    @Test
    void testGetItemMetaData() {
        List<ItemMasterDTO> metadata = itemMasterRepository.getItemDTO();

        given()
                .when()
                .get("/meta-data")
                .then()
                .statusCode(200)
                .body("size()", is(metadata.size()));
    }

    @Test
    void testItemMasterLifecycle() {
        // Step 1: Create a new item
        ItemMaster newItem = new ItemMaster(
                0, "X02", "K001", "111", "A", 2, 2, 4, 1, "N", "EA", 2, 1234567890,
                "G123456789", 1234567890, "Test1", "http://asdqowe.coasd.image.link",
                "Y", 9.99, "Eaches", 10, 1, 1.5, 1.5, 1.0, "N", 0, "Y", 5, 5, 1, 20, 20,
                20, 20, 20, 20, OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV"
        );

        int createdItemId = given()
                .contentType(ContentType.JSON)
                .body(newItem)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("itemId");

        // Step 2: Update the created item
        ItemMaster updatedItem = new ItemMaster(
                createdItemId, "X02", "K001", "111", "A", 2, 2, 4, 1, "N", "EA", 2, 1234567890,
                "G123456789", 1234567890, "Test1", "http://asdqowe.coasd.image.link",
                "Y", 9.99, "Eaches", 10, 1, 1.5, 1.5, 1.0, "N", 0, "Y", 5, 5, 1, 20, 20,
                20, 20, 20, 20, OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV"
        );

        given()
                .contentType(ContentType.JSON)
                .body(updatedItem)
                .when()
                .put("/" + createdItemId)
                .then()
                .statusCode(200)
                .body("itemDescr", is("Test1"));

        // Step 3: Delete the updated item
        given()
                .when()
                .delete("/" + createdItemId)
                .then()
                .statusCode(200);
    }
}
