package v1_basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServerV1 {

    private final int port;

    public HttpServerV1(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("서버 시작 port: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                process(socket);
            }
        }
    }

    private void process(Socket socket) throws IOException {
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, StandardCharsets.UTF_8)) {

            String requestString = requestToString(reader);
            if (requestString.contains("/favicon.ico")) {
                // GET /favicon.ico HTTP/1.1
                return;
            }

            System.out.println("HTTP 요청 정보 출력");
            System.out.println(requestString);
            responseToClient(writer);
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

    private void responseToClient(PrintWriter writer) {
        // 웹 브라우저에 전달하는 내용.
        // 원칙적으로 Content-Length를 계산해서 전달해야 하지만, 예제를 단순하게 설명하기 위해 생략.
        // 브라우저에 따라 실행이 안 될 수도 있다.
        writer.println("HTTP/1.1 200 ok");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println();
        writer.println("<h1>Hello World</h1>");
        writer.flush();
    }
}
