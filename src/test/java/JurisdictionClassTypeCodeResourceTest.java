import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.JurisdictionClassTypeCode;
import org.coresync.app.model.JurisdictionClassTypeCodeDTO;
import org.coresync.app.repository.inventory.JurisdictionClassTypeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class JurisdictionClassTypeCodeResourceTest {
    @Inject
    JurisdictionClassTypeCodeRepository jurisdictionClassTypeCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/jurisdiction-class-type-code";
    }

    @Test
    void testGetJurisdictionClassTypeCodes() {
        List<JurisdictionClassTypeCode> jurisdictionClassTypeCodes = jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(jurisdictionClassTypeCodes.size()));
    }

    @Test
    void testGetJurisdictionClassTypeCodesMetadata() {
        List<JurisdictionClassTypeCodeDTO> jurisdictionClassTypeCodeDTOList = jurisdictionClassTypeCodeRepository.getJurisdictionClassTypeCodeDTO();

        given()
                .when()
                .get("/meta-data")
                .then()
                .statusCode(200)
                .body("size()", is(jurisdictionClassTypeCodeDTOList.size()));
    }

    @Test
    void testGetJurisdictionClassTypeCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("classTypeCode", is("CITY"));
    }

    @Test
    void testValidateJurisdictionClassTypeCodeExists() {
        given()
                .when()
                .get("/validate/id/1")
                .then()
                .statusCode(409)
                .body("message", is("Jurisdiction Class Type Code exists"));
    }

    @Test
    void testValidateJurisdictionClassTypeCodeDuplicate() {
        given()
                .when()
                .get("/validate/cd/CITY")
                .then()
                .statusCode(409)
                .body("message", is("Jurisdiction Class Type Code already exists"));
    }

    @Test
    void testJurisdictionClassTypeCodeLifeCycle() {
        JurisdictionClassTypeCode mockNewJurisdictionClassTypeCode = new JurisdictionClassTypeCode(0, "REGION", "Region Jurisdiction", OffsetDateTime.now(), "QKDEV", OffsetDateTime.now(), "QKDEV");

        int newJrsClsTypCdId = given()
                .contentType(ContentType.JSON)
                .body(mockNewJurisdictionClassTypeCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        JurisdictionClassTypeCode mockUpdateJurisdictionClassTypeCode = new JurisdictionClassTypeCode(newJrsClsTypCdId, "REGION", "Region Jurisdiction", mockNewJurisdictionClassTypeCode.getCreationDate(), mockNewJurisdictionClassTypeCode.getCreatedByUser(), OffsetDateTime.now(), "QKDEV");

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(mockUpdateJurisdictionClassTypeCode)
                .when()
                .put("/" + newJrsClsTypCdId)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete("/" + newJrsClsTypCdId)
                .then()
                .statusCode(200);
    }
}
