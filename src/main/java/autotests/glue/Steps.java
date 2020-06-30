package autotests.glue;

import com.google.gson.Gson;
import cucumber.api.java.Before;
import cucumber.api.java.ru.Когда;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;

import static autotests.User.getUsers;
import static autotests.User.users;
import static io.restassured.RestAssured.given;

public class Steps {

    private Gson gson = new Gson();
    private static final String basePath = "http://localhost:8888";

    @Before
    public void setUp() {
        getNewTestUser();
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

    public void getNewTestUser() throws JSONException {

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/other/getTestUser");
        response.then()
                .statusCode(200).log();
    }

}
