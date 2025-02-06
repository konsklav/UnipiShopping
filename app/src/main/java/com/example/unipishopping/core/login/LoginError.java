package com.example.unipishopping.core.login;

/**
 * Contains useful details about what went wrong in the login procedure.
 */
public class LoginError {

    public static final int INVALID_CREDENTIALS = 0;
    public static final int USERNAME_NOTFOUND = 1;
    public static final int PASSWORD_NOTFOUND = 2;
    public static final int TASK_FAILED = 3;

    private final int code;
    private final String description;

    private LoginError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    static LoginError invalidCredentials() {
        return new LoginError(INVALID_CREDENTIALS, "You need to fill out the username and password fields!");
    }

    static LoginError usernameNotFound() {
        return new LoginError(USERNAME_NOTFOUND, "Username is incorrect.");
    }

    static LoginError passwordNotFound() {
        return new LoginError(PASSWORD_NOTFOUND, "Password is incorrect.");
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
