package org.kapablankaNew.simpleWebServer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AccountService {
    private final Connection connection;

    private final String URL;
    private final String drv;
    private final String user;
    private final String password;

    public AccountService() throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException,
            SQLException, IOException {

        Properties prop = new Properties();
        prop.load(new FileReader("database.properties"));
        URL = prop.getProperty("URL");
        drv = prop.getProperty("driver");
        user = prop.getProperty("user");
        password = prop.getProperty("password");

        Driver driver = (Driver)Class.forName(drv).getDeclaredConstructor().newInstance();
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(URL, user, password);
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
