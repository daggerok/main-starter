package com.github.daggerok;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RestResourceTest {

  @Test
  void testHelloRoot() {
    given()
        .when().get("/api/v1/hello")
        .then().statusCode(200)
        .body(is("Hola"));
  }

  @Test
  void testHelloWhitespace() {
    given()
        .when().get("/api/v1/hello/{name}", singletonMap("name", " "))
        .then().statusCode(200)
        .body(is("Hola, anonymous!"));
  }

  @Test
  void testHelloMax() {
    given()
        .when().get("/api/v1/hello/Max")
        .then().statusCode(200)
        .body(is("Hola, Max!"));
  }
}
