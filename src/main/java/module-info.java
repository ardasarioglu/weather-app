module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires jdk.jsobject;
    requires org.json;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}