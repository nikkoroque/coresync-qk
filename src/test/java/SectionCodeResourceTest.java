import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.SectionCode;
import org.coresync.app.repository.inventory.SectionCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SectionCodeResourceTest {
    @Inject
    SectionCodeRepository sectionCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/section-code";
    }

    @Test
    void testGetSectionCodes() {
        List<SectionCode> sectionCodes = sectionCodeRepository.getSectionCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(sectionCodes.size()));
    }

    @Test
    void testGetSectionCodeDetail() {
        given()
                .when()
                .get("/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("code", is("NA"));
    }

    @Test
    void testValidateSectionCodeExists() {
        given()
                .when()
                .get("/validate/sectionId/1")
                .then()
                .statusCode(409)
                .body("message", is("Section Code exists"));
    }

    @Test
    void validateStatusCodeDuplicate() {
        given()
                .when()
                .get("/validate/section/NA")
                .then()
                .statusCode(409)
                .body("message", is("Section Code already exists"));
    }

    @Test
    void testSectionCodeLifeCycle() {
        SectionCode mockSectionCode = new SectionCode(0, "T98", "TEST98", OffsetDateTime.now(), "QKDEV",
                OffsetDateTime.now(), "QKDEV");

        int newSectionCdId = given()
                .contentType(ContentType.JSON)
                .body(mockSectionCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        SectionCode mockUpdateSectionCode = new SectionCode(newSectionCdId, "T99", "TEST99", mockSectionCode.getCreationDate(),
                mockSectionCode.getCreatedByUser(),
                OffsetDateTime.now(), "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateSectionCode)
                .when()
                .put("/" + newSectionCdId)
                .then()
                .statusCode(200);

        given().when().delete("/" + newSectionCdId).then().statusCode(200);
    }
}
