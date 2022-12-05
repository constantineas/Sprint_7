import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

@DisplayName("Тест-сьют 'Создание курьера'")
public class CreateCourierTest {

    private Courier courier;
    private CourierAccount account;
    private CourierApi createCourier;
    private String id = "0";

    @Before
    public void setUp() {
        courier = AccountGenerator.getDefault();
        account = AccountGenerator.getDefaultLogin();
        createCourier = new CourierApi();
    }

    @After
    public void setDown(){
        createCourier.deleteCourier(id);
    }

    @Test
    @DisplayName("Можно ли создать курьера со всеми обязательными полями?")
    public void verifyCreateNewCourier() {
        ValidatableResponse responseCreateCourier =
                createCourier.createCourier(courier)
                        .assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                createCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
        String expected = "true";
        String actual = responseCreateCourier.extract().path("ok").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void verifyCreateTwoSameCourier() {
        ValidatableResponse responseCreateCourier =
                createCourier.createCourier(courier)
                        .assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                createCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
        String expected = "Этот логин уже используется. Попробуйте другой.";
        String actual = createCourier.createCourier(courier).extract().path("message").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа: 201")
    public void verifyCreateCourierScCreated() {
        ValidatableResponse responseCreateCourier =
                createCourier.createCourier(courier)
                        .assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                createCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
        int expected = 201;
        int actual = responseCreateCourier.extract().statusCode();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Успешный запрос возвращает ok: true")
    public void verifyCreateCourierOkTrue() {
        ValidatableResponse responseCreateCourier =
                createCourier.createCourier(courier)
                        .assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseLoginCourier =
                createCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
        String expected = "true";
        String actual = responseCreateCourier.extract().path("ok").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку")
    public void verifyCreateCourierWithoutAllFields() {
        Courier courierWithoutPassword = new Courier("Defcon184", "", "Konstantin");
        ValidatableResponse responseCreateCourier =
                createCourier.createCourier(courierWithoutPassword)
                        .assertThat().statusCode(SC_BAD_REQUEST);
        String expected = "Недостаточно данных для создания учетной записи";
        String actual = responseCreateCourier.extract().path("message").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void verifyCreateCourierWithSameLogin() {
        Courier courierWithSameLogin = new Courier("Defcon184", "213sdf45", "ElonMask");
        createCourier.createCourier(courier).assertThat().statusCode(SC_CREATED);
        ValidatableResponse responseCreateCourier =
                createCourier.createCourier(courierWithSameLogin)
                        .assertThat().statusCode(SC_CONFLICT);
        ValidatableResponse responseLoginCourier =
                createCourier.loginCourier(account).assertThat().statusCode(SC_OK);
        id = responseLoginCourier.extract().path("id").toString();
        String expected = "Этот логин уже используется. Попробуйте другой.";
        String actual =  responseCreateCourier.extract().path("message").toString();
        Assert.assertEquals(expected, actual);
    }
}
