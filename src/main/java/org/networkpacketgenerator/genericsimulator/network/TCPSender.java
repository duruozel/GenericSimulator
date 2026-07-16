package org.networkpacketgenerator.genericsimulator.network;

import java.io.OutputStream;
import java.net.Socket;

public class TCPSender extends BaseSender {

    public TCPSender(String ip, int port){
        super(ip,port);
    }

    @Override
    public void send(byte rawByte){
        try (Socket socket = new Socket(ip, port);
             OutputStream output = socket.getOutputStream()) {

            output.write(rawByte);
            output.flush();
            System.out.println("[TCP SENDER] " + String.format("0x%02X", rawByte) + " verisi gonderildi!");
        } catch (Exception e) {
            System.err.println("TCP gonderme hatasi: " + e.getMessage());
        }

    }

}
