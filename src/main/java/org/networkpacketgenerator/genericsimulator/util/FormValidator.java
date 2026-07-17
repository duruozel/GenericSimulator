package org.networkpacketgenerator.genericsimulator.util;

import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;

public class FormValidator {
    private FormValidator() {}

    public static void validateForm(String ip, String portText, String rawInput, String protocol, String dataType, String endian) {
        if (ip.isEmpty() || portText.isEmpty() || rawInput.isEmpty()) {
            throw new IllegalArgumentException("Hata: IP, Port ve Veri alanlari bos birakilamaz.");
        }

        if (protocol == null || dataType == null || endian == null) {
            throw new IllegalArgumentException("Hata: Lutfen secim kutularini eksiksiz doldurun.");
        }

        int targetPort = Integer.parseInt(portText);
        if (targetPort < PacketConstants.MIN_PORT || targetPort > PacketConstants.MAX_PORT) {
            throw new IllegalArgumentException("Hata: Gecersiz port numarasi.");
        }
}
}