import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

public class TestAddCourier {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        CourierClient courierClient = new CourierClient();
        courierClient.addCourier("CourierNumberOneTest", "12345", "courier1Test");
        courierClient.deleteCourier("CourierNumberOne", "12345");
    }

    @Test
    @DisplayName ("Тестирование метода создания курьера (позитивная проверка)")
    public void testAddCourierPositive() {
        CourierClient courierClient = new CourierClient();
        Response response = courierClient.addCourier("CourierNumberOne", "12345", "courier1");
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName ("Тестирование метода создания курьера (негативные проверки)")
    public void testAddCourierNegative() {
        CourierClient courierClient = new CourierClient();
        Response response = courierClient.addCourier("CourierNumberOneTest", "12345", "courier1Test");
        response.then().assertThat().statusCode(409);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        Response response1 = courierClient.addCourier("CourierNumberTwo", "", "courier2");
        response1.then().assertThat().statusCode(400);
        response1.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        CourierClient courierClient = new CourierClient();
        courierClient.deleteCourier("CourierNumberOne", "12345");
    }

}