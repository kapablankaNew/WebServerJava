package org.kapablankaNew.simpleWebServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
    private final String login = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String value = req.getParameter("key");
        //String answer = "<!DOCTYPE html>\n" +
        //      "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
        //        "<head>" + value + "</head>\n" +
        //        "</html>";
        resp.getWriter().println(value);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
