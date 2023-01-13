import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class TestAddCourier {
    private final String login;
    private final String password;
    private final String firstName;
    private final int expectedCode;
    private final String expectedMessage;

    public TestAddCourier(String login, String password, String firstName, int expectedCode, String expectedMessage) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedCode = expectedCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"CourierNumberOne", "12345", "courier1", 201, ""},
                {"CourierNumberOneTest", "12345", "courier1Test", 409, "Этот логин уже используется. Попробуйте другой."},
                {"CourierNumberTwo", "", "courier2", 400, "Недостаточно данных для создания учетной записи"},
                {"", "12345", "courier3", 400, "Недостаточно данных для создания учетной записи"},
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        MethodsAPI methodAPI = new MethodsAPI();
        methodAPI.addCourier("CourierNumberOneTest", "12345", "courier1Test");
        methodAPI.deleteCourier("CourierNumberOne", "12345");
    }


    @Test
    @DisplayName ("Тестирование метода создания курьера")
    public void testAddCourier() {
        MethodsAPI methodAPI = new MethodsAPI();
        Response response = methodAPI.addCourier(login, password, firstName);
        response.then().assertThat().statusCode(expectedCode);
        if (expectedCode == 201) {
            response.then().assertThat().body("ok", equalTo(true));
        } else
            response.then().assertThat().body("message", equalTo(expectedMessage));
    }

    @After
    public void deleteCourier() {
        MethodsAPI methodAPI = new MethodsAPI();
        methodAPI.deleteCourier(login, password);
    }

}