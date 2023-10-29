package org.example.utils;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRequests {

    /**
     С помощью библиотеки RestAssured получилось написать метод, позволяющий удалить созданного пользователя и выполнить
     12ый пункт в требованиях "Нужные тестовые данные создаются перед тестом и удаляются после того, как он выполнится."
     Написаны на основе Api проекта. Единственный найденный способ удалить клиента
     */


    @Step("Создание клиента")
    public void createUser(String name, String email, String password) {
        Response response = given(this.baseRequest("application/json"))
                .body(new User(email, password, name))
                .when()
                .post(ListOfUrls.CREATE_USER);

        response.getStatusCode();
    }

    @Step("Аутентификация клиента")
    private Response loginUser(String email, String password) {
        return given(this.baseRequest("application/json"))
                .body(new User(email, password))
                .when()
                .post(ListOfUrls.USER_LOGIN);
    }

    @Step("Удаление клиента по токену")
    private Response deleteUserByToken(String token) {
        return given(this.baseRequest())
                .auth().oauth2(token)
                .delete(ListOfUrls.DELETE_USER);
    }
    @Step("Удаление клиента")
    public void deleteUser(String email, String password) {
        Response response = loginUser(email, password);

        if(response.getStatusCode() != 200) return;

        String token = response.body().as(MethodsForUser.class).getAccessToken().split(" ")[1];
        deleteUserByToken(token);
    }

    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
    private RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }
}