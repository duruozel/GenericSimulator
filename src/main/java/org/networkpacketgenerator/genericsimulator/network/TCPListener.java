package org.networkpacketgenerator.genericsimulator.network;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TCPListener extends BaseListener{
    private ServerSocket serverSocket;
    private final Consumer<byte[]> packetHandler;

    public TCPListener(int port, Consumer<byte[]> packetHandler){
        super(port);
        this.packetHandler=packetHandler;
    }

    @Override
    public void startListening() {
        this.isRunning = true;
        this.listenerThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("[TCP LISTENER] " + port + " portunda sunucu acildi");

                while (isRunning) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[TCP LISTENER] baglandi: " + clientSocket.getRemoteSocketAddress());

                    try (InputStream input = clientSocket.getInputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = input.read(buffer)) != -1) {
                            byte[] receivedData = new byte[bytesRead];
                            System.arraycopy(buffer, 0, receivedData, 0, bytesRead);

                            if(packetHandler!=null){
                                packetHandler.accept(receivedData);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("istemci verisi okunurken hata: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                if (isRunning) {
                    System.err.println("TCP dinleme hatasi: " + e.getMessage());
                }
            }
        });
        this.listenerThread.start();
    }

    @Override
    public void stopListening() {
        super.stopListening();
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (Exception e) {
            System.err.println("ServerSocket kapatilamadi " + e.getMessage());
        }
        System.out.println("[TCP LISTENER] sunucu durduruldu.");
    }

    }

