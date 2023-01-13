import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestAddOrder {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;


    public TestAddOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha", "4", "800553535", 5, "2020", "Saske", new String[]{"BLACK", "GREY"}},
                {"Naruto", "Uchiha", "Konoha", "4", "800553535", 5, "2020", "Saske", new String[]{"BLACK"}},
                {"Naruto", "Uchiha", "Konoha", "4", "800553535", 5, "2020", "Saske", new String[]{"GREY"}},
                {"Naruto", "Uchiha", "Konoha", "4", "800553535", 5, "2020", "Saske", new String[]{""}},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Тестирование метода создания заказа")
    public void testAddOrder() {
        MethodsAPI methodAPI = new MethodsAPI();
        Response response = methodAPI.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("track", notNullValue());
    }
}
