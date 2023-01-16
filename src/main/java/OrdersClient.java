import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    public Response createOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        BodyForOrder jsonBody = new BodyForOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(jsonBody)
                        .when()
                        .post("/api/v1/orders");
        return response;
    }

    public Response getListOrder() {
        Response response =
                given()
                        .get("/api/v1/orders");
        return response;
    }
}
