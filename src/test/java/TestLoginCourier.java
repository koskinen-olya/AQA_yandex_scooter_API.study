import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TestLoginCourier {

    private final String login;
    private final String password;
    private final int expectedCode;
    private final String expectedMessage;

    public TestLoginCourier(String login, String password, int expectedCode, String expectedMessage) {
        this.login = login;
        this.password = password;
        this.expectedCode = expectedCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"CourierNumberOne", "12345", 200, ""},
                {"CourierNumberOne", "", 400, "Недостаточно данных для входа"},
                {"", "12345", 400, "Недостаточно данных для входа"},
                {"CourierNumberTwo", "12345", 404, "Учетная запись не найдена"}


        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        MethodsAPI methodsAPI = new MethodsAPI();
        //Добавляем тестовую учетку для проверки успешного первого login
        methodsAPI.addCourier("CourierNumberOne", "12345", "courier1");
        //На всякий случай удаляем учетку для проверки несуществующего пользователя
        methodsAPI.deleteCourier("CourierNumberTwo", "12345");
    }


    @Test
    @DisplayName("Тестирование метода логина курьера")
    public void testLoginCourier() {
        MethodsAPI methodsAPI = new MethodsAPI();
        Response response = methodsAPI.loginCourier(login, password);
        response.then().assertThat().statusCode(expectedCode);
        if (expectedCode == 200) {
            response.then().assertThat().body("id", notNullValue());
        } else
            response.then().assertThat().body("message", equalTo(expectedMessage));
    }
}
