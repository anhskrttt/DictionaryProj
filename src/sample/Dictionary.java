package sample;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Dictionary extends Application {

  private static final String DATA_FILE_PATH = "E_V.txt";
  private static final String FXML_FILE_PATH = "sample/sample.fxml";
  private static final String SPLITTING_CHARACTERS = "<html>";
  private Map<String, Word> data = new HashMap<>();

  private List<String> listSearchWord = new ArrayList<>();

  @FXML private TextField textSearch;
  @FXML private ListView<String> listView;
  @FXML private WebView definitionView;
  @FXML private ImageView btnDelete;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    VBox root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Dictionary Demonstration");
    primaryStage.show();

    // init components
    initComponents(scene);

    // read word list from E_V.txt
    readData();

    // load word list to the ListView
    loadWordList();
  }

  public void initComponents(Scene scene) {
    this.definitionView = (WebView) scene.lookup("#definitionView");
    this.listView = (ListView<String>) scene.lookup("#listView");
    this.textSearch = (TextField) scene.lookup("#textSearch");
    this.btnDelete = (ImageView) scene.lookup("#btnDelete");
    Dictionary context = this;
    this.listView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              Word selectedWord = data.get(newValue);
              if(selectedWord != null){
                String definition = selectedWord.getDef();
                context.definitionView.getEngine().loadContent(definition, "text/html");
              }
            });

    textSearch
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.length() > 0) {
                searchWord(newValue);
              }

              if (newValue.length() == 0) {
                listView.getItems().clear();
                loadWordList();
              }
            });

    btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        deleteWord();
      }
    });
  }

  private void searchWord(String wordInput) {
    listSearchWord.clear();
    for (Map.Entry<String, Word> entry : data.entrySet()) {
      String key = entry.getKey();

      String wordTemp = wordInput.replaceAll("\\s", "");
      String keyTemp = key.replaceAll("\\s", "");

      if (keyTemp.length() >= wordTemp.length()) {
        if (keyTemp.substring(0, wordTemp.length()).equalsIgnoreCase(wordTemp)) {
          listSearchWord.add(key.trim());
          if (keyTemp.length() == wordTemp.length()) {
            listSearchWord.set(listSearchWord.size() - 1, listSearchWord.get(0));
            listSearchWord.set(0, key);
          }
        }
      }
    }

    if (listSearchWord.size() > 0) {
      listView.getItems().clear();
      listView.getItems().addAll(listSearchWord);
    }
  }

  public void loadWordList() {
    this.listView.getItems().addAll(data.keySet());
  }

  public void readData() throws IOException {
    FileReader fis = new FileReader(DATA_FILE_PATH);
    BufferedReader br = new BufferedReader(fis);
    String line;
    while ((line = br.readLine()) != null) {
      String[] parts = line.split(SPLITTING_CHARACTERS);
      String word = parts[0];
      String definition = SPLITTING_CHARACTERS + parts[1];
      Word wordObj = new Word(word, definition);
      data.put(word, wordObj);
    }
  }

  // Hiển thị Information Alert không có Header Text
  private void showAlertWithoutHeaderText(String mess) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Thông báo");
    alert.setHeaderText(null);
    alert.setContentText(mess);
    alert.showAndWait();
  }

  public void deleteWord() {
    if (listView.getSelectionModel().getSelectedItem() != null) {
      data.remove(listView.getSelectionModel().getSelectedItem());
      listView.getItems().clear();
      loadWordList();
      definitionView.getEngine().loadContent("");
      //saveDataFile();
      showAlertWithoutHeaderText("Xóa từ thành công!");
    } else {
      showAlertWithoutHeaderText("Chưa chọn từ để xóa?");
    }
  }

  public void saveDataFile() {
    //
    try {
      FileWriter writer = new FileWriter("E_V.txt");
      writer.write("");
      BufferedWriter buffer = new BufferedWriter(writer);

      for (Map.Entry<String, Word> entry : data.entrySet()) {
        Word value = entry.getValue();
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

class Word {
  private String word;
  private String def;

  public Word(String word, String def) {
    this.word = word;
    this.def = def;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public String getDef() {
    return def;
  }

  public void setDef(String def) {
    this.def = def;
  }
}
