package org.networkpacketgenerator.genericsimulator.network;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPListener extends BaseListener{
    private ServerSocket serverSocket;
    public TCPListener(int port){
        super(port);
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
                        int data;
                        while ((data = input.read()) != -1) {
                            System.out.println("[TCP LISTENER] veri yakalandi: " + String.format("0x%02X", data));
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

