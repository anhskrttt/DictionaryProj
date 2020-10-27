package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.speech.synthesis.Synthesizer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Utils {
    public static Stage stage = new Stage();

    //constant values
    public static final String DATA_FILE_PATH = "src/resources/text/E_V.txt";
    public static final String FXML_FILE_PATH = "application/sample.fxml";
    public static final String SPLITTING_CHARACTERS = "<html>";

    public static Map<String, Word> data = new HashMap<>();

    //synthesizer of tts
    public static Synthesizer synthesizer;

    //FXML
    @FXML
    public static TextField textSearch;
    @FXML
    public static ListView<String> listView;
    @FXML
    public static WebView definitionView;
    @FXML
    public static ImageView btnDelete;

    //Create an alert window
    public static void showAlertWithoutHeaderText(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(mess);
        alert.showAndWait();
    }

    public static boolean showQuitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }

    public static void closeProgram() {
        boolean confirm = false;
        confirm = showQuitConfirmation();
        if (confirm) {
            //Save files

            stage.close();
        }
    }

    public static void saveDataFile() {
        try {
            FileWriter writer = new FileWriter("E_V.txt");
            writer.write("");
            BufferedWriter buffer = new BufferedWriter(writer);

            for (Map.Entry<String, Word> entry : data.entrySet()) {
                Word value = entry.getValue();
                System.out.println(value.getDef());
                buffer.append(value.getDef());
                buffer.newLine();
            }
            buffer.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
