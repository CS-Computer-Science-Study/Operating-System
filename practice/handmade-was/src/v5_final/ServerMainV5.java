package v5_final;

import common.HttpServer;
import common.HttpServlet;
import common.ServletManager;
import common.repository.ModelRepository;
import common.servlet.DiscardServlet;
import v5_final.controller.RedirectController;
import v5_final.controller.SearchController;
import v5_final.controller.StoreController;

import java.io.IOException;
import java.util.List;

public class ServerMainV5 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ModelRepository modelRepository = new ModelRepository();

        List<Object> controllers = List.of(
                new StoreController(modelRepository),
                new SearchController(modelRepository),
                new RedirectController()
        );
        HttpServlet annotationServlet = new AnnotationServlet(controllers);

        ServletManager servletManager = new ServletManager();
        servletManager.setDefaultServlet(annotationServlet);
        servletManager.setNotFoundErrorServlet(new CustomNotFoundServlet());
        servletManager.add("/favicon.ico", new DiscardServlet());

        HttpServer server = new HttpServer(PORT, servletManager);
        server.start();
    }
}
