package org.networkpacketgenerator.genericsimulator.util;

import java.math.BigInteger;

public class PacketDecoder {
    private PacketDecoder(){}

    public static String decode(byte[] bytes){
        if(bytes==null || bytes.length==0){
            System.out.println("Cozumlencek ag verisi bos.");
        }

        return new BigInteger(1,bytes).toString();
    }
}
