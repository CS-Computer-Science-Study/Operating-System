
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        final int port = 6543;

        try (DatagramSocket clientSocket = new DatagramSocket()) {

            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] receiveData = new byte[1024];

            String userInput;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while ((userInput = stdIn.readLine()) != null) {
                byte[] sendData = userInput.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                clientSocket.send(sendPacket);

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("서버로부터 받은 메시지: " + modifiedSentence);

                if ("bye".equalsIgnoreCase(userInput)) {
                    System.out.println("연결을 종료합니다.");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
