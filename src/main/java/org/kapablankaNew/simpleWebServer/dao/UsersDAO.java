package org.kapablankaNew.simpleWebServer.dao;

import org.kapablankaNew.simpleWebServer.entities.UserProfile;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class UsersDAO {
    private final Connection connection;

    public UsersDAO() throws IOException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        Properties prop = new Properties();
        prop.load(new FileReader("database.properties"));
        String URL = prop.getProperty("URL");
        String drv = prop.getProperty("driver");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        Driver driver = (Driver) Class.forName(drv).getDeclaredConstructor().newInstance();
        DriverManager.registerDriver(driver);
        connection = DriverManager.getConnection(URL, user, password);
        createTable();
    }

    private void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS users (login varchar(256) PRIMARY KEY, password varchar(256))";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public boolean haveUser(String login) throws SQLException {
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

    public void addUSer(UserProfile profile) throws SQLException {
        String query = "INSERT INTO users (login, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, profile.getLogin());
            stmt.setString(2, profile.getPassword());
            stmt.executeUpdate();
        }
    }

    public UserProfile getUserByLogin(String login) throws SQLException {
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
