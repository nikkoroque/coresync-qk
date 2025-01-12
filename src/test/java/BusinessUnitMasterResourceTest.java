import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.BusinessUnitMaster;
import org.coresync.app.repository.inventory.BusinessUnitMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class BusinessUnitMasterResourceTest {

    @Inject
    BusinessUnitMasterRepository businessUnitRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/business-unit";
    }

    @Test
    void testGetBusinessUnits() {
        List<BusinessUnitMaster> units = businessUnitRepository.getBusinessUnits();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(units.size()));
    }

    @Test
    void testGetBusinessUnitDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("businessUnitCode", is("345"))
                .body("businessUnitName", is("Tejon"));
    }

    @Test
    void testValidateBusinessUnitExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Business Unit exists"));
    }

    @Test
    void testValidateBusinessUnitDuplicate() {
        given()
                .when()
                .get("/validate/cd/345")
                .then()
                .statusCode(409)
                .body("message", is("Business Unit already exists."));
    }

    @Test
    void testBusinessUnitLifeCycle() {
        BusinessUnitMaster mockBusinessUnit = new BusinessUnitMaster();
        mockBusinessUnit.setBusinessUnitCode("BU002");
        mockBusinessUnit.setBusinessUnitName("New Business Unit");
        mockBusinessUnit.setAddress("456 Business St");
        mockBusinessUnit.setContactNumber("987-654-3210");
        mockBusinessUnit.setEmail("new@business.com");
        mockBusinessUnit.setTaxIdentificationNumber("TIN67890");
        mockBusinessUnit.setJurisdictionId(200);
        mockBusinessUnit.setCreationDate(OffsetDateTime.now());
        mockBusinessUnit.setCreatedByUser("QKDEV");
        mockBusinessUnit.setLastUpdateDate(OffsetDateTime.now());
        mockBusinessUnit.setLastUpdatedByUser("QKDEV");

        // Create a new Business Unit
        int newBusinessUnitId = given()
                .contentType(ContentType.JSON)
                .body(mockBusinessUnit)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Update the Business Unit
        mockBusinessUnit.setId(newBusinessUnitId);
        mockBusinessUnit.setBusinessUnitName("Updated Business Unit");

        given()
                .contentType(ContentType.JSON)
                .body(mockBusinessUnit)
                .when()
                .put("/" + newBusinessUnitId)
                .then()
                .statusCode(200);

        // Delete the Business Unit
        given()
                .when()
                .delete("/" + newBusinessUnitId)
                .then()
                .statusCode(200);
    }
}
