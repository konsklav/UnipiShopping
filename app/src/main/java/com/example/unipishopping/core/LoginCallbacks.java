package com.example.unipishopping.core;

import com.example.unipishopping.domain.User;

/**
 * Provides callbacks for handling login events.
 * <p>
 *     Implement this interface if you wish to be notified of a login success/failure.
 * </p>
 */
public interface LoginCallbacks {

    /**
     * Is invoked when the login procedure succeeds and user credentials are retrieved.
     * @param user The user's details
     */
    void onLoginSuccess(User user);


    /**
     * Is invoked when something goes wrong in the login procedure.
     * @param error An error instance containing useful information about what went wrong.
     */
    void onLoginFail(LoginError error);
}
