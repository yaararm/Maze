
import Model.MyModel;
import View.MyView;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {
    Stage window;



    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        MyModel model = new MyModel();
        model.startServers();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        window.setTitle("My Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/BasicView.fxml").openStream());
        Scene scene = new Scene(root, 700, 700);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        window.setScene(scene);
        //--------------
        MyView view = fxmlLoader.getController();
        view.setResizeEvent(scene);
        view.setMaximizeEvent(window);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
        window.setMinWidth(800);
        window.setMinHeight(800);
        SetStageCloseEvent(window);
        window.show();
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
