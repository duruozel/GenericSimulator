package org.networkpacketgenerator.genericsimulator.util;

public class HexFormatterUtil {
    public static String bytesToHex (byte[] bytes){
        if(bytes==null){
            return "";
        }
        return java.util.HexFormat.ofDelimiter(" ").withUpperCase().formatHex(bytes);
    }
}
