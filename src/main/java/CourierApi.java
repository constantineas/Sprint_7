import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Constant.*;
import static io.restassured.RestAssured.given;

public class CourierApi extends BaseApi{

    @Step ("Создание нового курьера")
    public ValidatableResponse createCourier (Courier courier) {
        return given()
                .spec(requestSpecification())
                .body(courier)
                .when()
                .post(REQUEST_POST_COURIER_CREATE)
                .then();
    }

    @Step ("Авторизация курьера")
    public ValidatableResponse loginCourier (CourierAccount account) {
        return given()
                .spec(requestSpecification())
                .body(account)
                .when()
                .post(REQUEST_POST_COURIER_LOGIN)
                .then();
    }

    @Step ("Удаление курьера")
    public ValidatableResponse deleteCourier (String idCourier) {
        return given()
                .spec(requestSpecification())
                .when()
                .delete(REQUEST_DELETE_COURIER_DELETE + idCourier )
                .then();
    }

    public static class OrderListApi {
    }
}
