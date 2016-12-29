package com.gutierrez.diego.android_template.managers;

import java.util.HashMap;

/**
 * Created by diego on 27-12-16.
 */
public interface SessionManager {

    public void createSession (String username, String password, String token);

    public HashMap<String, String> getUserDetails();

    public boolean isLoggedIn();

    public void logoutUser();

}
