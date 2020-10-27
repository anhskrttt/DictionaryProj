package sample;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.*;
import java.util.Map;

import static sample.Utils.*;

public class DictionaryApplication extends Application {


    private List<String> listSearchWord = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        VBox root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Dictionary Demonstration");
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        stage.show();

        // init components
        initComponents(scene);

        // read word list from E_V.txt
        readData();

        // load word list to the ListView
        loadWordList();
    }

    public void initComponents(Scene scene) {
        definitionView = (WebView) scene.lookup("#definitionView");
        listView = (ListView<String>) scene.lookup("#listView");
        textSearch = (TextField) scene.lookup("#textSearch");
        btnDelete = (ImageView) scene.lookup("#btnDelete");
        //Dictionary context = this;
        listView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            Word selectedWord = data.get(newValue);
                            if (selectedWord != null) {
                                String definition = selectedWord.getDef();
                                definitionView.getEngine().loadContent(definition, "text/html");
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
        listView.getItems().addAll(data.keySet());
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

    public void deleteWord() {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            data.remove(listView.getSelectionModel().getSelectedItem());
            listView.getItems().clear();
            loadWordList();
            definitionView.getEngine().loadContent("");
            showAlertWithoutHeaderText("Xóa từ thành công!");
        } else {
            showAlertWithoutHeaderText("Chưa chọn từ để xóa?");
        }
    }


}


