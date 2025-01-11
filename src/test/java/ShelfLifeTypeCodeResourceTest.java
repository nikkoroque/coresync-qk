import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.ShelfLifeTypeCode;
import org.coresync.app.model.ShelfLifeTypeCodeDTO;
import org.coresync.app.repository.inventory.ShelfLifeTypeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ShelfLifeTypeCodeResourceTest {
    @Inject
    ShelfLifeTypeCodeRepository shelfLifeTypeCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/shelf-life-type-code";
    }

    @Test
    void testGetShelfLifeTypeCodes() {
        List<ShelfLifeTypeCode> shelfLifeTypeCodes =  shelfLifeTypeCodeRepository.getShelfLifeTypeCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(shelfLifeTypeCodes.size()));
    }

    @Test
    void testGetShelfLifeTypeCodeNames() {
        List<ShelfLifeTypeCodeDTO> shlfLfTypCdNames = shelfLifeTypeCodeRepository.getShelfLifeTypeCodeDTO();

        given()
                .when()
                .get("/meta-data")
                .then()
                .statusCode(200)
                .body("size()", is(shlfLfTypCdNames.size()));
    }

    @Test
    void testGetShelfLifeTypeCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("code", is("A"));
    }

    @Test
    void testValidateShelfLifeTypeCodeExists() {
        given()
                .when()
                .get("/validate/sltCdId/1")
                .then()
                .statusCode(409)
                .body("message", is("Shelf Life Type Code exists"));
    }

    @Test
    void testValidateShelfLifeTypeCodeDuplicate() {
        given()
                .when()
                .get("/validate/sltCd/A")
                .then()
                .statusCode(409)
                .body("message", is("Shelf Life Type Code already exists"));
    }

    @Test
    void testShelfLifeTypeCodeLifeCycle() {
        ShelfLifeTypeCode mockNewShelfLifeTypeCode = new ShelfLifeTypeCode(0, "T0", "Test", OffsetDateTime.now(), "QKDEV"
                ,OffsetDateTime.now(), "QKDEV");

        int newShelfLifeTypeCodeId = given()
                .contentType(ContentType.JSON)
                .body(mockNewShelfLifeTypeCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        ShelfLifeTypeCode mockUpdateShelfLifeTypeCode = new ShelfLifeTypeCode(newShelfLifeTypeCodeId, "T01", "Test",
                mockNewShelfLifeTypeCode.getCreationDate(), mockNewShelfLifeTypeCode.getCreatedByUser(),
                OffsetDateTime.now(), "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateShelfLifeTypeCode)
                .when()
                .put("/" + newShelfLifeTypeCodeId)
                .then()
                .statusCode(200);

        given().when().delete("/" + newShelfLifeTypeCodeId).then().statusCode(200);

    }
}
