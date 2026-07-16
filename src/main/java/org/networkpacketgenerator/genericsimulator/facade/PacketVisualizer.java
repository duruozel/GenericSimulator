package org.networkpacketgenerator.genericsimulator.facade;

import org.networkpacketgenerator.genericsimulator.model.PacketStructure;
import org.networkpacketgenerator.genericsimulator.util.PacketConverter;
import java.util.HexFormat;

public class PacketVisualizer {
    private PacketVisualizer(){}

    public static String generateHexRepresentation(PacketStructure structure){

        byte[] convertedBytes = PacketConverter.toByteArray(structure);

      return HexFormat.ofDelimiter(" ")
                    .withUpperCase()
                    .formatHex(convertedBytes);
    }
}
