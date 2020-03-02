package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.jobUser.JobUserCreationResponse;
import models.jobUser.UserNameJob;
import models.jobUser.JobUserUpdateResponse;
import org.apache.http.protocol.HTTP;

import static io.restassured.RestAssured.given;

public class JobUserAdapter extends MainAdapter {
    public JobUserCreationResponse post(UserNameJob user) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                .when()
                        .post("https://reqres.in/api/users")
                .then()
                        .log().all()
                        .statusCode(201)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), JobUserCreationResponse.class);
    }

    public JobUserUpdateResponse patch(UserNameJob user, int id){
        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                .when()
                        .patch(String.format("https://reqres.in/api/users/%s", id))
                .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), JobUserUpdateResponse.class);
    }

    public JobUserUpdateResponse put(UserNameJob user, int id){
        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .body(gson.toJson(user))
                        .log().all()
                .when()
                        .put(String.format("https://reqres.in/api/users/%s", id))
                .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), JobUserUpdateResponse.class);
    }

    public int deleteJobUserByIdStatusCode(int id){
        int statusCode =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .delete(String.format("https://reqres.in/api/users/%s", id))
                .then()
                        .log().all(false)
                        .extract().statusCode();
        return statusCode;
    }

}
