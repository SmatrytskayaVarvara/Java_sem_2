package sample;
import java.lang.StringBuffer;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.*;
import java.io.*;
public class Server {

    private static Socket ClientSock;
    private static ServerSocket ServerSock;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            ServerSock = new ServerSocket(8000);
            System.out.println("Ожидание подключения...");
            ClientSock = ServerSock.accept();
            System.out.println("Клиент подключен!");
            in = new BufferedReader(new InputStreamReader(ClientSock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(ClientSock.getOutputStream()));

            while(true) {
                String data = in.readLine();
                if (data.compareTo("disconnect") == 0){
                    System.out.println("Клиент отключен");
                    break;
                }
                String result;
                char[] symbols = data.toCharArray();
                for (int i = 0; i < symbols.length; i++) {
                    if ((i + 1) % 4 == 0) {
                        symbols[i] = '%';
                    }
                }
                result = new String(symbols);
                out.write(result + "\n");
                out.flush();
            }
            out.flush();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
