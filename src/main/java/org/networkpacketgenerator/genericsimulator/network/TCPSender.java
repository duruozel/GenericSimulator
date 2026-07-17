package org.networkpacketgenerator.genericsimulator.network;

import java.io.OutputStream;
import java.net.Socket;

public class TCPSender extends BaseSender {

    public TCPSender(String ip, int port){
        super(ip,port);
    }

    @Override
    public void send(byte[] rawByte){
        try (Socket socket = new Socket(ip, port);
             OutputStream output = socket.getOutputStream()) {

            output.write(rawByte);
            output.flush();

            System.out.println("TCP SENDER: " + rawByte.length + " byte boyutundaki paket basariyla gonderildi.");

        } catch (Exception e) {
            System.err.println("TCP gonderme hatasi: " + e.getMessage());
        }

    }

}
