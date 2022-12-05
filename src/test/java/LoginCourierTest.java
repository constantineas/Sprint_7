import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

@DisplayName("Тест-сьют 'Логин курьера в системе'")
public class LoginCourierTest {

    private Courier courier;
    private CourierAccount account;
    private CourierApi loginCourier;
    private String id = "0";

    @Before
    public void setUp() {
        courier = AccountGenerator.getDefault();
        account = AccountGenerator.getDefaultLogin();
        loginCourier = new CourierApi();
    }

    @After
    public void setDown(){
        loginCourier.deleteCourier(id);
    }

    @Test
    @DisplayName("Курьер может авторизоваться при передаче всех обязательных полей")
    public void verifyLoginCourierWithAllField() {
        loginCourier.createCourier(courier).assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                loginCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль или несуществующий аккаунт")
    public void verifyCreateNewCourier() {
        CourierAccount accountWithIncorrectData = new CourierAccount("Elon438","Mask256");
        ValidatableResponse responseLoginCourier =
                loginCourier.loginCourier(accountWithIncorrectData)
                        .assertThat().statusCode(SC_NOT_FOUND);
        String expected = "Учетная запись не найдена";
        String actual = responseLoginCourier.extract().path("message").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Если какого-то поля нет, запрос возвращает ошибку")
    public void verifyLoginCourierWithoutAllField() {
        CourierAccount accountWithoutPassword = new CourierAccount("Defcon184", "");
        loginCourier.createCourier(courier).assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                loginCourier.loginCourier(accountWithoutPassword).assertThat().statusCode(SC_BAD_REQUEST);
        id = loginCourier.loginCourier(account).extract().path("id").toString();
        String expected = "Недостаточно данных для входа";
        String actual = responseLoginCourier.extract().path("message").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("успешный запрос возвращает id")
    public void verifyLoginCourierWithEmptyAccount() {
        loginCourier.createCourier(courier).assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                loginCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
        String data = responseLoginCourier.extract().path("id").toString();
        Boolean expected = false;
        Boolean actual = data.isEmpty();
        Assert.assertEquals(expected, actual);
    }

}
