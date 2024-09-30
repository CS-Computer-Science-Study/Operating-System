
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

    public static void main(String[] args) {
        final int port = 6543;

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String remoteSocketInfo = receivePacket.getSocketAddress().toString();
                String messageHeader = "[" + remoteSocketInfo + " -> :" + serverSocket.getLocalPort() + "]";

                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(messageHeader + " " + sentence);

                byte[] sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);  // 클라이언트로 전송

                if ("bye".equalsIgnoreCase(sentence)) {
                    System.out.println("클라이언트 연결 종료.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
