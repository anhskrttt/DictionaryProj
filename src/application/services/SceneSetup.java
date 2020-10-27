package application.services;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import static application.services.Utils.*;
import static application.services.Utils.showAlertWithoutHeaderText;

public class SceneSetup {

    private List<String> listSearchWord = new ArrayList<>();

    public void sceneSetup() throws IOException {
        // init components
        initComponents(scene);

        // read word list from E_V.txt
        readData();

        // load word list to the ListView
        loadWordList();
    }

    private void initComponents(Scene scene) {
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

    private void loadWordList() {
        listView.getItems().addAll(data.keySet());
    }

    private void readData() throws IOException {
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

    private void deleteWord() {
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
