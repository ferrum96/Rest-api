package glue;

import com.google.gson.Gson;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.IOException;

import static glue.User.getUsers;
import static glue.User.users;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Steps {

    private Gson gson = new Gson();
    private static final String basePath = "http://localhost:8888";

    @Before
    public void setUp() {
            getNewTestUserTest();
    }

    @Когда("пользователь снимает {int} рублей, лимит 400 рублей")
    public void пользовательСнимаетРублейЛимитРублей(int value) {
        getUsers();
        String id = users.get(0).getId();

        JSONObject requestBody = new JSONObject();
        requestBody.put("value", value);
        requestBody.put("resource", "ACCOUNT");

        Response response = given().when()
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .post(basePath + "/users/" + id + "/operations");
        response.then()
                .statusCode(200).log();
        System.out.println(response.getBody().asString());
    }

    public void getNewTestUserTest() throws JSONException {

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/other/getTestUser");
        response.then()
                .statusCode(200).log();
    }



}
