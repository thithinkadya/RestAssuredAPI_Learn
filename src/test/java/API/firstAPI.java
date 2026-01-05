package API;

import File.payLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class firstAPI {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().relaxedHTTPSValidation().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payLoad.addPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response); //to parse the Json
        String placeid = js.getString("place_id");
        System.out.println(placeid);

        String newaddress = "33 Summer walk, USA";
        //Update address
        given().relaxedHTTPSValidation().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "    \"place_id\": \""+placeid+"\",\n" +
                        "    \"address\": \""+newaddress+"\",\n" +
                        "    \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));

        //get address details
        given().relaxedHTTPSValidation().queryParam("key","qaclick123").queryParam("place_id",placeid)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("address",equalTo(newaddress));
    }
}
