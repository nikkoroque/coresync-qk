import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.repository.BusinessUnitRepository;
import org.coresync.app.resource.BusinessUnitResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

public class BusinessUnitResourceTest {

    @InjectMocks
    private BusinessUnitResource businessUnitResource;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RestAssured.baseURI = "http://localhost:8080/api/bu";
    }

    @Test
    void testGetAllBusinessUnits() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("buId", containsInAnyOrder(345, 452));
    }

    @Test
    void testGetPaginatedBusinessUnits() {
        given()
                .queryParam("sortBy", "buid")
                .queryParam("sortOrder", "asc")
                .when()
                .get("/page/1")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("buId", containsInAnyOrder(345, 452));
    }

    @Test
    void testFilterBusinessUnits() {
        given()
                .queryParam("buDesc", "Tejon")
                .when()
                .get("/filter")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("buId", containsInAnyOrder(345));
    }

    @Test
    void testGetBusinessUnitDetail() {
        given()
                .when()
                .get("/345")
                .then()
                .statusCode(200)
                .body("buId", is(345))
                .body("buDesc", is("Tejon"));
    }

    @Test
    void testBusinessUnitLifecycle() {
        // Step 1: Add Business Unit
        BusinessUnit newUnit = new BusinessUnit(
                999,
                "Unit 999",
                "123454352",
                "Address 1",
                "Address 2",
                "City 999",
                "State 999",
                "99999-55555",
                "USA"
        );

        given()
                .contentType(ContentType.JSON)
                .body(newUnit)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .body("buId", is(newUnit.getBuId()))
                .body("buDesc", is(newUnit.getBuDesc()))
                .body("buGln", is(newUnit.getBuGln()))
                .body("buAdd1", is(newUnit.getBuAdd1()))
                .body("buCity", is(newUnit.getBuCity()))
                .body("buState", is(newUnit.getBuState()))
                .body("buZip", is(newUnit.getBuZip()))
                .body("buCountry", is(newUnit.getBuCountry()));

        // Step 2: Update Business Unit
        BusinessUnit updatedUnit = new BusinessUnit(
                999, // Same ID
                "Unit 888",
                "123454352",
                "Address 1",
                "Address 2",
                "City 888",
                "State 888",
                "88888-55555",
                "USA"
        );

        given()
                .contentType(ContentType.JSON)
                .body(updatedUnit)
                .when()
                .put("/999")
                .then()
                .statusCode(200)
                .body("buDesc", is("Unit 888"));

        // Step 3: Validate Business Unit Exists
        given()
                .when()
                .get("/exists/999")
                .then()
                .statusCode(302)
                .body("message", is("Business Unit exists"));

        // Step 4: Delete Business Unit
        given()
                .when()
                .delete("/999")
                .then()
                .statusCode(200);

        // Step 5: Ensure Business Unit No Longer Exists
        given()
                .when()
                .get("/exists/999")
                .then()
                .statusCode(404)
                .body("message", is("Business Unit not found"));
    }


}
