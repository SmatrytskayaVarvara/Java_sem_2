package sample;

import java.io.*;
import java.net.*;
import java.awt.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class Controller {

    Socket ClientSock = null;
    BufferedReader in;
    BufferedWriter out;

    @FXML
    private Button ConnectButton;

    @FXML
    private TextField IPAddressField;

    @FXML
    private TextField PortField;

    @FXML
    private Button SendButton;

    @FXML
    private TextField SendingDataField;

    @FXML
    private TextArea ResultArea;

    @FXML
    void initialize()
    {
        IPAddressField.setText("localhost");
        PortField.setText("8000");


        ConnectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                try
                {
                    if (PortField.getText().compareTo("8000")!=0){
                        System.out.println("Введен неверный порт");
                    }
                    else {
                        ClientSock = new Socket(InetAddress.getByName(IPAddressField.getText()),
                                Integer.parseInt(PortField.getText()));
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        });

        SendButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(ClientSock == null)
                    return;
                try {
                    // чтение сообщения с сервера
                    in = new BufferedReader(new InputStreamReader(ClientSock.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(ClientSock.getOutputStream()));
                    String data = SendingDataField.getText();
                    out.write(data + "\n");
                    if (data.compareTo("disconnect") == 0){
                        out.flush();
                    }
                    else {
                        out.flush();
                        String result = in.readLine();
                        ResultArea.setText(result);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        /*SendButton.setOnAction(event2 -> {
            if(ClientSock == null)
                return;
            try {
                // чтение сообщения с сервера
                in = new BufferedReader(new InputStreamReader(ClientSock.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(ClientSock.getOutputStream()));
                String data = SendingDataField.getText();
                out.write(data + "\n");
                out.flush();
                String result = in.readLine();
                ResultArea.setText(result);
            } catch (IOException e){
                e.printStackTrace();
            }
        });*/


    }
}
