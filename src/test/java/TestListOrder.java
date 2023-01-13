import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class TestListOrder {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Тестирование метода получения списка заказов")
    public void testGetListOrder() {
        MethodsAPI methodAPI = new MethodsAPI();
        Response response = methodAPI.getListOrder();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body(notNullValue());
    }
}
