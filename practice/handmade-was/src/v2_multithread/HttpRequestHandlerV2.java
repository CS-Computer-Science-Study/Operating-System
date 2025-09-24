package v2_multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpRequestHandlerV2 implements Runnable {

    private final Socket socket;

    public HttpRequestHandlerV2(Socket socket) {
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

            String requestString = requestToString(reader);
            if (requestString.contains("/favicon.ico")) {
                return;
            }

            System.out.println("HTTP 요청 정보 출력");
            System.out.println(requestString);

            if (requestString.startsWith("GET /apple")) {
                apple(writer);
            }
            else if (requestString.startsWith("GET /samsung")) {
                samsung(writer);
            }
            else if (requestString.startsWith("GET /search")) {
                search(writer, requestString);
            }
            else if (requestString.startsWith("GET / ")) {
                home(writer);
            }
            else {
                notFound(writer);
            }
        }
    }

    private String requestToString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private void apple(PrintWriter writer) {
        writer.println("HTTP/1.1 200 ok");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>Apple Store</h1>");
        writer.flush();
    }

    private void samsung(PrintWriter writer) {
        writer.println("HTTP/1.1 200 ok");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>Samsung Store</h1>");
        writer.flush();
    }

    private void search(PrintWriter writer, String requestString) {
        // "GET /search?model=hello HTTP/1.1"
        String prefix = "/search?model=";
        int startIndex = requestString.indexOf(prefix);
        int endIndex = requestString.indexOf(" ", startIndex + prefix.length());
        String query = requestString.substring(startIndex + prefix.length(), endIndex);
        String decode = URLDecoder.decode(query, StandardCharsets.UTF_8);
        writer.println("HTTP/1.1 200 ok");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>Search</h1>");
        writer.println("<ul>");
        writer.println("<li>query: " + query + "</li>");
        writer.println("<li>decode: " + decode + "</li>");
        writer.println("</ul>");
        writer.flush();
    }

    private void home(PrintWriter writer) {
        writer.println("HTTP/1.1 200 ok");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>Welcome Store!</h1>");
        writer.println("<form method='GET' action='/search'>");
        writer.println("<input type='text' placeholder = '제품명 입력' name='model'>");
        writer.println("<input type='submit' value='검색하기'>");
        writer.println("</form>");
        writer.println("<h3><a href='/apple'>Apple</a></h3>");
        writer.println("<h3><a href='/samsung'>Samsung</a></h3>");
        writer.flush();
    }

    private void notFound(PrintWriter writer) {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>404 페이지를 찾을 수 없습니다.</h1>");
        writer.flush();
    }
}
