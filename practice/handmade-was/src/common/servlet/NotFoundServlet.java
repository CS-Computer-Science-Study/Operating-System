package common.servlet;

import common.HttpRequest;
import common.HttpResponse;
import common.HttpServlet;

public class NotFoundServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setStatusCode(404);
        response.writeBody("<h1>Not Found</h1>");
    }
}
