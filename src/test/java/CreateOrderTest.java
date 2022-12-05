import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;

@DisplayName("Тест-сьют 'Создание заказа'")
@RunWith(value = Parameterized.class)
public class CreateOrderTest {

    private OrderApi createOrder;
    private Order order;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public CreateOrderTest(String firstName, String lastName, String address,
                           int metroStation, String phone, int rentTime,
                           String deliveryDate, String comment, List<String> color) {
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
    public static Object[] getData()
    {
        return new Object[][]
                {

                        {"Elon", "Mask", "Palo Alto, 245 off.1", 1, "+7 984 547 69 87", 4, "2022-12-09",
                                "I would like to die on Mars. Just not on impact.", List.of("BLACK")},

                        {"Naruto", "Uchiha", "Konoha, 151 apt.1", 2, "+7 587 897 55 00", 5, "2022-12-15",
                                "Saske2, come back to Konoha2", List.of("BLACK", "GREY")},

                        {"Jaina", "Proudmoore", "Theramore, 234 apt.23", 3, "+7 458 756 45 73", 6, "2022-12-19",
                                "Beware the Daughter of the Sea.", List.of("")},

                        {"Archmage", "Khadgar", "Dalaran, 874 apt.25", 4, "+7 574 802 01 25", 7, "2022-12-18",
                                "Hello", List.of("BLACK", "GREY")}
                };
    }

    @Before
    public void setUp() {
        createOrder = new OrderApi();
    }

    @Test
    @DisplayName("Параметризированный тест создания заказа")
    public void verifyCreateOrder() {
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse responseCreateOrder =
                createOrder.сreateOrders(order)
                        .assertThat().statusCode(SC_CREATED);
        String data = responseCreateOrder.extract().path("track").toString();
        Boolean expected = false;
        Boolean actual = data.isEmpty();
        Assert.assertEquals(expected, actual);
    }

}
