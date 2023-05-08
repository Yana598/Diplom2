package praktikum;

import data.ResponseData;
import data.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import orderOrder.Order;
import orderOrder.OrderCheck;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import spec.BurgerRentSpec;
import userUser.UserClient;

import java.util.List;

import static data.UserCreds.credsFrom;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static userUser.UserGenerator.randomUser;

@RunWith(Parameterized.class)
public class OrderParametr {

    private UserClient userClient;
    private User user;
    private OrderCheck orderCheck;
    private Order order;
    private final List<String> ingredients;
    public OrderParametr(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getData() {
        return new Object[][] {
                {List.of("61c0c5a71d1f82001bdaaa70","61c0c5a71d1f82001bdaaa72")},
                {List.of()},
                {List.of("6878758","6757658") }
        };
    }

    @Before
    public void setUp() {
        user = randomUser();
        userClient = new UserClient();
        RestAssured.baseURI = BurgerRentSpec.BASE_URI;
    }

    @Test
    @DisplayName("создание заказа")
    @Description("проверка создания заказа авторизированным пользователем")
    public void createOrder() {
        userClient.create(user);
        userClient.login(credsFrom(user));
        String token = userClient.login(credsFrom(user)).extract().response().as(ResponseData.class).getAccessToken();
        order=new Order(ingredients);
        orderCheck=new OrderCheck();
        ValidatableResponse response=orderCheck.createNewOrder(order,token);
        response.assertThat().statusCode(SC_OK).and().body("success", is( true));
    }
    @Test
    @DisplayName("создание заказа")
    @Description("проверка создания заказа неавторизированным пользователем")
    public void createOrderNoAuth() {
        userClient.create(user);
        userClient.login(credsFrom(user));
        order=new Order(ingredients);
        orderCheck=new OrderCheck();
        ValidatableResponse response=orderCheck.createNewOrder(order,"");
        response.assertThat().statusCode(SC_OK).and().body("success", is( true));
    }

}
