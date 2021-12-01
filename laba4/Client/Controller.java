package Client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TableView tableView;
    public TextField ipField;
    public TextField portField;
    public TextField timeField;

    public Button connectButton;
    public Button disconnectButton;
    public Button showTimeButton;

    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;
    public TextField contact_idField;
    public TextField first_nameField;
    public TextField last_nameField;
    public TextField addressField;
    public TextField telephoneField;
    public TextField emailField;
    public Button addButton;
    public Button deleteButton;
    public Button editButton;
    public boolean exit = false;

    @FXML
    public ObservableList<Contact> addingData = FXCollections.observableArrayList();
    @FXML
    public TableColumn<Contact, String> contact_idCol;
    @FXML
    public TableColumn<Contact, String> first_nameCol;
    @FXML
    public TableColumn<Contact, String> last_nameCol;
    @FXML
    public TableColumn<Contact, String> addressCol;
    @FXML
    public TableColumn<Contact, String> telephoneCol;
    @FXML
    public TableColumn<Contact, String> emailCol;

    public void initTable(){

    }

    public void onShowTimeClick(ActionEvent event) throws IOException {
        timeField.setText(LocalTime.now().toString());
    }

    public void onConnectClick(ActionEvent event) {
        if (!ipField.getText().equals("") && !portField.getText().equals("")) {
            try {
                socket = new Socket(InetAddress.getByName(ipField.getText()), Integer.parseInt(portField.getText()));
                connectButton.setDisable(true);
                disconnectButton.setDisable(false);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                Thread read = new Thread(new ReadMessage());
                read.start();
                TableView.TableViewSelectionModel<Contact> selectionModel = tableView.getSelectionModel();
                selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    contact_idField.setText(newValue.getContact_id());
                    first_nameField.setText(newValue.getFirst_name());
                    last_nameField.setText(newValue.getLast_name());
                    addressField.setText(newValue.getAddress());
                    telephoneField.setText(newValue.getTelephone());
                    emailField.setText(newValue.getEmail());
                });
                timeField.setText(LocalTime.now().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDisconnectClick(ActionEvent event) throws IOException {
        String query = "Exit";
        exit = true;
        outputStream.write(query.getBytes());
        inputStream.close();
        outputStream.close();
        disconnectButton.setDisable(true);
        connectButton.setDisable(false);
        socket.close();
    }

    public void onAddButtonClick(ActionEvent event) throws IOException {

        Contact contact = new Contact();
        contact.setContact_id(contact_idField.getText());
        contact.setFirst_name(first_nameField.getText());
        contact.setLast_name(last_nameField.getText());
        contact.setAddress(addressField.getText());
        contact.setTelephone(telephoneField.getText());
        contact.setEmail(emailField.getText());
        String query = "Add/" + contact.toString();
        System.out.println(query);
        outputStream.write(query.getBytes());
    }

    public void onDeleteButtonClick(ActionEvent event) throws IOException {
        if(!contact_idField.getText().equals("")) {
            String query = "Del/" + contact_idField.getText()+"/";
            outputStream.write(query.getBytes());
            //addingData.remove(tableView.getSelectionModel().getSelectedItem());
        }
    }

    public void onEditButtonClick(ActionEvent event) throws IOException {
        if(!contact_idField.getText().equals("")){
            Contact contact = new Contact();
            contact.setContact_id(contact_idField.getText());
            contact.setFirst_name(first_nameField.getText());
            contact.setLast_name(last_nameField.getText());
            contact.setAddress(addressField.getText());
            contact.setTelephone(telephoneField.getText());
            contact.setEmail(emailField.getText());
            String query = "Edit/" + contact.toString();
            outputStream.write(query.getBytes());
    }
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        contact_idCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("contact_id"));

        first_nameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("first_name"));

        last_nameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("last_name"));

        addressCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));

        telephoneCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("telephone"));

        emailCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));

        tableView.setItems(addingData);

//        showTimeButton.setOnAction(event -> {
//            timeField.setText(LocalTime.now().toString());
//        });
    }

    class ReadMessage extends Thread {

        @Override
        public void run() {
            while(true){
                if(exit)
                    break;
                String data="";
                int a=0;
                try {
                    byte[] bytes = new byte[10000];
                    if (inputStream.read(bytes) > 0)
                        data = new String(bytes);

                    for (int i=0; i<data.length(); i++)
                    {
                        if (data.charAt(i) == '/')
                            a++;
                    }

                    String[] result = data.split("/");
                    System.out.println(result.length);
                    addingData.clear();
                    for(int i = 0; i < a;i += 6){
                        addingData.add(new Contact(result[i], result[i+1], result[i+2], result[i+3], result[i+4], result[i+5]));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
