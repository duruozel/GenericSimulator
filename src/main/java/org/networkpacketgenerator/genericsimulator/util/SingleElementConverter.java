package org.networkpacketgenerator.genericsimulator.util;

import org.jetbrains.annotations.NotNull;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SingleElementConverter {
    private SingleElementConverter() {}

    public static byte @NotNull [] convert(@NotNull PacketElement element){
        long numericValue = Long.parseLong(element.getValue());
        String dataType = element.getDataType();

        ByteOrder order = (element.getEndian()==PacketElement.EndianType.BIG_ENDIAN )
                ? ByteOrder.BIG_ENDIAN
                : ByteOrder.LITTLE_ENDIAN;

        return switch (dataType) {
            case "U8" -> new byte[]{(byte) numericValue};
            case "U16" -> ByteBuffer.allocate(2)
                    .order(order)
                    .putShort((short) numericValue)
                    .array();
            case "U32" -> ByteBuffer.allocate(4)
                    .order(order)
                    .putInt((int) numericValue)
                    .array();
            case "U64" -> ByteBuffer.allocate(8)
                    .order(order)
                    .putLong((long) numericValue)
                    .array();
            default -> throw new IllegalArgumentException("Donusturulemez veri tipi");
        };
    }
}
