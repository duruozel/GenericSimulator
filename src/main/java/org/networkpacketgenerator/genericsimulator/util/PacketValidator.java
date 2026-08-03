package org.networkpacketgenerator.genericsimulator.util;

import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PacketValidator {

    public record ValidatedData(String value, String dataType){}

   private static final Map<String, BigInteger> MAX_VALUES = Map.ofEntries(
           Map.entry("U8", BigInteger.valueOf(PacketConstants.U8_MAX_VALUE)),
           Map.entry("U16", BigInteger.valueOf(PacketConstants.U16_MAX_VALUE)),
           Map.entry("U32", BigInteger.valueOf(PacketConstants.U32_MAX_VALUE)),
           Map.entry("U64", new BigInteger("2").pow(64).subtract(BigInteger.ONE)),

           Map.entry("S8", BigInteger.valueOf(Byte.MAX_VALUE)),
           Map.entry("S16", BigInteger.valueOf(Short.MAX_VALUE)),
           Map.entry("S32", BigInteger.valueOf(Integer.MAX_VALUE)),
           Map.entry("S64", BigInteger.valueOf(Long.MAX_VALUE))
   );

    private static final Map<String, BigInteger> MIN_VALUES = Map.ofEntries(
            Map.entry("S8", BigInteger.valueOf(Byte.MIN_VALUE)),
            Map.entry("S16", BigInteger.valueOf(Short.MIN_VALUE)),
            Map.entry("S32", BigInteger.valueOf(Integer.MIN_VALUE)),
            Map.entry("S64", BigInteger.valueOf(Long.MIN_VALUE))
    );

    public static ValidatedData normalizeAndValidate(String value, String dataType, PacketElement.EndianType endian){
        if(value==null || value.isBlank()){
            throw new IllegalArgumentException("Deger (value) bos girdiniz");
        }
        if(dataType==null || dataType.isBlank()){
            throw new IllegalArgumentException("Veri tipini (data type) bos girdiniz.");
        }

        if(endian==null){
            throw new IllegalArgumentException("Endian secimi zorunludur.");
        }

        String cleanValue = value.trim();
        String cleanDataType = dataType.toUpperCase().trim();

        if("STRING".equals(cleanDataType)){
            byte[] stringBytes = cleanValue.getBytes(StandardCharsets.UTF_8);

            return new ValidatedData(cleanValue,cleanDataType);
        }

        BigInteger numericValue = parseToBigInteger(cleanValue);

        if(cleanDataType.startsWith("U") && numericValue.compareTo(BigInteger.ZERO)<0){
            throw new IllegalArgumentException("Unsigned tipler icin negatif deger girilemez");
        }

        BigInteger maxValue = MAX_VALUES.get(cleanDataType);

        if(maxValue==null){
            throw new IllegalArgumentException("Desteklenmeyen veri tipi");
        }
        if (numericValue.compareTo(maxValue) > 0) {
            throw new IllegalArgumentException("Tasma hatasi: " + cleanDataType + " en fazla " + maxValue + " alabilir!");
        }

        BigInteger minValue = MIN_VALUES.getOrDefault(cleanDataType, BigInteger.ZERO);
        if (numericValue.compareTo(minValue) < 0) {
            throw new IllegalArgumentException("Tasma hatasi: " + cleanDataType + " en az " + minValue + " alabilir!");
        }

        return new ValidatedData(cleanValue, cleanDataType);

    }

    private static BigInteger parseToBigInteger (String cleanValue){
        try{
            return new BigInteger(cleanValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Girdiginiz deger gecerli bir sayi degil.");
        }
    }

}