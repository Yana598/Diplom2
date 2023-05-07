package userUser;
import data.User;
import data.UserCreds;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import spec.ScooterRentSpec;

import static io.restassured.RestAssured.given;
import static userUser.UserGenerator.randomUser;

public class UserClient extends ScooterRentSpec {

    private static final String PATH = "api/auth/register";
    private static final String LOGIN_PATH = "api/auth/login";
    private static final String UPDATE="api/auth/user";

    public UserClient() {
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * создаем user
     */
    public ValidatableResponse create(User user) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(user)
                .when()
                .post(PATH)
                .then();
    }

    /**
     * авторизуем usera в системе
     */
    public ValidatableResponse login(UserCreds creds) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .body(creds)
                .post(LOGIN_PATH)
                .then();
    }

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
    public ValidatableResponse updateNoAuthorization() {
        return given()
                .log().all()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(randomUser())
                .when()
                .patch(UPDATE)
                .then();
    }

    public ValidatableResponse userDelete(String token) {
        return given()
                .header("Authorization",token)
                .spec(ScooterRentSpec.requestSpecification())
                .delete(UPDATE)
                .then();
    }


}
