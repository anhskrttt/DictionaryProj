package application.services;

import javafx.scene.input.MouseEvent;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import static application.services.Utils.*;

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

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Clicked speak btn without selecting
            showAlertWithoutHeaderText("Không thể đọc! Vui lòng thử lại!");
        }
    }

    public void openInfoBrowser() throws URISyntaxException, IOException {
        URI u = new URI(INFO_HYPERLINK);
        java.awt.Desktop.getDesktop().browse(u);
    }
}
