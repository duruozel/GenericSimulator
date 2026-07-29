package org.networkpacketgenerator.genericsimulator.designPatterns;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.networkpacketgenerator.genericsimulator.model.PacketElement;

public class UIElementFactory {

    private UIElementFactory() {}

    public static HBox createDynamicRow(PacketElement.EndianType currentEndian) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> dynamicDataType = new ComboBox<>();
        dynamicDataType.getItems().addAll("U8", "U16", "U32", "U64");
        dynamicDataType.setPromptText("Veri Tipi");
        dynamicDataType.setPrefWidth(120);

        TextField dynamicTextField = new TextField();
        dynamicTextField.setPromptText("Deger Giriniz");
        dynamicTextField.setPrefWidth(150);

        row.getChildren().addAll(dynamicDataType, dynamicTextField);

        row.setUserData(currentEndian);

        return row;
    }


    public static String getDataType(HBox row) {
        ComboBox<String> box = (ComboBox<String>) row.getChildren().getFirst();
        return box.getValue();
    }

    public static String getValue(HBox row) {
        TextField field = (TextField) row.getChildren().get(1);
        return field.getText() != null ? field.getText().trim() : "";
    }

    public static PacketElement.EndianType getEndianType(HBox row) {
        Object data = row.getUserData();
        if (data instanceof PacketElement.EndianType endian) {
            return endian;
        }
        return PacketElement.EndianType.BIG_ENDIAN;
    }
}