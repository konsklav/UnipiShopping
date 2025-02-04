package com.example.unipishopping.core;

public class LoginError {

    public static final int INCORRECT_USERNAME = 1;
    public static final int INCORRECT_PASSWORD = 2;
    public static final int TASK_FAILED = 3;

    private int code;
    private String description;

    private LoginError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    static LoginError invalidUsername() {
        return new LoginError(INCORRECT_USERNAME, "Username is incorrect.");
    }

    static LoginError invalidPassword() {
        return new LoginError(INCORRECT_PASSWORD, "Password is incorrect.");
    }

    static LoginError taskFailed() {
        return new LoginError(TASK_FAILED, "An error occurred whilst trying to login!");
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
