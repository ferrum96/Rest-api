package autotests.glue;

import autotests.Resource;
import autotests.User;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

import static autotests.User.getUsers;
import static autotests.User.users;
import static io.restassured.RestAssured.given;

public class Steps {

    private static final String basePath = "http://localhost:8888";
    Process process;

    @Before
    public void setUp() {
        getNewTestUser();
    }

    @Когда("пользователь снимает с {string} {int} рублей, лимит {int} рублей")
    public void пользовательСнимаетРублей(String resource, int value, int limit) {

        getUsers();
        String id = users.stream().sorted(Comparator.comparing(User::getName).reversed()).collect(Collectors.toList()).get(0).getId();
        String name = users.stream().sorted(Comparator.comparing(User::getName).reversed()).collect(Collectors.toList()).get(0).getName();

        getUserInfo(id);
        postOperationsResponse(id, resource, value);
        getOperationsResponse(id);
        getRemainResponse(id);
        getCurrentValues(id);
    }

    @Когда("пользователь проводит {int} операций через аккаунт, лимит 10 операций")
    public void пользовательПроводитОпераций(int value) throws IOException {

        getUsers();
        String id = users.stream().sorted(Comparator.comparing(User::getName).reversed()).collect(Collectors.toList()).get(0).getId();
        int count = 0;

        getUserInfo(id);

        while (count < value) {

            JSONObject requestBody = new JSONObject();
            requestBody.put("value", 10);
            requestBody.put("resource", Resource.ACCOUNT);

            Response postResponse = given().when()
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                    .post(basePath + "/users/" + id + "/operations");
            postResponse.then()
                    .statusCode(200).log();
            count++;
            System.out.println(postResponse.getBody().asString());
        }

        getOperationsResponse(id);
        getRemainResponse(id);
        getCurrentValues(id);

    }

    private void postOperationsResponse(String id, String resource, int value) {

        JSONObject postRequestBody = new JSONObject();
        postRequestBody.put("value", value);
        switch (resource) {
            case ("карты"):
                postRequestBody.put("resource", Resource.CARD);
                break;
            case ("аккаунта"):
                postRequestBody.put("resource", Resource.ACCOUNT);
                break;
            case ("карты и аккаунта"):
                postRequestBody.put("resource", Resource.CARD);

                Response postResponse = given().when()
                        .header("Content-Type", "application/json")
                        .body(postRequestBody.toString())
                        .post(basePath + "/users/" + id + "/operations");
                postResponse.then()
                        .statusCode(200).log();
                System.out.println(postResponse.getBody().asString());

                postRequestBody.put("value", value);
                postRequestBody.put("resource", Resource.ACCOUNT);
                break;
        }

        Response postResponse = given().when()
                .header("Content-Type", "application/json")
                .body(postRequestBody.toString())
                .post(basePath + "/users/" + id + "/operations");
        postResponse.then()
                .statusCode(200).log();
        System.out.println(postResponse.getBody().asString());
        //assertThat(postResponse.getBody().asString()).isEqualTo("{\"verdict\":\"Success\",\"message\":\"Успешная операция\"}");

        if (postResponse.getBody().asString().equals("{\"verdict\":\"Warning\",\"message\":\"Необходимо дополнительнео подтверждение операции\"}")) {

            JSONObject putRequestBody = new JSONObject();
            putRequestBody.put("value", value);
            switch (resource) {
                case ("карты"):
                    putRequestBody.put("resource", Resource.CARD);
                    break;
                case ("аккаунта"):
                    putRequestBody.put("resource", Resource.ACCOUNT);
                    break;
                case ("карты и аккаунта"):
                    putRequestBody.put("resource", Resource.CARD);

                    Response putResponse = given().when()
                            .header("Content-Type", "application/json")
                            .body(putRequestBody.toString())
                            .put(basePath + "/users/" + id + "/operations/confirm");
                    putResponse.then()
                            .statusCode(200).log();

                    putRequestBody.put("value", value);
                    putRequestBody.put("resource", Resource.ACCOUNT);
                    break;
            }

            Response putResponse = given().when()
                    .header("Content-Type", "application/json")
                    .body(putRequestBody.toString())
                    .put(basePath + "/users/" + id + "/operations/confirm");
            postResponse.then()
                    .statusCode(200).log();
        }
    }

    private void getOperationsResponse(String id) {
        Response getResponse = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id + "/operations");
        getResponse.then()
                .statusCode(200).log();
        System.out.println(getResponse.getBody().asString());

    }

    private void getUserInfo(String id) {

        Response getResponse = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id);
        getResponse.then()
                .statusCode(200).log();
        System.out.println(getResponse.getBody().asString());
    }

    private void getCurrentValues(String id) {
        Response getResponse = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id + "/currentValues");
        getResponse.then()
                .statusCode(200).log();
        System.out.println(getResponse.getBody().asString());
        System.out.println("\n");
    }

    private void getRemainResponse(String id) {
        Response getRemainResponse = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users/" + id + "/remain");
        getRemainResponse.then()
                .statusCode(200).log();
        System.out.println(getRemainResponse.getBody().asString());
    }

    public void getNewTestUser() throws JSONException {
        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/other/getTestUser");
        response.then()
                .statusCode(200).log();
    }

}
