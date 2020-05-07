package com.techelevator.authentication;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.techelevator.model.UserDao;
import com.techelevator.pojo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RequestAuthProvider
 */
@Component
public class RequestAuthProvider implements AuthProvider {

    private HttpServletRequest request;
    private UserDao dao;
    public final static String USER_KEY = "appCurrentUser";

    @Autowired
    public RequestAuthProvider(HttpServletRequest request, UserDao dao) {
        this.request = request;
        this.dao = dao;
    }

    @Override
    public boolean isLoggedIn() {
        return (request.getAttribute(USER_KEY) != null);
    }

    @Override
    public Users getCurrentUser() {
        return (Users) request.getAttribute(USER_KEY);
    }

    @Override
    public boolean signIn(String username, String password) {
        Users authenticatedUser = dao.getValidUserWithPassword(username, password);
        if (authenticatedUser != null) {
            request.setAttribute(USER_KEY, authenticatedUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logOff() {
        request.removeAttribute(USER_KEY);
    }

    @Override
    public boolean changePassword(String existingPassword, String newPassword) {
        Users userFromSession = (Users) request.getAttribute(USER_KEY);
        if (userFromSession == null) {
            return false;
        }
        Users userFromDb = dao.getValidUserWithPassword(userFromSession.getUsername(), existingPassword);
        if (userFromDb != null && userFromDb.getId() == userFromDb.getId()) {
            dao.changePassword(userFromSession, newPassword);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void register(String username, String password, boolean isManager) {
        dao.saveUser(username, password, isManager);
    }

    @Override
    public boolean userHasRole(String[] roles) {
        Users currentUser = getCurrentUser();
        if (currentUser != null && roles != null) {
            return Arrays.asList(roles).contains(currentUser.isManager());
        } else {
            return false;
        }
    }
}