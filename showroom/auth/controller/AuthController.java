package showroom.auth.controller;

import showroom.auth.model.User;
import showroom.auth.dao.UserDAO;
import com.mongodb.MongoException;

public class AuthController {
    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(String name, String phone, String address, String email, String password) {
        try {
            if (userDAO.isEmailRegistered(email)) {
                throw new IllegalArgumentException("Email is already registered");
            }

            User user = new User(name, phone, address, email, password);
            return userDAO.registerUser(user);

        } catch (MongoException e) {
            System.err.println("MongoDB error during registration: " + e.getMessage());
            e.printStackTrace();
            return false;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String password) {
        try {
            return userDAO.authenticateUser(email, password);

        } catch (MongoException e) {
            System.err.println("MongoDB error during login: " + e.getMessage());
            e.printStackTrace();
            return null;

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean validatePhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }
}