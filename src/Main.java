import Model.MyModel;
import View.MyView;
import View.YaaraView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.util.Optional;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("Savta Haya Meta");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/base6.fxml").openStream());
        Scene scene = new Scene(root, 560, 330);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);


        //maybe on "setmusic?"
        String musicFile = "resources/startMusic.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        YaaraView.mediaPlayer = new MediaPlayer(sound);
        YaaraView.mediaPlayer.play();
        //--------------
        YaaraView view = fxmlLoader.getController();
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
        SetStageCloseEvent(primaryStage);

        primaryStage.show();
    }

    private void SetStageCloseEvent(Stage primaryStage) { //ToDo close servers
        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                // Close program
            } else {
                // ... user chose CANCEL or closed the dialog
                e.consume();
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
