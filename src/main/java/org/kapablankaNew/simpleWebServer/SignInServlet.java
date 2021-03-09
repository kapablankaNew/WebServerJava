package org.kapablankaNew.simpleWebServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        super();
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            if (accountService.isUserAuthorized(login)) {
                UserProfile profile = accountService.getUserProfile(login);
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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
