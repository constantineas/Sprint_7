import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Constant.*;
import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApi {

    @Step("Создание заказа")
    public ValidatableResponse сreateOrders (Order order){
        return given()
                .spec(requestSpecification())
                .body(order)
                .when()
                .post(REQUEST_POST_ORDER_CREATE)
                .then();
    }
}
