package org.networkpacketgenerator.genericsimulator.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;

public class PacketValidator {

    public record ValidatedData(String value, String dataType){}

    @Contract("_, _, _ -> new")
    public static @NotNull ValidatedData normalizeAndValidate(@NotNull String value, @NotNull String dataType, PacketElement.EndianType endian) throws IllegalArgumentException{
        String cleanValue = value.trim();
        String cleanDataType = dataType.toUpperCase().trim();

        if (cleanValue.isEmpty()) {
            throw new IllegalArgumentException("Bos deger (value) girdiniz.");
        }
        if (cleanDataType.isEmpty()) {
            throw new IllegalArgumentException("Bos deger (dataType) girdiniz.");
        }
        if (endian == null) {
            throw new IllegalArgumentException("Endian secimi zorunludur.");
        }

        long valueToValidate = parseAndCheckNegative(cleanValue);
        switch (cleanDataType) {
            case "U8":
                if (valueToValidate > PacketConstants.U8_MAX_VALUE) {
                    throw new IllegalArgumentException("Tasma hatasi: U8 alani en fazla 255 tutabilir! Girilen: " + valueToValidate);
                }
                break;
            case "U16":
                if (valueToValidate > PacketConstants.U16_MAX_VALUE) {
                    throw new IllegalArgumentException("Tasma hatasi: U16 alani en fazla 65535 tutabilir! Girilen: " + valueToValidate);
                }
                break;
            case "U32":
                if (valueToValidate > PacketConstants.U32_MAX_VALUE) {
                    throw new IllegalArgumentException("Tasma hatasi: U32 alani en fazla 4294967295 tutabilir! Girilen: " + valueToValidate);
                }
                break;
            default:
                throw new IllegalArgumentException("Tip Hatası: Sadece U8, U16 ve U32 tipleri desteklenmektedir.");
        }

        return new ValidatedData(cleanValue, cleanDataType);
    }

    public static long parseAndCheckNegative(String cleanValue){
        long parsedValue;
        try{
            parsedValue = Long.parseLong(cleanValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Girdiginiz deger tam sayi degil.");
        }
        if(parsedValue<0){
            throw new IllegalArgumentException("Unsigned icin negatif deger giremezsiniz.");
        }
        return parsedValue;
    }
}
