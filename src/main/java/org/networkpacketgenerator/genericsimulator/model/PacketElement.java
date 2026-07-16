package org.networkpacketgenerator.genericsimulator.model;

import org.networkpacketgenerator.genericsimulator.util.PacketValidator;

public class PacketElement {

    public enum EndianType{
        BIG_ENDIAN, LITTLE_ENDIAN
    }
    private final String value;
    private final String dataType;
    private final EndianType endian;

    public PacketElement(String value, String dataType, EndianType endian){
        PacketValidator.ValidatedData validated = PacketValidator.normalizeAndValidate(value,dataType,endian);
        this.value= validated.value();
        this.dataType=validated.dataType();
        this.endian=endian;

    }

    public String getValue() { return value; }
    public String getDataType() { return dataType; }
    public EndianType getEndian() { return endian; }

}
