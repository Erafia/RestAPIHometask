package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.SingleUser;
import models.UsersList;
import org.apache.http.protocol.HTTP;

import static io.restassured.RestAssured.given;

public class UsersAdapter extends MainAdapter {

    public UsersList get(int page) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .get(String.format("https://reqres.in/api/users?page=%s", page))
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();
        return gson.fromJson(response.asString().trim(), UsersList.class);
    }
    public UsersList getWithDelay() {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .get("https://reqres.in/api/users?delay=3")
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();
        return gson.fromJson(response.asString().trim(), UsersList.class);
    }

    public SingleUser getUserById(int id){
        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .get(String.format("https://reqres.in/api/users/%s", id))
                .then()
                        .log().all(false)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), SingleUser.class);
    }

    public int getUserByIdStatusCode(int id){
        int statusCode =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .get(String.format("https://reqres.in/api/users/%s", id))
                .then()
                        .log().all(false)
                        .extract().statusCode();
        return statusCode;
    }

    public String getUserByIdResponseBody(int id){
        String body =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                        .when()
                        .get(String.format("https://reqres.in/api/users/%s", id))
                        .then()
                        .log().all(false)
                        .contentType(ContentType.JSON).extract().body().asString();
        return body;
    }

}
