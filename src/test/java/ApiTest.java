import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import glue.User;
import glue.UserComporator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiTest {

    private Gson gson = new Gson();
    private List<User> users = new LinkedList<>();
    private static final String basePath = "http://localhost:8888";

    @Test(description = "GET")
    public void getNewTestUserTest() throws JSONException {

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/other/getTestUser");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "GET")
    public void getUsersRequestTest() throws JSONException {

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "GET")
    public void getUserInfoTest() {

        getUsers();
        String id = users.get(1).getId();

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id);
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "GET")
    public void getLimitsTest() throws JSONException {

        Response response = given().when()
                .get(basePath + "/limits");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "GET")
    public void getOperationsTest() throws JSONException {

        getUsers();
        String id = users.get(0).getId();

        RequestSpecification request = given()
                .header("Content-Type", "application/json");
        Response response = request.get(basePath + "/users/" + id + "/operations");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "POST")
    public void postOperationTest() throws JSONException {

        getUsers();
        String id = users.get(0).getId();

        JSONObject requestBody = new JSONObject();
        requestBody.put("value", 200);
        requestBody.put("resource", "ACCOUNT");

        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .post(basePath + "/users/" + id + "/operations");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "PUT")
    public void putConfirmOperationTest() {

        getUsers();
        String id = users.get(0).getId();

        JSONObject requestBody = new JSONObject();
        requestBody.put("value", 200);
        requestBody.put("resource", "ACCOUNT");

        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .put(basePath + "/users/" + id + "/operations/confirm");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "GET")
    public void getUserRemainTest() throws JSONException {

        getUsers();
        String id = users.get(0).getId();

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id + "/remain");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    @Test(description = "GET")
    public void getCurrentValuesTest() throws JSONException {

        getUsers();
        String id = users.get(0).getId();

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id + "/currentValues");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    public void getUsers() throws JSONException {

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users");

        users = gson.fromJson(response.getBody().asString(), new TypeToken<ArrayList<User>>() {
        }.getType());
    }

}
