package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import java.net.URI;

import static sample.Utils.*;

public class Controller {
    public void btnSpeakUS_Click(MouseEvent mouseEvent) {
        if (listView.getSelectionModel().selectedItemProperty().getValue() != null) {
            //textSearch.setText();
            try {
                // Set property as Kevin Dictionary
                System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

                // Register Engine
                Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

                // Create a Synthesizer
                synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

                // Allocate synthesizer
                synthesizer.allocate();

                // Resume Synthesizer
                synthesizer.resume();

                // Speaks the given text
                // until the queue is empty.
                synthesizer.speakPlainText(listView.getSelectionModel().selectedItemProperty().getValue(), null);
                synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

                // Deallocate the Synthesizer.
                //synthesizer.deallocate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Clicked speak btn without selecting
            showAlertWithoutHeaderText("Không thể đọc! Vui lòng thử lại!");
        }
    }

    public void openInfoBrowser() throws URISyntaxException, IOException {
        URI u = new URI("https://github.com/duonanh195/DictionaryProj");
        java.awt.Desktop.getDesktop().browse(u);
    }
}
