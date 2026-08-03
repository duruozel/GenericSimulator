package org.networkpacketgenerator.genericsimulator.util;

import org.networkpacketgenerator.genericsimulator.model.PacketElement;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class SingleElementConverter {
    private SingleElementConverter() {}

    public static byte[] convert(PacketElement element) {
        String dataType = element.getDataType().toUpperCase().trim();
        String rawValue = element.getValue();

        if ("STRING".equals(dataType)) {
            return rawValue.getBytes(StandardCharsets.UTF_8);
        }

        BigInteger numericValue = new BigInteger(rawValue);

        ByteOrder order = (element.getEndian() == PacketElement.EndianType.BIG_ENDIAN)
                ? ByteOrder.BIG_ENDIAN
                : ByteOrder.LITTLE_ENDIAN;

        return switch (dataType) {
            case "U8", "S8" -> new byte[]{ numericValue.byteValue() };

            case "U16", "S16" -> ByteBuffer.allocate(2)
                    .order(order)
                    .putShort(numericValue.shortValue())
                    .array();

            case "U32", "S32" -> ByteBuffer.allocate(4)
                    .order(order)
                    .putInt(numericValue.intValue())
                    .array();

            case "U64", "S64" -> ByteBuffer.allocate(8)
                    .order(order)
                    .putLong(numericValue.longValue())
                    .array();

            default -> throw new IllegalArgumentException("Donusturulemez veri tipi: " + dataType);
        };
    }
}