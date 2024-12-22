import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.coresync.app.model.HazardClassification;
import org.coresync.app.repository.inventory.HazardClassificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class HazardClassificationResourceTest {

    @Inject
    HazardClassificationRepository hazardClassificationRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:8080/api/hazard-classification";
    }

    @Test
    void testGetPaginatedHazardClassifications() {
        // Arrange: Fetch paginated hazard classifications
        List<HazardClassification> hazardClasses = hazardClassificationRepository
                .getPaginatedHazardClassification(1, "hzrdClsCd", "asc")
                .collect(Collectors.toList()); // Collect the stream into a list

        // Act & Assert: Use RestAssured to validate the API response
        given()
                .queryParam("sortBy", "hzrdClsCd")
                .queryParam("sortOrder", "asc")
                .when()
                .get("/page/1")
                .then()
                .statusCode(200) // Ensure HTTP status is 200 OK
                .body("size()", is(hazardClasses.size())) // Validate response size matches repository result
                .body("[0].hzrdClsCd", is(hazardClasses.get(0).getHzrdClsCd())) // Validate the first record
                .body("[0].hzrdClsDesc", is(hazardClasses.get(0).getHzrdClsDesc())); // Validate other fields as needed
    }

    @Test
    void testFilterHazardClassifications() {
        given()
                .queryParam("hzrdClsDesc", "Alcohol")
                .when()
                .get("/filter")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("hzrdClsCd", containsInAnyOrder("4"));
    }

    @Test
    void testGetHazardClassificationDetail() {
        given()
                .when()
                .get("/4")
                .then()
                .statusCode(200)
                .body("hzrdClsCd", is("4"))
                .body("hzrdClsCd", is("4"));
    }

//    @Test
//    void testHazardClassificationLifecycle() {
//        // Current timestamp
//        Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
//
//        // Create HazardClassification instance
//        HazardClassification newHazardClassification = new HazardClassification(
//                0,
//                "99",
//                "Bleach",
//                currentTimestamp,
//                "QKDEVATEST",
//                currentTimestamp,
//                "QKDEVATEST"
//        );
//
//        // Serialize and send the request
//        given()
//                .contentType(ContentType.JSON)
//                .body(newHazardClassification)
//                .when()
//                .post("/")
//                .then()
//                .statusCode(201)
//                .body("hzrdClsCd", is(newHazardClassification.getHzrdClsCd()))
//                .body("hzrdClsDesc", is(newHazardClassification.getHzrdClsDesc()))
//                .body("createdByUser", is(newHazardClassification.getCreatedByUser()))
//                .body("lastUpdatedByUser", is(newHazardClassification.getLastUpdatedByUser()));
//
//        // Step 2 : Update Business Unit
//        HazardClassification uodateHazardClassification = new HazardClassification(0, "99", "Bleach Update", currentTimestamp,
//                "QKDEVATEST", currentTimestamp, "QKDEV");
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(uodateHazardClassification)
//                .when()
//                .put("/99")
//                .then()
//                .statusCode(200)
//                .body("hzrdClsDesc", is("Bleach Update"));
//    }

//    @Test
//    public void testDeleteHazardClassification() {
//        // Setup test data
//        HazardClassification hc = new HazardClassification();
//        hc.setHzrdClsCd("99");
//        entityManager.persist(hc);
//
//        // Execute DELETE request
//        given()
//                .when()
//                .delete("/99")
//                .then()
//                .statusCode(200)
//                .body("message", is("Hazard Classification deleted"))
//                .body("Class Code", is("99"));
//    }

}
