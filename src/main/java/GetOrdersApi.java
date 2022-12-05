import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static constants.Constant.REQUEST_GET_ORDER_LIST;
import static io.restassured.RestAssured.given;

public class GetOrdersApi extends BaseApi{

    @Step("Получить список заказов")
    public ValidatableResponse getOrders (){
        return given()
                .spec(requestSpecification())
                .when()
                .get(REQUEST_GET_ORDER_LIST)
                .then();
    }
}

