module com.example.cmpt_365_wav_reader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.cmpt_365_wav_reader to javafx.fxml;
    exports com.example.cmpt_365_wav_reader;
}