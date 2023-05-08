package userUser;

import data.User;
import data.UserCreds;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import spec.BurgerRentSpec;

import static io.restassured.RestAssured.given;
import static userUser.UserGenerator.randomUser;

public class UserClient extends BurgerRentSpec {

    private static final String PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String UPDATE="api/auth/user";

    public UserClient() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("создание USER")
    public ValidatableResponse create(User user) {
        return given()
                .spec(BurgerRentSpec.requestSpecification())
                .and()
                .body(user)
                .when()
                .post(PATH)
                .then();
    }

    @Step("login USER")
    public ValidatableResponse login(UserCreds creds) {
        return given()
                .spec(BurgerRentSpec.requestSpecification())
                .body(creds)
                .post(LOGIN_PATH)
                .then();
    }
    @Step("обновление данных AUTH USER")
    public ValidatableResponse update(String token) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .body(randomUser())
                .when()
                .patch(UPDATE)
                .then();
    }

    @Step("обновление данных AUTH USER")
    public ValidatableResponse updateNoAuthorization() {
        return given()
                .log().all()
                .spec(BurgerRentSpec.requestSpecification())
                .and()
                .body(randomUser())
                .when()
                .patch(UPDATE)
                .then();
    }
    @Step("удаление USER")
    public ValidatableResponse userDelete(String token) {
        return given()
                .header("Authorization",token)
                .spec(BurgerRentSpec.requestSpecification())
                .delete(UPDATE)
                .then();
    }


}
