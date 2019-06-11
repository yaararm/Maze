/**
 * this class is the controller of the maze window
 */
package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class MyViewController implements Observer,IView {
    private String mazeDifficulty;
    private boolean isHint;

    @FXML
    private MyViewModel viewModel;
    public MazeDisplayer mazeDisplayer;
    public SolutionDisplayer solutionDisplayer;
    public ChracterDisplayer chracterDisplayer;
    public javafx.scene.control.MenuItem btn_newEasy;
    public javafx.scene.control.MenuItem btn_newMedium;
    public javafx.scene.control.MenuItem btn_newHard;
    public javafx.scene.control.Menu btn_new;
    public javafx.scene.layout.Pane mainPane;
    public javafx.scene.layout.BorderPane mainBorderPane;
    public javafx.scene.control.MenuItem btn_save;
    public javafx.scene.control.MenuItem btn_open;
    public javafx.scene.control.Toggle tg_mute;
    public javafx.scene.control.Button btn_hint;
    public javafx.scene.control.Button btn_revealSolution;
    public javafx.scene.control.Button btn_yes;
    public javafx.scene.control.Button btn_no;


    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            if ((int) arg == 1) {
                displayMaze(viewModel.getMaze());
                displayCharecter(viewModel.getMaze()[0].length);
                btn_new.setDisable(false);

            } else if ((int) arg == 2) {
                displayCharecter(viewModel.getMaze()[0].length);
            } else if ((int) arg == 3) {
                displayCharecter(viewModel.getMaze()[0].length);
                mazeSolved();
            }
        }

    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void displayMaze(int[][] maze) {
        mazeDisplayer.setMaze(maze);
    }

    public void displayCharecter(int lenght) {
        chracterDisplayer.setArraySize(lenght);

        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        chracterDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
    }

    public void generateMaze(int i, int j) {
        solutionDisplayer.clear();
        viewModel.generateMaze(i, j);
        btn_hint.setDisable(false);
        btn_revealSolution.setDisable(false);
        mainPane.requestFocus();

    }

    public void generateEasyMaze() {
        btn_new.setDisable(true);
        generateMaze(21, 21);

    }

    public void generateMediumMaze() {
        btn_new.setDisable(true);
        generateMaze(35, 35);
    }

    public void generateHardMaze() {
        btn_new.setDisable(true);
        generateMaze(51, 51);
    }

    public void getHint() {
        isHint = true;
        int[][] nextStep = viewModel.getNextStep();
        solutionDisplayer.setSize(mainPane.getHeight(), mainPane.getWidth());
        solutionDisplayer.setSolution(nextStep);


    }

    public void revealSolution() {
        int[][] sol = viewModel.getAllSolution();
        solutionDisplayer.setSize(mainPane.getHeight(), mainPane.getWidth());
        solutionDisplayer.setSolution(sol);
    }

    public void KeyPressed(KeyEvent keyEvent) {
        if (isHint) {
            solutionDisplayer.clear();
            isHint = false;
        }
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    public void setResizeEvent(Scene scene) {

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            mazeDisplayer.setSize(mainPane.getHeight(), mainPane.getWidth());
            solutionDisplayer.setSize(mainPane.getHeight(), mainPane.getWidth());
            chracterDisplayer.setSize(mainPane.getHeight(), mainPane.getWidth());
            //System.out.println("Width: " + newValue);
        };
        mainPane.widthProperty().addListener(stageSizeListener);
        mainPane.heightProperty().addListener(stageSizeListener);
    }


    public void AboutUs(ActionEvent actionEvent) {
        //showAlert("this is us!!!!!!!!");

        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("About Us");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("aboutUs.fxml").openStream());
            Scene scene = new Scene(root, 550, 400);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void AboutTheGame(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("About The Game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("AboutTheGame.fxml").openStream());
            Scene scene = new Scene(root, 650, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void HowToPlay(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("How To Play");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("howToPlay.fxml").openStream());
            Scene scene = new Scene(root, 650, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }


    public void setDifficulty(String diff) {
        this.mazeDifficulty = diff;
    }

    public void generateFirstMaze() {
        if (mazeDifficulty != null) {
            if (mazeDifficulty == "easy") generateEasyMaze();
            if (mazeDifficulty == "medium") generateMediumMaze();
            if (mazeDifficulty == "hard") generateHardMaze();

        }
    }

    //region String Property for Binding
    public StringProperty characterPositionRow = new SimpleStringProperty();

    public StringProperty characterPositionColumn = new SimpleStringProperty();

    public String getCharacterPositionRow() {
        return characterPositionRow.get();
    }

    public StringProperty characterPositionRowProperty() {
        return characterPositionRow;
    }

    public String getCharacterPositionColumn() {
        return characterPositionColumn.get();
    }

    public StringProperty characterPositionColumnProperty() {
        return characterPositionColumn;
    }

    //endregion

    public void saveMazeToFile(ActionEvent actionEvent) {
        viewModel.saveMazeToFile();
    }

    public void openExistMaze(ActionEvent actionEvent) {
        viewModel.openExistMaze();
    }

    private void mazeSolved() {
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Winning!!!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("endScene.fxml").openStream());
            fxmlLoader.setController(this);
            Scene scene = new Scene(root, 580, 380);
            stage.setScene(scene);
            scene.getStylesheets().add("View/mainDisplay.css");
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes

            //set winnig sound
            String musicFile = "resources/End.mp3";     // For example
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer FinishSong = new MediaPlayer(sound);

            //set buttons
            btn_yes = new Button();
            btn_no = new Button();
            Button no = (Button) scene.lookup("#btn_no");
            Button yes = (Button) scene.lookup("#btn_yes");

            //set action events
            stage.setOnCloseRequest(e -> {
                no.fire();
            });
            yes.setOnAction((event) -> {
                // Button was clicked, do something...
                generateFirstMaze();
                FinishSong.stop();
                WelcomeView.mediaPlayer.play();
                stage.close();
                event.consume();
            });
            no.setOnAction((event) -> {
                // Button was clicked, do something...
                solutionDisplayer.clear();
                btn_hint.setDisable(true);
                btn_revealSolution.setDisable(true);
                FinishSong.stop();
                WelcomeView.mediaPlayer.play();
                stage.close();
                event.consume();


            });
            WelcomeView.mediaPlayer.stop();
            stage.show();
            FinishSong.play();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void mute(ActionEvent actionEvent) {
        if (tg_mute.isSelected()) {
            WelcomeView.mediaPlayer.setVolume(0);
        } else {
            WelcomeView.mediaPlayer.setVolume(1);

        }
        mainPane.requestFocus();
    }

    public void quitFunction(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_hint.getScene().getWindow();
        WelcomeView.mediaPlayer.stop();


        stage.fireEvent(
                new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)
        );
    }
}



