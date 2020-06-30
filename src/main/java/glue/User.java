package glue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class User {

    private String id;
    private String name;
    private static Gson gson = new Gson();
    public static List<User> users = new LinkedList<>();
    private static final String basePath = "http://localhost:8888";


    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "glue.User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static void getUsers() throws JSONException {

        Response response = given().when()
                .header("Content-Type", "application/json")
                .get(basePath + "/users");

        users = gson.fromJson(response.getBody().asString(), new TypeToken<ArrayList<User>>() {
        }.getType());
    }
}
