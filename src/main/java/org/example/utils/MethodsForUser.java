package org.example.utils;

public class MethodsForUser {

    /**
     * Конструктор для получения и обновления токена и использования при удалении юзера в tearDown
     */
    private String success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String message;

    public MethodsForUser() { }

    public MethodsForUser(String success, User user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public MethodsForUser(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getAccessToken() {

        return accessToken;
    }

    public void setAccessToken(String accessToken) {

        this.accessToken = accessToken;
    }

    public String getSuccess() {

        return success;
    }

    public void setSuccess(String success) {

        this.success = success;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getRefreshToken() {

        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {

        this.refreshToken = refreshToken;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}