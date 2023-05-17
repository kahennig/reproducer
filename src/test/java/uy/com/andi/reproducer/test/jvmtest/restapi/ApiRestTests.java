package uy.com.andi.reproducer.test.jvmtest.restapi;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiRestTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRestTests.class);

    @Test
    @Order(0)
    void testHealthCheck() {
        LOGGER.info("\n--------------------------------------------------------------------------------------");
        LOGGER.info("\n--- testHealthCheck()");

        ValidatableResponse body = given().when().get("/q/health/live").then().statusCode(200).body("status", is("UP"));
        LOGGER.info("body Class: {}.", body.getClass());

        LOGGER.info("\n--------------------------------------------------------------------------------------");
    }

    @Test
    @Order(10)
    void test() {
        LOGGER.info("\n--------------------------------------------------------------------------------------");
        LOGGER.info("\n--- test()");

        String message =
                given().with().body("Hello, world!").when().post("/tests/endpoints").then()
                        .statusCode(Response.Status.ACCEPTED.getStatusCode()).extract().response().getBody().as(String.class);

        assertEquals("Hello, world!", message);

        LOGGER.info("\n--------------------------------------------------------------------------------------");
    }


}
