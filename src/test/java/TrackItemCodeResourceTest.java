import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.TrackItemCode;
import org.coresync.app.repository.inventory.TrackItemCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@QuarkusTest
public class TrackItemCodeResourceTest {
    @Inject
    private TrackItemCodeRepository trackItemCodeRepository;
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/trk-itm-cd";
    }

    @Test
    void testGetAllTrackItemCodes() {
        List<TrackItemCode> itemCodes = trackItemCodeRepository.getAllTrackItemCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(itemCodes.size()));
    }

    @Test
    void testGetTrackItemCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("code", is("N"));
    }

    @Test
    void testValidateTrackitemCodeExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Track Item Code exists"));
    }

    @Test
    void testValidateTrackItemCodeDuplicate() {
        given()
                .when()
                .get("/validate/code/N")
                .then()
                .statusCode(409)
                .body("message", is("Track Item Code already exists."));
    }

    @Test
    void testTrackItemCodeLifeCycle() {
        //Add Track Item Code
        TrackItemCode mockNewItemCode = new TrackItemCode(0, "L", "Tracked by License Plate", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        int newItemCodeId = given()
                .contentType(ContentType.JSON)
                .body(mockNewItemCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update Track Item Code
        TrackItemCode mockUpdateItemCode = new TrackItemCode(newItemCodeId, "L", "Tracked by License Plates", mockNewItemCode.getCreationDate(), mockNewItemCode.getCreatedByUser(), OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKTUDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateItemCode)
                .when()
                .put("/" + newItemCodeId)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete("/" + newItemCodeId)
                .then()
                .statusCode(200);
    }
}
