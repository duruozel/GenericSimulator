package org.networkpacketgenerator.genericsimulator.util;

import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;

import java.math.BigInteger;

public class PacketValidator {

    public record ValidatedData(String value, String dataType){}

    public static ValidatedData normalizeAndValidate(String value, String dataType, PacketElement.EndianType endian) throws IllegalArgumentException{
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

        BigInteger valueToValidate = parseAndCheckNegative(cleanValue);

        switch (cleanDataType) {
            case "U8":
                BigInteger u8Max = BigInteger.valueOf(PacketConstants.U8_MAX_VALUE);
                if (valueToValidate.compareTo(u8Max) > 0) {
                    throw new IllegalArgumentException("Tasma hatasi: U8 alani en fazla 255 tutabilir! Girilen: " + cleanValue);
                }
                break;
            case "U16":
                BigInteger u16Max = BigInteger.valueOf(PacketConstants.U16_MAX_VALUE);
                if (valueToValidate.compareTo(u16Max) > 0) {
                    throw new IllegalArgumentException("Tasma hatasi: U16 alani en fazla 65535 tutabilir! Girilen: " + cleanValue);
                }
                break;
            case "U32":
                BigInteger u32Max = BigInteger.valueOf(PacketConstants.U32_MAX_VALUE);
                if (valueToValidate.compareTo(u32Max) > 0) {
                    throw new IllegalArgumentException("Tasma hatasi: U32 alani en fazla 4294967295 tutabilir! Girilen: " + cleanValue);
                }
                break;
            case "U64":
                BigInteger u64Max = new BigInteger("18446744073709551615");
                if (valueToValidate.compareTo(u64Max) > 0) {
                    throw new IllegalArgumentException("Tasma hatasi: U64 alani en fazla 18446744073709551615 tutabilir! Girilen: " + cleanValue);
                }
                break;
            default:
                throw new IllegalArgumentException("Tip Hatası: Sadece U8, U16, U32 ve U64 tipleri desteklenmektedir.");
        }

        return new ValidatedData(cleanValue, cleanDataType);
    }

    public static BigInteger parseAndCheckNegative(String cleanValue) {
        BigInteger parsedValue;
        try {
            parsedValue = new BigInteger(cleanValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Girdiginiz deger geçerli bir tam sayi degil.");
        }

        if (parsedValue.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Unsigned tipler için negatif deger giremezsiniz.");
        }

        return parsedValue;
    }
}

