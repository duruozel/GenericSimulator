package org.networkpacketgenerator.genericsimulator.util;

import org.networkpacketgenerator.genericsimulator.model.PacketElement;
import org.networkpacketgenerator.genericsimulator.model.PacketStructure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PacketConverter {

    private PacketConverter() {}

    public static byte [] toByteArray(PacketStructure structure){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for(PacketElement element : structure.getElements()){
            byte[] elementBytes = SingleElementConverter.convert(element);
            try{
                outputStream.write(elementBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return outputStream.toByteArray();
    }

}
