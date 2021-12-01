package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    @FXML
    private TextField TextField1;

    @FXML
    private TextField TextField2;

    @FXML
    private TextField TextField3;

    @FXML
    private TextArea TextArea;

    @FXML
    private Button SendButton;

    @FXML
    void initialize() {
        SendButton.setOnAction(event -> {
            try {
                doGUI();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void doGUI() throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket(1236);
            DatagramPacket packet;
            InetAddress address = InetAddress.getByName("localhost");
            byte[] buf;



            String text = (TextField1.getText() + '\n')+(TextField2.getText() + '\n')+(TextField3.getText() + '\n');
            byte[] overallBuf = text.getBytes();
            int overallLength = overallBuf.length;
            packet = new DatagramPacket(overallBuf, overallLength, address, 1235);
            socket.send(packet);

            buf = new byte[100];
            packet = new DatagramPacket(buf, 100);
            socket.receive(packet);
            TextArea.appendText(new String(packet.getData()));
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
