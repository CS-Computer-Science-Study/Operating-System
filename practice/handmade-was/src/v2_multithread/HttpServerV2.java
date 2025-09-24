package v2_multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerV2 {

    private final int port;
    private final ExecutorService es = Executors.newFixedThreadPool(10);

    public HttpServerV2(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("서버 시작 port: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                es.submit(new HttpRequestHandlerV2(socket));
            }
        }
    }
}
