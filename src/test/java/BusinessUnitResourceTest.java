import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.BusinessUnit;
import org.coresync.app.repository.inventory.BusinessUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class BusinessUnitResourceTest {

    @Inject
    BusinessUnitRepository businessUnitRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:8080/api/business-unit";
    }

    @Test
    void testGetPaginatedBusinessUnits() {
        // Arrange: Fetch paginated business units
        List<BusinessUnit> businessUnits = businessUnitRepository.getPaginatedBusinessUnit(1, "buId", "asc").collect(Collectors.toList());

        given()
                .queryParam("sortBy", "buId")
                .queryParam("sortOrder", "asc")
                .when()
                .get("/page/1")
                .then()
                .statusCode(200)
                .body("size()", is(businessUnits.size()))
                .body("[0].buId", is(businessUnits.get(0).getBuId()))
                .body("[0].buDesc", is(businessUnits.get(0).getBuDesc()));
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
                .get("/validate/999")
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
                .get("/validate/999")
                .then()
                .statusCode(404)
                .body("message", is("Business Unit not found"));
    }
}
