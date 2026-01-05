package API;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class firstAPI {
    public static void main(String[] args) {
        RestAssured.baseURI = "";
        given().relaxedHTTPSValidation();
    }
}
