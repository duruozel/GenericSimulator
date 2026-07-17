package org.networkpacketgenerator.genericsimulator.network;

import java.net.*;

public class UDPSender extends BaseSender{
    public UDPSender(String ip, int port){
        super(ip, port);
    }

    @Override
    public void send(byte[] rawByte){

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(ip);

            DatagramPacket packet = new DatagramPacket(rawByte, rawByte.length, address, port);
            socket.send(packet);

            System.out.println("[UDP SENDER] " + rawByte.length + " byte boyutunda paket gonderildi!");
        } catch (Exception e) {
            System.err.println("UDP gonderme hatasi: " + e.getMessage());
        }
    }
    }


