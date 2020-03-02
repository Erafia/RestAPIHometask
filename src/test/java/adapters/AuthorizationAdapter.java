package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.loginNregister.LoginUserRequest;
import models.loginNregister.LoginUserResponse;
import models.loginNregister.UnsuccessLoginUserResponse;
import org.apache.http.protocol.HTTP;

import static io.restassured.RestAssured.given;

public class AuthorizationAdapter extends MainAdapter{

    public LoginUserResponse post(LoginUserRequest user) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                .when()
                        .post("https://reqres.in/api/register")
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), LoginUserResponse.class);
    }

    public UnsuccessLoginUserResponse postInvalid(LoginUserRequest user) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                .when()
                        .post("https://reqres.in/api/register")
                .then()
                        .log().all()
                        .statusCode(400)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), UnsuccessLoginUserResponse.class);
    }

    public LoginUserResponse postLogin(LoginUserRequest user) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), LoginUserResponse.class);
    }

    public UnsuccessLoginUserResponse postInvalidLogin(LoginUserRequest user) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().all()
                        .statusCode(400)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), UnsuccessLoginUserResponse.class);
    }
}
