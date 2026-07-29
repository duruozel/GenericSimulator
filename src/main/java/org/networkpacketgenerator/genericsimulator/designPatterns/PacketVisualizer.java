package org.networkpacketgenerator.genericsimulator.designPatterns;

import org.networkpacketgenerator.genericsimulator.model.PacketStructure;
import org.networkpacketgenerator.genericsimulator.util.HexFormatterUtil;
import org.networkpacketgenerator.genericsimulator.util.PacketConverter;

public class PacketVisualizer {
    private PacketVisualizer(){}

    public static byte[] toNetworkBytes(PacketStructure structure){

        byte[] convertedBytes = PacketConverter.toByteArray(structure);

        String hexResult = HexFormatterUtil.bytesToHex(convertedBytes);

        System.out.println("\nPaketin gonderilmek icin uretilen hex hali:\n" + hexResult + "\n");

        return convertedBytes;
    }
}
