package sample;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ServerUDP {

    public static void main(String[] args) throws IOException {
        new ServerUDP();
    }
    private ServerUDP() throws SocketException {
        DatagramSocket socket = new DatagramSocket(1235);
        try {
            byte[] buf = new byte[100];
            DatagramPacket packet = new DatagramPacket(buf, 100);
            int a, b, c;
//            socket.receive(packet);
//            String res = new String(packet.getData());
//            res = res.substring(0, res.indexOf('\n'));
//            a = Integer.parseInt(res);

//            socket.receive(packet);
//            res = new String(packet.getData());
//            res = res.substring(0, res.indexOf('\n'));
//            b = Integer.parseInt(res);
            
//            socket.receive(packet);
//            res = new String(packet.getData());
//            res = res.substring(0, res.indexOf('\n'));
//            c = Integer.parseInt(res);
            socket.receive(packet);
            String res = new String(packet.getData());
            String delimiter = "\n";
            String[] subStr = res.split(delimiter);
//            res = res.substring(0, res.indexOf('\n'));
            a = Integer.parseInt(subStr[0]);
            b = Integer.parseInt(subStr[1]);
            c = Integer.parseInt(subStr[2]);

            double[] sum = {0, 0};
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    for (double n = a; n <= b; n++) {
                        sum[0] += Math.pow(n-1, 2);
                    }
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    for (double n = b; n <= c; n++) {
                        sum[1] += (2*n/ 7*n+1);
                    }
                }
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();

            String result = Double.toString(sum[0] + sum[1]);
            byte[] toSend = result.getBytes();
            packet = new DatagramPacket(toSend, toSend.length, InetAddress.getByName("localhost"), 1236);
            socket.send(packet);
        } catch (Exception e) {
            socket.close();
        }
    }
}
