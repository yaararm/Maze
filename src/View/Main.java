package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("Savta Haya Meta");


        //create welcome scene
        FXMLLoader fxmlLoader1 = new FXMLLoader();
        Parent rootWelcome = fxmlLoader1.load(getClass().getResource("welcome.fxml").openStream());
        Scene welcomeScene = new Scene(rootWelcome, 560, 330);
        welcomeScene.getStylesheets().add(getClass().getResource("WelcomeStyle.css").toExternalForm());

        //create main display scene
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        Parent rootMaze = fxmlLoader2.load(getClass().getResource("BasicView.fxml").openStream());
        Scene mazeScene = new Scene(rootMaze, 800, 800);
        mazeScene.getStylesheets().add(getClass().getResource("mainDisplay.css").toExternalForm());

        primaryStage.setScene(welcomeScene);


        startMusic();



        //--------------
        MyView view = fxmlLoader2.getController();
        YaaraView yaaraView = fxmlLoader1.getController();
        yaaraView.setMazeScene(mazeScene);
        yaaraView.setMazeView(view);
        //--------------------------
        view.setResizeEvent(mazeScene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
        SetStageCloseEvent(primaryStage,model);

        primaryStage.show();
    }

    private void SetStageCloseEvent(Stage primaryStage,MyModel model) {
        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                model.stopServers();
                // ... user chose OK
                // Close program
            } else {
                // ... user chose CANCEL or closed the dialog
                e.consume();
            }
        });

    }
    private void startMusic(){
        String musicFile = "resources/opening.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        YaaraView.mediaPlayer = new MediaPlayer(sound);
        YaaraView.mediaPlayer.setCycleCount(INDEFINITE);
        YaaraView.mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
