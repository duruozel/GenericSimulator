package org.networkpacketgenerator.genericsimulator.network;

public abstract class BaseSender {
    protected final String ip;
    protected final int port;

    public BaseSender(String ip, int port){
        this.ip=ip;
        this.port=port;
    }

    public abstract void send(byte[] rawByte);
}
