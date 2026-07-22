package org.networkpacketgenerator.genericsimulator.util;

import org.networkpacketgenerator.genericsimulator.model.ConvertedValueList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteToFile {

    public static void saveToXml(ConvertedValueList convertedList, String filePath){

        StringBuilder xmlContent = new StringBuilder();

        xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlContent.append("<ConvertedElements>");

        List<byte[]> items = convertedList.getValueList();

        for(int i=0; i<items.size(); i++) {
            byte[] rawBytes = items.get(i);

            String hexData = HexFormatterUtil.bytesToHex(rawBytes);

            xmlContent.append("  <Entry index=\"").append(i + 1).append("\">\n");
            xmlContent.append("    <Value>").append(hexData).append("</Value>\n");
            xmlContent.append("  </Entry>\n");
        }
        xmlContent.append("</ConvertedElements>");

        try(FileWriter writer = new FileWriter(filePath)){
            writer.write(xmlContent.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    }

