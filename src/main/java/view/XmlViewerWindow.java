package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import java.awt.*;

public class XmlViewerWindow {
    public static void display(String xmlContent){
        Stage stage = new Stage();
        stage.setTitle("Guncel XML Icerigi");

        TextArea textArea = new TextArea(xmlContent);
        textArea.setEditable(false);
        textArea.setStyle("-fx-font-family: 'Monospaced', monospace; -fx-font-size: 13px;");

        VBox layout = new VBox(10, textArea);
        layout.setPadding(new Insets(10));
        VBox.setVgrow(textArea, Priority.ALWAYS);

        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
    }

