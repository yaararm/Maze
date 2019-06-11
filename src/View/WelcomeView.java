/**
 * this class is the controller of the welcome scene
 */
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


public class WelcomeView {
    private MyViewController mazeView;
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

    /**
     *  this function set the maze scene
     * @param mazeScene
     */
    public void setMazeScene(Scene mazeScene) {
        this.mazeScene = mazeScene;
    }
    /**
     * this function set the controller for the maze view
     * @param view
     */
    public void setMazeView(MyViewController view) {
        this.mazeView = view;
    }
    /**
     * this function mute the music
     * @param actionEvent
     */
    public void musicMute(ActionEvent actionEvent) {
        if (btn_music.isSelected()) {//mute
            this.mediaPlayer.setVolume(0);
            mazeView.tg_mute.setSelected(true);
        } else {// on
            this.mediaPlayer.setVolume(1);
            mazeView.tg_mute.setSelected(false);

        }
    }
    /**
     * this function open the window of the maze game
     * @throws IOException
     */
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
    /**
     * this function update the difficulty of the maze
     * @throws IOException
     */
    public void UpdateDifficultyAtMazeController() throws IOException { // connect with tomer view after start

        mazeView.setDifficulty(difficultLevel);
        mazeView.generateFirstMaze();


    }

    /**
     * set easy level as maze difficulty
     * @param actionEvent
     */
    public void easy_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected()){
            difficultLevel = "easy";
            btn_start.setDisable(false);
        }

        else
            btn_start.setDisable(true);

    }

    /**
     * set medium level as maze difficulty
     * @param actionEvent
     */
    public void medium_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected()){
            difficultLevel = "medium";
            btn_start.setDisable(false);
        }
        else
            btn_start.setDisable(true);
    }
    /**
     * set hard level as maze difficulty
     * @param actionEvent
     */
    public void hard_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected()){
            difficultLevel = "hard";
            btn_start.setDisable(false);
        }
        else
            btn_start.setDisable(true);
    }

    /**
     * this function shows alert
     * @param alertMessage
     */
    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

}