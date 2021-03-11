package org.kapablankaNew.simpleWebServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.kapablankaNew.simpleWebServer.dao.UsersDAO;
import org.kapablankaNew.simpleWebServer.servlets.SignInServlet;
import org.kapablankaNew.simpleWebServer.servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        UsersDAO dao = new UsersDAO();
        SignInServlet signInServlet = new SignInServlet(dao);
        SignUpServlet signUpServlet = new SignUpServlet(dao);

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
