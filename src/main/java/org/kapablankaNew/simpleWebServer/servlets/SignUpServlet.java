package org.kapablankaNew.simpleWebServer.servlets;

import org.kapablankaNew.simpleWebServer.dao.UsersDAO;
import org.kapablankaNew.simpleWebServer.entities.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    private final UsersDAO dao;

    public SignUpServlet(UsersDAO dao) {
        super();
        this.dao = dao;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            if (dao.haveUser(login)) {
                resp.setStatus(401);
            } else {
                UserProfile profile = new UserProfile(login, password);
                dao.addUSer(profile);
                resp.setStatus(200);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
