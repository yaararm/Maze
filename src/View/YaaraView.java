package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class YaaraView implements Observer {
    @FXML
    private MyViewModel viewModel;
    public MazeDisplayer mazeDisplayer;


    public javafx.scene.control.Toggle btn_music;
    public javafx.scene.control.ToggleGroup difficult;
    public javafx.scene.control.Button btn_start;
    public javafx.scene.control.Toggle btn_hard;
    public javafx.scene.control.Toggle btn_easy;
    public javafx.scene.control.Toggle btn_medium;

    private Boolean musicIsOn = true;
    public static MediaPlayer mediaPlayer;
    public String difficultLevel ;

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            //displayMaze(viewModel.getMaze());
           // btn_new.setDisable(false);

            // btn_generateMaze.setDisable(false);
        }
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }
/*
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
       // mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        //this.characterPositionRow.set(characterPositionRow + "");
        //this.characterPositionColumn.set(characterPositionColumn + "");
    }


    public void revealSolution() {
        showAlert("Solving maze..");
    }
*/
    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
/*
    public void KeyPressed(KeyEvent keyEvent) {

        //viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }
*/
    public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
                //ToDo redraw maze sol and character
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
            }
        });
    }
/*
    public void AboutUs(ActionEvent actionEvent) {
        showAlert("this is us!!!!!!!!");

        try {
            Stage stage = new Stage();
            stage.setTitle("About Us");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("AboutUs.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void AboutTheGame(ActionEvent actionEvent) {
        showAlert("this is maze!!!!!!!!");

        try {
            Stage stage = new Stage();
            stage.setTitle("About The Game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("AboutTheGame.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }
*/
    public void musicMute(ActionEvent actionEvent) {
        if (musicIsOn) {//mute
            this.mediaPlayer.stop();
            musicIsOn = false;
        } else {// on
            this.mediaPlayer.play();
            musicIsOn = true;
        }
    }

    public void openMazeWindow(ActionEvent actionEvent) { // connect with tomer view after start

        if (difficult.getSelectedToggle()!=null){
            btn_start.setDisable(false);
            showAlert("YES");



        }
        else
            showAlert("you have to choose difficulty level");
    }

    private void ButtonStart(ActionEvent event) {
        btn_start.setDisable(true);
    }

    public void easy_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected())
        btn_start.setDisable(false);
        else
            btn_start.setDisable(true);

    }
    public void medium_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected())
            btn_start.setDisable(false);
        else
            btn_start.setDisable(true);
    }
    public void hard_level(ActionEvent actionEvent) {
        if (btn_hard.isSelected()||btn_medium.isSelected()||btn_easy.isSelected())
            btn_start.setDisable(false);
        else
            btn_start.setDisable(true);
    }
}