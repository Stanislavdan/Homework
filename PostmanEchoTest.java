import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostmanEchoTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    @DisplayName("GET Request")
    public void testGetMethod() {
        given()
                .log().all()
                .queryParam("foo1", "bar1")
                .queryParam("foo2", "bar2")
                .when()
                .get("/get")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.foo1", equalTo("bar1"))
                .body("args.foo2", equalTo("bar2"))
                .body("headers.host", equalTo("postman-echo.com"))
                .body("url", containsString("/get?foo1=bar1&foo2=bar2"));
    }

    @Test
    @DisplayName("POST Raw Text")
    public void testPostRawText() {
        given()
                .log().all()
                .contentType("text/plain; charset=UTF-8")
                .when()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("url", equalTo("https://postman-echo.com/post"))
                .body("headers.host", equalTo("postman-echo.com"))
                .body("json", nullValue())
                .body("args", anEmptyMap())
                .body("files", anEmptyMap())
                .body("form", anEmptyMap());
    }

    @Test
    @DisplayName("POST Form Data")
    public void testPostFormData() {
        given()
                .log().all()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("url", equalTo("https://postman-echo.com/post"))
                .body("form.foo1", equalTo("bar1"))
                .body("form.foo2", equalTo("bar2"))
                .body("args", anEmptyMap())
                .body("data", equalTo(""))
                .body("files", anEmptyMap())
                .body("json.foo1", equalTo("bar1"))
                .body("json.foo2", equalTo("bar2"))
                .body("headers.host", equalTo("postman-echo.com"));
    }

    @Test
    @DisplayName("POST JSON")
    public void testPostJson() {
        Map<String, String> requestBody = Map.of(
                "name", "John Doe",
                "role", "QA Automation"
        );

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("json.name", equalTo("John Doe"))
                .body("json.role", equalTo("QA Automation"))
                .body("data.name", equalTo("John Doe"))
                .body("data.role", equalTo("QA Automation"))
                .body("args", anEmptyMap())
                .body("files", anEmptyMap())
                .body("form", anEmptyMap())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("url", containsString("/post"));
    }

    @Test
    @DisplayName("PUT Request")
    public void testPutMethod() {
        Map<String, String> requestBody = Map.of(
                "status", "updated",
                "id", "123"
        );

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/put")
                .then()
                .log().all()
                .statusCode(200)
                .body("json.status", equalTo("updated"))
                .body("json.id", equalTo("123"))
                .body("data.status", equalTo("updated"))
                .body("data.id", equalTo("123"))
                .body("args", anEmptyMap())
                .body("files", anEmptyMap())
                .body("form", anEmptyMap())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("url", containsString("/put"));
    }

    @Test
    @DisplayName("PATCH Request")
    public void testPatchMethod() {
        Map<String, String> requestBody = Map.of(
                "fieldToPatch", "new_value"
        );

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("/patch")
                .then()
                .log().all()
                .statusCode(200)
                .body("json.fieldToPatch", equalTo("new_value"))
                .body("data.fieldToPatch", equalTo("new_value"))
                .body("args", anEmptyMap())
                .body("files", anEmptyMap())
                .body("form", anEmptyMap())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("url", containsString("/patch"));
    }

    @Test
    @DisplayName("DELETE Request")
    public void testDeleteMethod() {
        Map<String, String> requestBody = Map.of(
                "idToDelete", "999"
        );

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .delete("/delete")
                .then()
                .log().all()
                .statusCode(200)
                .body("json.idToDelete", equalTo("999"))
                .body("data.idToDelete", equalTo("999"))
                .body("args", anEmptyMap())
                .body("files", anEmptyMap())
                .body("form", anEmptyMap())
                .body("headers.host", equalTo("postman-echo.com"))
                .body("url", containsString("/delete"));
    }
}