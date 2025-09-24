package v4_servlet;

import common.HttpServer;
import common.ServletManager;
import common.repository.ModelRepository;
import common.servlet.DiscardServlet;
import v4_servlet.servlet.AppleServlet;
import v4_servlet.servlet.HomeServlet;
import v4_servlet.servlet.SamsungServlet;
import v4_servlet.servlet.SearchServlet;

import java.io.IOException;

public class ServerMainV4 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ModelRepository modelRepository = new ModelRepository();

        ServletManager servletManager = new ServletManager();
        servletManager.add("/", new HomeServlet());
        servletManager.add("/apple", new AppleServlet(modelRepository));
        servletManager.add("/samsung", new SamsungServlet(modelRepository));
        servletManager.add("/search", new SearchServlet(modelRepository));
        servletManager.add("/favicon.ico", new DiscardServlet());

        HttpServer server = new HttpServer(PORT, servletManager);
        server.start();
    }
}
