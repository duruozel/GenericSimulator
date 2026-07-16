package org.networkpacketgenerator.genericsimulator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;
import org.networkpacketgenerator.genericsimulator.network.*;

public class SimulatorController {
    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;
    @FXML private ComboBox<String> protocolComboBox;
    @FXML private TextField DataTextField;
    @FXML private Button sendButton;

    private TCPListener tcpListener;
    private UDPListener udpListener;

    @FXML
    private void initialize(){
        protocolComboBox.getItems().addAll("TCP", "UDP");
        protocolComboBox.setPromptText("Protokol Tipini Seciniz");

        int testPort=5005;

        tcpListener = new TCPListener(testPort);
        tcpListener.startListening();

        udpListener=new UDPListener(testPort);
        udpListener.startListening();

    }

    @FXML
    private void onSendButtonClick() {
        try {

            String targetIp = ipTextField.getText() != null ? ipTextField.getText().trim() : "";
            String portText = portTextField.getText() != null ? portTextField.getText().trim() : "";
            String selectedProtocol = protocolComboBox.getValue();
            String hexInput = DataTextField.getText() != null ? DataTextField.getText().trim() : "";

            if (targetIp.isEmpty() || portText.isEmpty() || hexInput.isEmpty()) {
                System.err.println("Hata: IP, Port ve Hex Veri alanlari bos birakilamaz");
                return;
            }

            if (selectedProtocol == null || selectedProtocol.equals("Protokol Tipini Seciniz")) {
                System.err.println("Hata: Lutfen bir protokol tipi (TCP/UDP) secin!");
                return;
            }
            try {
                java.net.InetAddress.getByName(targetIp);
            } catch (Exception e) {
                System.err.println("Hata: Gecersiz IP adresi");
                return;
            }
            int targetPort = Integer.parseInt(portText);

            if (targetPort < PacketConstants.MIN_PORT || targetPort > PacketConstants.MAX_PORT) {
                System.err.println("Hata: Port numarasi 0 ile 65535 arasinda olmalidir");
                return;
            }

            byte rawByte = (byte) Integer.parseInt(hexInput, 16);

            BaseSender sender;
            if ("TCP".equals(selectedProtocol)) {
                sender = new TCPSender(targetIp, targetPort);
            } else {
                sender = new UDPSender(targetIp, targetPort);
            }

            sender.send(rawByte);

        } catch (NumberFormatException e) {
            System.err.println("Hata: Port sayisal olmali ve Hex veri gecerli (0-9, A-F) formatta olmalidir");
        } catch (Exception e) {
            System.err.println("Gonderim sirasinda beklenmedik bir hata: " + e.getMessage());
        }
    }

}
