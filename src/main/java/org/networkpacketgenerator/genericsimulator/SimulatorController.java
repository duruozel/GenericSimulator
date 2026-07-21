package org.networkpacketgenerator.genericsimulator;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;
import org.networkpacketgenerator.genericsimulator.facade.PacketVisualizer;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;
import org.networkpacketgenerator.genericsimulator.model.PacketStructure;
import org.networkpacketgenerator.genericsimulator.network.*;
import org.networkpacketgenerator.genericsimulator.util.FormValidator;
import org.networkpacketgenerator.genericsimulator.util.PacketDecoder;

public class SimulatorController {
    @FXML private TextField ipTextField;
    @FXML private TextField portTextField;
    @FXML private ComboBox<String> protocolComboBox;
    @FXML private TextField DataTextField;
    @FXML private ComboBox<String> dataTypeComboBox;
    @FXML private ComboBox<String> endianComboBox;

    private BaseListener activeListener;

    @FXML
    private void initialize() {
        protocolComboBox.getItems().addAll("TCP", "UDP");
        protocolComboBox.setPromptText("Protokol Tipini Seciniz");

        dataTypeComboBox.getItems().addAll("U8", "U16", "U32", "U64");
        dataTypeComboBox.setPromptText("Veri Tipini Seciniz");

        endianComboBox.getItems().addAll("BIG_ENDIAN", "LITTLE_ENDIAN");
        endianComboBox.setPromptText("Siralamayi Seciniz");

        final int listenPort = PacketConstants.LISTEN_PORT;

        protocolComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switchActiveListener(newValue, listenPort);
            }
        });

    }

    private String getSelectedDataType() {
        return dataTypeComboBox.getValue();
    }

    private String getSelectedEndianType() {
        return endianComboBox.getValue();
    }

    private PacketElement.EndianType getEndianType() {
        String endianStr = getSelectedEndianType();
        return endianStr != null ? PacketElement.EndianType.valueOf(endianStr) : PacketElement.EndianType.BIG_ENDIAN;
    }
    private void switchActiveListener(String protocol, int  port) {
        if (activeListener != null) {
            activeListener.stopListening();
        }

        if ("TCP".equals(protocol)) {
            activeListener = new TCPListener(port, (receivedBytes) -> {
                System.out.println("Controller, TCP uzerinden veri yakalandi");

                String decodedValue = PacketDecoder.decode(receivedBytes);

                System.out.println("Elimize gelen bilgiler: value: " + decodedValue + " dataType: " + getSelectedDataType() + " endian secimi: " + getSelectedEndianType());
            });
        } else if ("UDP".equals(protocol)) {
            activeListener = new UDPListener(port, (receivedBytes) -> {
                System.out.println("Controller, UDP uzerinden veri yakalandi.");
                String decodedValue = PacketDecoder.decode(receivedBytes);

                System.out.println("Elimize gelen bilgiler: value: " + decodedValue + " dataType: " + getSelectedDataType() + " endian secimi: " + getEndianType());
            });

        }

        if (activeListener != null) {
            activeListener.startListening();
        }

    }

    @FXML
    private void onSendButtonClick() {
        try {

            String targetIp = ipTextField.getText() != null ? ipTextField.getText().trim() : "";
            String portText = portTextField.getText() != null ? portTextField.getText().trim() : "";
            String rawInput = DataTextField.getText() != null ? DataTextField.getText().trim() : "";
            String selectedProtocol = protocolComboBox.getValue();

            FormValidator.validateForm(targetIp,portText,rawInput,selectedProtocol,getSelectedDataType(), getSelectedEndianType());

            int targetPort = Integer.parseInt(portText);

            PacketStructure structure = new PacketStructure();
            structure.addElement(new PacketElement(rawInput, getSelectedDataType(), getEndianType()));

            byte[] dataToSend = PacketVisualizer.toNetworkBytes(structure);

            BaseSender sender = "TCP".equals(selectedProtocol)
                    ? new TCPSender(targetIp,targetPort)
                    : new UDPSender(targetIp,targetPort);
            sender.send(dataToSend);

        }catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Gonderim hatasi: " + e.getMessage());
        }

    }
}
