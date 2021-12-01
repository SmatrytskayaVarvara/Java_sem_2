package Server;

import javafx.fxml.FXML;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.LinkedList;

public class Server {
    @FXML
    public static LinkedList<ServerThread> serverThreads = new LinkedList<>();

    private ServerSocket serverSocket;
    private int count;
    private static Statement statement;
    private static Connection connection;

    public void start() throws IOException {
        try {
            setServerSocket(new ServerSocket(1024));
            System.out.println("Server started");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "12q23w34e");
            statement = connection.createStatement();// объект для исполнения команд

            while(true){
                Socket clientSocket = serverSocket.accept();
                setCount(getCount()+1);

                try {
                    serverThreads.add(new ServerThread(clientSocket));
                }catch (IOException ex){
                    clientSocket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            serverSocket.close();
            System.out.println("Server stopped");
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    class ServerThread extends Thread{
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ServerThread(Socket socket) throws IOException {
            this.socket = socket;
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            start();
        }
        public void getAll() throws SQLException, IOException {// метод получения данных из бд
            ResultSet set;
            int id;
            String f_name;
            String l_name;
            String addr;
            String tel;
            String mail;
            String result = "";

            String query = "SELECT * FROM address_book";
            synchronized (statement) {
                set = statement.executeQuery(query);
            }
            while(set.next()) {
                id = set.getInt("contact_id");
                f_name = set.getString("first_name");
                l_name = set.getString("last_name");
                addr = set.getString("address");
                tel = set.getString("telephone");
                mail = set.getString("email");
                result += String.valueOf(id)+ "/" +f_name+ "/" + l_name + "/" + addr + "/" + tel + "/" + mail + "/";
            }
            System.out.println(result);
            outputStream.write(result.getBytes());
        }

    public void editContact(String[] queries) throws SQLException, IOException {
        String edit="UPDATE address_book SET contact_id = '"+Integer.parseInt(queries[1])+"', first_name = '"+queries[2]+"', last_name = '"+
                queries[3]+"',address = '"+queries[4]+"', telephone = '"+queries[5]+"', email = '"+ queries[6]+"' WHERE contact_id = '"+queries[1]+"'";

        System.out.println(edit);
        statement.executeUpdate(edit);
        getAll();
    }
    public void addContact(String[] queries) throws SQLException, IOException {
        String sq_str_insert="INSERT INTO address_book VALUES ('"+Integer.parseInt(queries[1])+"','"+queries[2]+"','"+
                queries[3]+"','"+queries[4]+"','"+queries[5]+"','"+queries[6]+"')";

        statement.executeUpdate(sq_str_insert);
            System.out.println(sq_str_insert);
            getAll();

    }

    public void deleteContact(String[] queries) throws SQLException, IOException {

        String del="DELETE FROM address_book WHERE contact_id = '"+queries[1]+"'";
        statement.executeUpdate(del);
        System.out.println(del);
        getAll();
    }

    @Override
    public void run() {
        String query;
        byte[] bytes;
        try {
            getAll();
            while(true){
                bytes = new byte[10000];
                inputStream.read(bytes);
                query = new String(bytes);
                String[] queries = query.split("/");
                if(queries[0].equals("Exit")){
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    break;
                }
                else if(queries[0].equals("Add")) {
                    System.out.println(queries[1]);
                    addContact(queries);
                    System.out.println(queries[1]);
                }
                else if(queries[0].equals("Del"))
                    deleteContact(queries);
                else if(queries[0].equals("Edit"))
                    editContact(queries);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

}
