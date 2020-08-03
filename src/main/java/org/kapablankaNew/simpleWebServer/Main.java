package org.kapablankaNew.simpleWebServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        SignInServlet signInServlet = new SignInServlet(accountService);
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");

        Server server = new Server(8080);

        server.setHandler(contextHandler);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
