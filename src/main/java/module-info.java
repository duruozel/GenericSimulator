module org.networkpacketgenerator.genericsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;


    opens org.networkpacketgenerator.genericsimulator to javafx.fxml;
    exports org.networkpacketgenerator.genericsimulator;
}