package com.solvd.testing.zebrunner.api;

/*
This class is implemented as a singleton, since we only need one instance of the token for each and every
REST API call. The token is automagically refreshed once it expires, so no need of checking dates.
*/

public class AuthToken {
    private static AuthToken instance;
    private String authToken;

    private AuthToken() {
    }

    public static synchronized AuthToken getInstance() {
        if (instance == null) {
            //Read the auth details from the property file
            instance = new AuthToken();
            instance.authToken = RestApiWrapper.getAuthToken();
        }
        return instance;
    }

    public String getAuthToken() {
        return authToken;
    }

}