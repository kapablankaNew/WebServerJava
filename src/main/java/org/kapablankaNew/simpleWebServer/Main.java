package org.kapablankaNew.simpleWebServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        SignInServlet signInServlet = new SignInServlet(accountService);
        SignUpServlet signUpServlet = new SignUpServlet(accountService);

        accountService.addUser(new UserProfile("admin2", "password2"));

        //Server - main class of the library jetty
        //now address: http://localhost:8080/
        Server server = new Server(8080);

        //ServletContextHandler - class of the library jetty
        //It is servlet container, he connect URL with the corresponding servlet
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");

        server.setHandler(contextHandler);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
