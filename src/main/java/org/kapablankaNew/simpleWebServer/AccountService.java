package org.kapablankaNew.simpleWebServer;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Connection connection;

    String URL = "jdbc:postgresql://localhost:5432/webserver";
    String drv = "org.postgresql.Driver";

    public AccountService() throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException,
            SQLException {
        Driver driver = (Driver)Class.forName(drv).getDeclaredConstructor().newInstance();
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(URL, "postgres", "postgres");
    }

    public void addUser(UserProfile userProfile) throws SQLException {
        if (!isUserAuthorized(userProfile.getLogin())) {
            String query = "INSERT INTO users VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)){
                stmt.setString(1, userProfile.getLogin());
                stmt.setString(2, userProfile.getPassword());
                stmt.executeUpdate();
            }
        }
    }

    public boolean isUserAuthorized(String login) throws SQLException {
        String query = "SELECT EXISTS (SELECT * FROM users WHERE login = ?)";
        boolean result;
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getBoolean("exists");
        }
        return result;
    }

    public UserProfile getUserProfile(String login) throws SQLException {
        String query = "SELECT * FROM users WHERE login = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.isLast()){
                return null;
            }
            rs.next();
            return new UserProfile(rs.getString("login"),
                    rs.getString("password"));
        }
    }
}
