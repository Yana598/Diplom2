package orderOrder;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import spec.BurgerRentSpec;

import static io.restassured.RestAssured.given;

public class OrderCheck extends BurgerRentSpec {

    private static final String NEWORDER = "api/orders";
    private static final String LISTORDER="api/orders/all";

    public OrderCheck() {
        RestAssured.baseURI = BASE_URI;
    }
  @Step("создание заказа")
    public ValidatableResponse createNewOrder(Order order,String token) {
        return given()
                .header("Authorization",token)
                .spec(BurgerRentSpec.requestSpecification())
                .and()
                .body(order)
                .when()
                .post(NEWORDER)
                .then();
    }
@Step("получаем лист заказов AUHT USER")
public ValidatableResponse getListOrder(String token){
   return given()
            .header("Authorization",token)
            .spec(BurgerRentSpec.requestSpecification())
            .get(NEWORDER)
            .then();
}
@Step("получаем лист заказов NO AUTH USER")
    public ValidatableResponse getListOrderNoAuth(){
        return given()
                .spec(BurgerRentSpec.requestSpecification())
                .get(NEWORDER)
                .then();
    }
}
