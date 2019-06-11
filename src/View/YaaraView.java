package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;


public class YaaraView  {
    private MyView mazeView;
    @FXML
    Scene mazeScene;


    public javafx.scene.control.Toggle btn_music;
    public javafx.scene.control.ToggleGroup difficult;
    public javafx.scene.control.Button btn_start;
    public javafx.scene.control.Toggle btn_hard;
    public javafx.scene.control.Toggle btn_easy;
    public javafx.scene.control.Toggle btn_medium;

    public static MediaPlayer mediaPlayer;
    public String difficultLevel ;

    public void setMazeScene(Scene mazeScene) {
        this.mazeScene = mazeScene;
    }
    public void setMazeView(MyView view) {
        this.mazeView = view;
    }





    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //ToDo redraw maze sol and character
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            }
        });
    }

    public void musicMute(ActionEvent actionEvent) {
        if (btn_music.isSelected()) {//mute
            this.mediaPlayer.setVolume(0);
            mazeView.tg_mute.setSelected(true);
        } else {// on
            this.mediaPlayer.setVolume(1);
            mazeView.tg_mute.setSelected(false);

        }
    }

    public void openMazeWindow() throws IOException { // connect with tomer view after start

        if (difficult.getSelectedToggle()!=null){
            btn_start.setDisable(false);
            //showAlert("YES");
            UpdateDifficultyAtMazeController();
            Stage stage = (Stage) btn_start.getScene().getWindow();
            stage.setResizable(true);
            stage.setScene(mazeScene);
            stage.centerOnScreen();
            stage.setMinHeight(700);
            stage.setMinWidth(605);


        }
        else
            showAlert("you have to choose difficulty level");
    }
    public void UpdateDifficultyAtMazeController() throws IOException { // connect with tomer view after start

        mazeView.setDifficulty(difficultLevel);
        mazeView.generateFirstMaze();


    }

    public void easy_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected()){
            difficultLevel = "easy";
            btn_start.setDisable(false);
        }

        else
            btn_start.setDisable(true);

    }
    public void medium_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected()){
            difficultLevel = "medium";
            btn_start.setDisable(false);
        }
        else
            btn_start.setDisable(true);
    }
    public void hard_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected()){
            difficultLevel = "hard";
            btn_start.setDisable(false);
        }
        else
            btn_start.setDisable(true);
    }
}