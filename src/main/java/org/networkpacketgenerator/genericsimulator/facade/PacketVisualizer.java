package org.networkpacketgenerator.genericsimulator.facade;

import org.networkpacketgenerator.genericsimulator.model.PacketStructure;
import org.networkpacketgenerator.genericsimulator.util.PacketConverter;

import java.util.HexFormat;

public class PacketVisualizer {
    private PacketVisualizer(){}

    public static byte[] toNetworkBytes(PacketStructure structure){

        byte[] convertedBytes = PacketConverter.toByteArray(structure);

        String hexResult = HexFormat.ofDelimiter(" ")
                .withUpperCase()
                .formatHex(convertedBytes);
        System.out.println("\nPaketin gonderilmek icin uretilen hex hali:\n" + hexResult + "\n");

        return convertedBytes;
    }
}
