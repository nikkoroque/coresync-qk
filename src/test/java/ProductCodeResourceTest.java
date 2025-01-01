import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.coresync.app.model.ProductCode;
import org.coresync.app.repository.inventory.ProductCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ProductCodeResourceTest {
    @Inject
    ProductCodeRepository productCodeRepository;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/product-code";
    }

    @Test
    void testGetProductCodes() {
        List<ProductCode> productCodes = productCodeRepository.getProductCodes();

        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body("size()", is(productCodes.size()));
    }

    @Test
    void testGetProductCodeDetail() {
        given()
                .when()
                .get("/2")
                .then()
                .statusCode(200)
                .body("id", is(2))
                .body("description", is("Refrigirated Products"));
    }

    @Test
    void testValidateProductCodeExists() {
        given()
                .when()
                .get("validate/productCdId/2")
                .then()
                .statusCode(409)
                .body("message", is("Product Code exists"));
    }

    @Test
    void validateProductCodeDuplicate() {
        given()
                .when()
                .get("/validate/productCd/Refrigirated Products")
                .then()
                .statusCode(409)
                .body("message", is("Product Code already exists"));
    }

    @Test
    void testProductCodeLifeCycle() {
        ProductCode mockProductCode = new ProductCode(0, "NA","Test",
                OffsetDateTime.now(), "QKDEV",OffsetDateTime.now(), "QKDEV");

        int newProductCodeId = given()
                .contentType(ContentType.JSON)
                .body(mockProductCode)
                .when()
                .post("/")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        ProductCode mockUpdateProductCode = new ProductCode(newProductCodeId, "NA","Test1",
                mockProductCode.getCreationDate(), mockProductCode.getCreatedByUser(),OffsetDateTime.now(),
                "QKDEV");

        given()
                .contentType(ContentType.JSON)
                .body(mockUpdateProductCode)
                .when()
                .put("/" + newProductCodeId)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete("/" + newProductCodeId)
                .then()
                .statusCode(200);
    }
}
