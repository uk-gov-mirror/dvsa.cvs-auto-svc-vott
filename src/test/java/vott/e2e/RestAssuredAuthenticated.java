package vott.e2e;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredAuthenticated {

    public static RequestSpecification givenAuth(String bearerToken) {
        return given()
            .header("Authorization", "Bearer " + bearerToken);
    }

    public static RequestSpecification givenAuth(String bearerToken, String apiKey) {
        return given()
            .header("Authorization", "Bearer " + bearerToken)
            .header("X-Api-Key", apiKey);
    }
}
