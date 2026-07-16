package org.networkpacketgenerator.genericsimulator.network;

public abstract class BaseListener {
    protected final int port;
    protected boolean isRunning=false;
    protected Thread listenerThread;

    public BaseListener(int port){
        this.port=port;
    }

    public abstract void startListening();

    public void stopListening(){
        this.isRunning=false;
        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
        }
    }


}
