package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.resource.ResourceList;
import models.resource.SingleResource;
import org.apache.http.protocol.HTTP;

import static io.restassured.RestAssured.given;

public class ResourcesAdapter extends MainAdapter {

    public ResourceList get() {
        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                        .when()
                        .get("https://reqres.in/api/unknown")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();
        return gson.fromJson(response.asString().trim(), ResourceList.class);
    }

    public SingleResource getResourceById(int id) {
        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                        .when()
                        .get(String.format("https://reqres.in/api/unknown/%s", id))
                        .then()
                        .log().all(false)
                        .extract().response();

        return gson.fromJson(response.asString().trim(), SingleResource.class);
    }

    public int getResourceByIdStatusCode(int id) {
        int statusCode =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                        .when()
                        .get(String.format("https://reqres.in/api/unknown/%s", id))
                        .then()
                        .log().all(false)
                        .contentType(ContentType.JSON).extract().statusCode();
        return statusCode;
    }

    /*здесь возникает вопрос: а может стоит сделать отдельный объект "Респонс" с необходимыми к проверке полями (i.e. код, тело, заголовки)
    и в каждом классе просто вызывать метод, который будет моделировать респонс, к которому можно будет обращаться в тестах для
    получения частичных данных? Т.е. когда нужно проверить, что тело пустое или соответствие кодов*/

    public String getResourceByIdResponseBody(int id) {
        String body =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                        .when()
                        .get(String.format("https://reqres.in/api/unknown/%s", id))
                        .then()
                        .log().all(false)
                        .contentType(ContentType.JSON).extract().body().asString();
        return body;
    }
}
