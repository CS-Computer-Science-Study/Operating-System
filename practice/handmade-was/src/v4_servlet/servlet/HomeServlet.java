package v4_servlet.servlet;

import common.HttpRequest;
import common.HttpResponse;
import common.HttpServlet;

public class HomeServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>Welcome Store!</h1>");
        response.writeBody("<form method='GET' action='/search'>");
        response.writeBody("<input type='text' placeholder = '제품명 입력' name='model'>");
        response.writeBody("<input type='submit' value='검색하기'>");
        response.writeBody("</form>");
        response.writeBody("<h3><a href='/apple'>Apple</a></h3>");
        response.writeBody("<h3><a href='/samsung'>Samsung</a></h3>");
    }
}
