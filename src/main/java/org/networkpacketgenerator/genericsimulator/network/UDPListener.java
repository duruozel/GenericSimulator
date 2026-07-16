package org.networkpacketgenerator.genericsimulator.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListener extends BaseListener{
    private DatagramSocket socket;
    public UDPListener(int port){
        super(port);
    }

    @Override
    public void startListening()
    {
        this.isRunning=true;
        this.listenerThread=new Thread(() ->{
            try {
                socket=new DatagramSocket(port);
                System.out.println("[UDP LISTENER] " + port + " portunu dinliyor");

                byte[] buffer = new byte[1024];
                while (isRunning) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    int length = packet.getLength();
                    System.out.println("[UDP LISTENER] veri alindi. Boyut: " + length + " byte.");
                    for (int i = 0; i < length; i++) {
                        System.out.print(String.format("0x%02X ", buffer[i]));
                    }
                    System.out.println();
                }
            } catch (Exception e) {
                if (isRunning) {
                    System.err.println("UDP dinleme hatasi: " + e.getMessage());
                }
            }


    });
    this.listenerThread.start();
    }

    @Override
    public void stopListening() {
        super.stopListening();
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        System.out.println("[UDP LISTENER] dinleyici durduruldu.");
    }
}
