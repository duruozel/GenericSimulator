package org.networkpacketgenerator.genericsimulator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.networkpacketgenerator.genericsimulator.constants.PacketConstants;
import org.networkpacketgenerator.genericsimulator.designPatterns.PacketVisualizer;
import org.networkpacketgenerator.genericsimulator.designPatterns.UIElementFactory;
import org.networkpacketgenerator.genericsimulator.model.ConvertedValueList;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;
import org.networkpacketgenerator.genericsimulator.model.PacketStructure;
import org.networkpacketgenerator.genericsimulator.network.*;
import org.networkpacketgenerator.genericsimulator.util.FormValidator;
import org.networkpacketgenerator.genericsimulator.util.WriteToFile;
import view.XmlViewerWindow;

import java.io.IOException;

public class SimulatorController {

    @FXML
    private TextField ipTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private ComboBox<String> protocolComboBox;
    @FXML
    private ComboBox<String> endianComboBox;
    @FXML
    private VBox dynamicRowsVBox;

    private BaseListener activeListener;
    private static final String xml_File_Path = "output_message.xml";

    @FXML
    private void initialize() {
        protocolComboBox.getItems().addAll("TCP", "UDP");
        protocolComboBox.setPromptText("Protokol Tipini Seciniz");

        endianComboBox.getItems().addAll("BIG_ENDIAN", "LITTLE_ENDIAN");
        endianComboBox.setPromptText("Siralamayi Seciniz");

        final int listenPort = PacketConstants.LISTEN_PORT;
        protocolComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switchActiveListener(newValue, listenPort);
            }
        });

        onAddRowButtonClick();
    }

    private String getSelectedEndianType() {
        return endianComboBox.getValue();
    }

    private PacketElement.EndianType getEndianType() {
        String endianStr = getSelectedEndianType();
        return endianStr != null ? PacketElement.EndianType.valueOf(endianStr) : PacketElement.EndianType.BIG_ENDIAN;
    }

    private void switchActiveListener(String protocol, int port) {
        if (activeListener != null) {
            activeListener.stopListening();
        }

        if ("TCP".equals(protocol)) {
            activeListener = new TCPListener(port, (receivedBytes) -> {
                System.out.println("Controller, TCP uzerinden veri yakalandi");
            });
        } else if ("UDP".equals(protocol)) {
            activeListener = new UDPListener(port, (receivedBytes) -> {
                System.out.println("Controller, UDP uzerinden veri yakalandi.");
            });
        }

        if (activeListener != null) {
            activeListener.startListening();
        }
    }

    @FXML
    private void onAddRowButtonClick() {
        HBox newRow = UIElementFactory.createDynamicRow();
        if (dynamicRowsVBox != null) {
            dynamicRowsVBox.getChildren().add(newRow);
        }
    }

    @FXML
    private void onSendButtonClick() {
        try {
            String targetIp = ipTextField.getText() != null ? ipTextField.getText().trim() : "";
            String portText = portTextField.getText() != null ? portTextField.getText().trim() : "";
            String selectedProtocol = protocolComboBox.getValue();

            PacketStructure structure = new PacketStructure();

            if (dynamicRowsVBox != null) {
                for (Node node : dynamicRowsVBox.getChildren()) {
                    if (node instanceof HBox row) {

                        String dataType = UIElementFactory.getDataType(row);
                        String rawInput = UIElementFactory.getValue(row);

                        if (rawInput.isBlank()) {
                            continue;
                        }

                        FormValidator.validateForm(targetIp, portText, rawInput, selectedProtocol, dataType, getSelectedEndianType());
                        structure.addElement(new PacketElement(rawInput, dataType, getEndianType()));
                    }
                }
            }

            byte[] dataToSend = PacketVisualizer.toNetworkBytes(structure);

            if (dataToSend.length > 0) {
                ConvertedValueList.getInstance().addList(dataToSend);
                WriteToFile.saveToXml(ConvertedValueList.getInstance(), xml_File_Path);
            }

            int targetPort = Integer.parseInt(portText);
            BaseSender sender = "TCP".equals(selectedProtocol)
                    ? new TCPSender(targetIp, targetPort)
                    : new UDPSender(targetIp, targetPort);
            sender.send(dataToSend);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Gonderim hatasi: " + e.getMessage());
        }
    }

    @FXML
    private void onShowXmlButtonClick() {
        try {
            java.io.File xmlFile = new java.io.File(xml_File_Path);

            if (!xmlFile.exists() || xmlFile.length() == 0) {
                System.out.println("Dosyaya henuz yazilmamis");
                return;
            }

            String xmlContent = java.nio.file.Files.readString(xmlFile.toPath());
            XmlViewerWindow.display(xmlContent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}