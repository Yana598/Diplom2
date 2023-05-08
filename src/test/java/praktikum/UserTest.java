package praktikum;

import data.ResponseData;
import data.User;
import data.UserCreds;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import orderOrder.OrderCheck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spec.BurgerRentSpec;
import userUser.UserClient;

import static data.UserCreds.credsFrom;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static userUser.UserGenerator.randomUser;



public class UserTest {

    private UserClient userClient;
    private User user;
    private static String token;
    private OrderCheck orderCheck;

    @Before
    public void setUp() {
        user = randomUser();
        userClient = new UserClient();
        RestAssured.baseURI = BurgerRentSpec.BASE_URI;
    }

    @Test
    @DisplayName("регистрация нового пользователя")
    @Description("проверка создания пользователя с валидными данными")
    public void createUser() {
        userClient=new UserClient();
        ValidatableResponse response = userClient.create(user);
       // token = userClient.login(credsFrom(user)).extract().response().as(ResponseData.class).getAccessToken();
        response.assertThat().statusCode(SC_OK).and().body("success", is( true));
    }

    @Test
    @DisplayName("авторизация")
    @Description("проверка авторизации пользователя с валидными данными")
    public void loginUser() {
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(credsFrom(user));
        token = userClient.login(credsFrom(user)).extract().response().as(ResponseData.class).getAccessToken();
        loginResponse.assertThat().statusCode(SC_OK).and().body("success", is( true));
    }

    @Test
    @DisplayName("регистрация нового пользователя")
    @Description("проверка создания уже созданного пользователя")
    public void createSameUser() {
        userClient.create(user);
        ValidatableResponse response = userClient.create(user);
        response.assertThat().statusCode(SC_FORBIDDEN).and().body("success", is( false));
    }

    @Test
    @DisplayName("регистрация нового пользователя")
    @Description("проверка создания пользователя без поля name")
    public void loginUserWithoutField() {
        ValidatableResponse response = userClient.create(new User(user.getEmail(), user.getPassword(), null));
        response.assertThat().statusCode(SC_FORBIDDEN).and().body("success", is( false));
    }

    @Test
    @DisplayName("Авторизация пользователя с неправильным паролем и логином")
    @Description("Проверка, что user не может авторизоваться с неправильным паролем и логином")
    public void NoLoginUserWithWrongEmailPasswordTest() {
        userClient.create(user);
        ValidatableResponse loginResponse = userClient.login(new UserCreds("bhjbjh","fuyg"));
        loginResponse.assertThat().statusCode(SC_UNAUTHORIZED).and().body("message", is("email or password are incorrect"));
    }

    @DisplayName("обновление данных user")
    @Description("с авторизацией")
    @Test
    public void updateUser(){
        userClient.create(user);
        userClient.login(credsFrom(user));
        token = userClient.login(credsFrom(user)).extract().response().as(ResponseData.class).getAccessToken();
        ValidatableResponse response = userClient.update(token);
        response.assertThat().statusCode(SC_OK).and().body("success", is( true));
    }

    @DisplayName("обновление данных user")
    @Description("без авторизации")
    @Test
    public void updateUserNoAuth(){
        userClient.create(user);
        userClient.login(credsFrom(user));
        ValidatableResponse response = userClient.updateNoAuthorization();
        response.assertThat().statusCode(SC_UNAUTHORIZED).and().body("success", is( false));

    }
    @DisplayName("получение заказов конкретного user")
    @Description("с авторизацией")
    @Test
    public void getOrderList(){
        userClient.create(user);
        userClient.login(credsFrom(user));
        orderCheck=new OrderCheck();
        //token = userClient.login(credsFrom(user)).extract().response().as(ResponseData.class).getAccessToken();
        ValidatableResponse response =orderCheck.getListOrder(token);
        response.assertThat().statusCode(SC_OK).and().body("success", is( true));

    }

    @DisplayName("получение заказов конкретного user")
    @Description("без авторизации")
    @Test
    public void getOrderListNoAuth(){
        userClient.create(user);
        orderCheck=new OrderCheck();
        ValidatableResponse response = orderCheck.getListOrderNoAuth();
        response.assertThat().statusCode(SC_UNAUTHORIZED).and().body("message", is( "You should be authorised"));
    }

    @After
    public void teaDown(){userClient.userDelete(token);}
}
