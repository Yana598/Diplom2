package orderOrder;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import spec.ScooterRentSpec;

import static io.restassured.RestAssured.given;

public class OrderCheck extends ScooterRentSpec {

    private static final String NEWORDER = "api/orders";
    private static final String LISTORDER="api/orders/all";

    public OrderCheck() {
        RestAssured.baseURI = BASE_URI;
    }

    public ValidatableResponse createNewOrder(Order order,String token) {
        return given()
                .header("Authorization",token)
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(order)
                .when()
                .post(NEWORDER)
                .then();
    }

public ValidatableResponse getListOrder(String token){
   return given()
            .header("Authorization",token)
            .spec(ScooterRentSpec.requestSpecification())
            .get(NEWORDER)
            .then();
}

    public ValidatableResponse getListOrderNoAuth(){
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .get(NEWORDER)
                .then();
    }
}
