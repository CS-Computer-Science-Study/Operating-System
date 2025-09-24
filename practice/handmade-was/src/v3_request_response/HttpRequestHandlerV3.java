package v3_request_response;

import common.HttpRequest;
import common.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpRequestHandlerV3 implements Runnable {

    private final Socket socket;

    public HttpRequestHandlerV3(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void process() throws IOException {
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, StandardCharsets.UTF_8)) {

            HttpRequest request = new HttpRequest(reader);
            HttpResponse response = new HttpResponse(writer);

            if (request.getPath().equals("/favicon.ico")) {
                return;
            }

            System.out.println("HTTP 요청 정보 출력");
            System.out.println(request);

            if (request.getPath().equals("/apple")) {
                apple(response);
            }
            else if (request.getPath().equals("/samsung")) {
                samsung(response);
            }
            else if (request.getPath().equals("/search")) {
                search(request, response);
            }
            else if (request.getPath().equals("/")) {
                home(response);
            }
            else {
                notFound(response);
            }
            response.flush();
        }
    }

    private void apple(HttpResponse response) {
        response.writeBody("<h1>Apple Store</h1>");
    }

    private void samsung(HttpResponse response) {
        response.writeBody("<h1>Samsung Store</h1>");
    }

    private void search(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>검색 결과</h1>");

        String query = request.getQueryParameter("model");
        response.writeBody("query: " + query);
    }

    private void home(HttpResponse response) {
        response.writeBody("<h1>Welcome Store!</h1>");
        response.writeBody("<form method='GET' action='/search'>");
        response.writeBody("<input type='text' placeholder = '제품명 입력' name='model'>");
        response.writeBody("<input type='submit' value='검색하기'>");
        response.writeBody("</form>");
        response.writeBody("<h3><a href='/apple'>Apple</a></h3>");
        response.writeBody("<h3><a href='/samsung'>Samsung</a></h3>");
    }

    private void notFound(HttpResponse response) {
        response.setStatusCode(404);
        response.writeBody("<h1>404 페이지를 찾을 수 없습니다.</h1>");
    }
}
