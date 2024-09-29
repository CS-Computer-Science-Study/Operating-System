import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketEx {
    public static void main(String[] args) {

        final String host = "127.0.0.1";
        final int port = 6543;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("서버에 접속되었습니다 : "+ socket.getRemoteSocketAddress());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String userInput;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);

                System.out.println("서버로부터 받은 메시지: " + in.readLine());

                if ("bye".equalsIgnoreCase(userInput)) {
                    System.out.println("연결을 종료합니다.");
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Cannot found the host at " + host);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

