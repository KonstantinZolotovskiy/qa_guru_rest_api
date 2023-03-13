package tests;

import com.github.javafaker.Faker;
import models.requests.CreateUserRequestDto;
import models.requests.LoginUserRequestDto;
import models.requests.RegistrationUserRequestDto;
import models.requests.UpdateUserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;
import static org.hamcrest.Matchers.*;

public class ReqresGroovyTests {

    Faker faker = new Faker();

    String name, job, email, password;

    @BeforeEach
    public void init() {
        name = faker.name().firstName();
        job = faker.job().position();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
    }

    @Test
    @DisplayName("Create user")
    void createUserTest() {

        CreateUserRequestDto createUserBody = new CreateUserRequestDto();
        createUserBody.setName(name);
        createUserBody.setJob(job);

        given(requestSpec)
                .body(createUserBody)
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job));
    }

    @Test
    @DisplayName("Update user")
    void updateUserTest() {

        UpdateUserRequestDto updateUserBody = new UpdateUserRequestDto();
        updateUserBody.setName(name);
        updateUserBody.setJob(job);

        given(requestSpec)
                .body(updateUserBody)
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("name", is(name))
                .body("job", is(job));
    }

    @Test
    @DisplayName("Delete user")
    void deleteUserTest() {

        given(requestSpec)
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Login without password")
    void negativeLoginTest() {

        LoginUserRequestDto loginUserBody = new LoginUserRequestDto();
        loginUserBody.setEmail(email);

        given(requestSpec)
                .body(loginUserBody)
                .post("/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Registration without email")
    void negativeRegistrationTest() {

        RegistrationUserRequestDto registrationUserBody = new RegistrationUserRequestDto();
        registrationUserBody.setPassword(password);

        given(requestSpec)
                .body(registrationUserBody)
                .post("/register")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
