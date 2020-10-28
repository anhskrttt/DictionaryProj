package application.services;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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
        btnAdd = (ImageView) scene.lookup("#btnAdd");
        
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
    
        btnAdd.setOnMousePressed(event -> {
            Stage popupwindow=new Stage();
            popupwindow.setTitle("Add word");
//            popupwindow.initModality(Modality.APPLICATION_MODAL);
        
            TextArea textArea = new TextArea();
            textArea.setText(String.format(
                            "VOCAB " +
                            "<html>" +
                            "<i> VOCAB </i>\n" +
                            "\t<!--Type_1 starts here-->\n" +
                            "<br/><ul><li>\n" +
                            "<b><i> TYPE_OF_VOCAB </i></b>\n" +
                            "\t<!--Type_1 Meaning_1 starts here-->\n" +
                            "<ul><li><font color='#cc0000'><b> MEANING1 </b></font></li></ul>\n" +
                            "\t<!--Type_1 Meaning_2 starts here-->\n" +
                            "<ul><li><font color='#cc0000'><b> MEANING2 </b></font></li></ul>\n" +
                            "\t<!--Add other meanings of this type below here-->\n\n" +
        
                            "</li></ul>\n" +
                            "\t<!--Type_1 ends-->\n" +
                            "\t<!--Definite other types of this vocab below here-->\n\n\n" +
                            "</html>", textArea.getText()));
        
            Button button = new Button("Done");
            button.setOnAction(e -> {
                String[] parts = textArea.getText().split(SPLITTING_CHARACTERS);
                String word = parts[0];
                String definition = SPLITTING_CHARACTERS + parts[1];
                Word wordObj = new Word(word, definition);
                data.put(word, wordObj);
            
                loadWordList();
                popupwindow.close();
                showAlertWithoutHeaderText("Thêm từ thành công!");
            });
        
            VBox layout = new VBox();
            layout.getChildren().addAll(textArea, button);
            layout.setAlignment(Pos.CENTER);
        
            Scene newScene = new Scene(layout, 500, 300);
            popupwindow.setScene(newScene);
            popupwindow.showAndWait();
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
