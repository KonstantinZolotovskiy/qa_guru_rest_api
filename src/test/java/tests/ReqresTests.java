package tests;

import com.github.javafaker.Faker;
import models.requests.CreateUserRequestDto;
import models.requests.LoginUserRequestDto;
import models.requests.RegistrationUserRequestDto;
import models.requests.UpdateUserRequestDto;
import models.responses.CreateUserResponseDto;
import models.responses.LoginUserUnsuccessfulResponseDto;
import models.responses.RegistrationUserUnsuccessfulResponseDto;
import models.responses.UpdateUserResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.responseSpec;

public class ReqresTests {

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

        CreateUserResponseDto response = step("Create user", () ->
                given(requestSpec)
                        .body(createUserBody)
                        .post("/users")
                        .then()
                        .spec(responseSpec)
                        .statusCode(201)
                        .extract().as(CreateUserResponseDto.class));

        step("Verify user name", () ->
                Assertions.assertThat(response.getName()).isEqualTo(name));

        step("Verify user job", () ->
                Assertions.assertThat(response.getJob()).isEqualTo(job));
    }

    @Test
    @DisplayName("Update user")
    void updateUserTest() {

        UpdateUserRequestDto updateUserBody = new UpdateUserRequestDto();
        updateUserBody.setName(name);
        updateUserBody.setJob(job);

        UpdateUserResponseDto response = step("Update user", () ->
                given(requestSpec)
                        .body(updateUserBody)
                        .put("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(UpdateUserResponseDto.class));

        step("Verify user name", () ->
                Assertions.assertThat(response.getName()).isEqualTo(name));

        step("Verify user job", () ->
                Assertions.assertThat(response.getJob()).isEqualTo(job));
    }

    @Test
    @DisplayName("Delete user")
    void deleteUserTest() {
        step("Check delete user", () ->
                given(requestSpec)
                        .delete("/users/2")
                        .then()
                        .statusCode(204));
    }

    @Test
    @DisplayName("Login without password")
    void negativeLoginTest() {

        LoginUserRequestDto loginUserBody = new LoginUserRequestDto();
        loginUserBody.setEmail(email);

        LoginUserUnsuccessfulResponseDto response = step("Login without password", () ->
                given(requestSpec)
                        .body(loginUserBody)
                        .post("/login")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(LoginUserUnsuccessfulResponseDto.class));

        step("Verify error message", () ->
                Assertions.assertThat(response.getError()).isEqualTo("Missing password"));
    }

    @Test
    @DisplayName("Registration without email")
    void negativeRegistrationTest() {

        RegistrationUserRequestDto registrationUserBody = new RegistrationUserRequestDto();
        registrationUserBody.setPassword(password);

        RegistrationUserUnsuccessfulResponseDto response = step("Registration without email", () ->
                given(requestSpec)
                        .body(registrationUserBody)
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .extract().as(RegistrationUserUnsuccessfulResponseDto.class));

        step("Verify error message", () ->
                Assertions.assertThat(response.getError()).isEqualTo("Missing email or username"));
    }
}
