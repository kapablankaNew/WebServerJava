package org.kapablankaNew.simpleWebServer;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, UserProfile> loginToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
    }

    public void addUser(UserProfile userProfile) {
        if (!isUserAuthorized(userProfile.getLogin())) {
            loginToProfile.put(userProfile.getLogin(), userProfile);
        }
    }

    public boolean isUserAuthorized(String login) {
        return loginToProfile.containsKey(login);
    }

    public UserProfile getUserProfile(String login) {
        return loginToProfile.get(login);
    }
}
