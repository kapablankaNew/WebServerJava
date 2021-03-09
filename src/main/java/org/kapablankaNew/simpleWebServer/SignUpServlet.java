package org.kapablankaNew.simpleWebServer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        super();
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile profile = new UserProfile(login, password);
        try {
            accountService.addUser(profile);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        resp.setStatus(200);
    }
}
