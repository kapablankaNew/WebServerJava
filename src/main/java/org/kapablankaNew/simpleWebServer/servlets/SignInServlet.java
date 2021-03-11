package org.kapablankaNew.simpleWebServer.servlets;

import org.kapablankaNew.simpleWebServer.dao.UsersDAO;
import org.kapablankaNew.simpleWebServer.entities.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SignInServlet extends HttpServlet {

    private final UsersDAO dao;

    public SignInServlet(UsersDAO dao) {
        super();
        this.dao = dao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            if (dao.haveUser(login)) {
                UserProfile profile = dao.getUserByLogin(login);
                if (profile.getPassword().equals(password)) {
                    resp.setStatus(200);
                    resp.getWriter().println("Authorized: " + login);
                } else {
                    resp.setStatus(401);
                    resp.getWriter().println("Unauthorized");
                }

            } else {
                resp.setStatus(401);
                resp.getWriter().println("Unauthorized");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
