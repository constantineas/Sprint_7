import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

@DisplayName("Тест-сьют 'Список заказов'")
public class GetOrdersPageByPageTest {

    private GetOrdersApi getOrders;

    @Before
    public void setUp() {
        getOrders = new GetOrdersApi();
    }

    @Test
    @DisplayName("Тело ответа возвращается список заказов")
    public void verifyCreateOrder() {
        ValidatableResponse responseCreateOrder =
                getOrders.getOrders()
                        .assertThat().statusCode(SC_OK);
        String data = responseCreateOrder.extract().path("orders").toString();
        Boolean expected = false;
        Boolean actual = data.isEmpty();
        Assert.assertEquals(expected, actual);
    }
}
