import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.TaxJurisdictionTypeCode;
import org.coresync.app.repository.inventory.TaxJurisdictionTypeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TaxJurisdictionTypeCodeResourceTest {
    @Inject
    private TaxJurisdictionTypeCodeRepository taxJurisdictionTypeCodeRepository;
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/txjrs-typ-cd";
    }

    @Test
    void testGetAllTaxJurisdictionTypeCodes() {
        List<TaxJurisdictionTypeCode> typeCodes = taxJurisdictionTypeCodeRepository.getAllTaxJurisdictionTypeCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(typeCodes.size()));
    }

    @Test
    void testGetTaxJurisdictionTypeCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("jurisdictionTypeCode", is("CIGARETTES"));
    }

    @Test
    void testValidateTaxJurisdictionTypeCodeExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Tax Jurisdiction Type Code exists"));
    }

    @Test
    void testValidateTaxJurisdictionTypeCodeDuplicate() {
        given()
                .when()
                .get("/validate/code/CIGARETTES")
                .then()
                .statusCode(409)
                .body("message", is("Tax Jurisdiction Type Code already exists."));
    }

    @Test
    void testTaxJurisdictionTypeCodeLifeCycle() {
        TaxJurisdictionTypeCode newTypeCode = new TaxJurisdictionTypeCode(0, "CMS", "CMS Jurisdiction Type", OffsetDateTime.parse("2024-12-24T05:11:20.070Z"), "QKDEV",OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKDEV");

        int newTypeCodeId = given()
                .contentType(ContentType.JSON)
                .body(newTypeCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        TaxJurisdictionTypeCode updateTypeCode = new TaxJurisdictionTypeCode(newTypeCodeId, "CMS", "CMS Jurisdiction Type", newTypeCode.getCreationDate(), newTypeCode.getCreatedByUser(), OffsetDateTime.parse("2024-12-24T05:11:20.070Z"),"QKTUDEV");

        given()
                .contentType(ContentType.JSON)
                .body(updateTypeCode)
                .when()
                .put("/" + newTypeCodeId)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete("/" + newTypeCodeId)
                .then()
                .statusCode(200);
    }
}
