module com.webapkconverter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires org.apache.commons.io;
    requires org.json;
    requires zip4j;
    
    opens com.webapkconverter to javafx.fxml;
    exports com.webapkconverter;
}
