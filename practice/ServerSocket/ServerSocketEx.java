import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketEx {

    static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String remoteSocketInfo = clientSocket.getRemoteSocketAddress().toString();
                String localSocketInfo = clientSocket.getLocalSocketAddress().toString();
                String messageHeader = "[" + remoteSocketInfo + " -> " + localSocketInfo + "]";

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(messageHeader + " " + inputLine);

                    out.println(inputLine); // echo back

                    if ("bye".equalsIgnoreCase(inputLine)) {
                        System.out.println("클라이언트 연결 종료.");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        final int PORT = 6543;

        try (ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
             ServerSocket server = new ServerSocket(PORT)) {

            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("클라이언트가 연결되었습니다.");

                executorService.submit(new ClientHandler(clientSocket));
            }

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
