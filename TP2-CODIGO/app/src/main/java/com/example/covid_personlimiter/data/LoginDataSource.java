package com.example.covid_personlimiter.data;

import com.example.covid_personlimiter.data.model.LoggedInUser;

import java.io.IOException;
import java.net.URL;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            URL mUrl = new URL("xd");
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe",
                            "xd",
                            "xd2");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void refresh_token() {
        // TODO: revoke authentication
    }

    public void logout() {
        // TODO: revoke authentication
    }
}