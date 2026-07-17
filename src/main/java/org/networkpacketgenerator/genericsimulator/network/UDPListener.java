package org.networkpacketgenerator.genericsimulator.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.function.Consumer;

public class UDPListener extends BaseListener{
    private DatagramSocket socket;
    private final Consumer<byte[]> packetHandler;

    public UDPListener(int port, Consumer<byte[]> packetHandler){
        super(port);
        this.packetHandler = packetHandler;
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
                    byte[] receivedData = new byte[length];
                    System.arraycopy(buffer, 0, receivedData, 0);

                    if (packetHandler != null) {
                        packetHandler.accept(receivedData);
                    }
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
