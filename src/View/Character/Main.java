package View.Character;

import View.MazeDisplayer;
import View.SolutionDisplayer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {
    private HashMap<KeyCode,Boolean> keys = new HashMap<>();
    //Image image = new Image(getClass().getResourceAsStream("path Here"));
     Image image = new Image("file:resources/Images/download.png");
    ImageView imageView = new ImageView(image);
    Charecter player = new Charecter(imageView);

    public Pane p = new Pane();

    public Pane root = new Pane();

     @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();
         root.setPrefSize(600,600);
         MazeDisplayer m = new MazeDisplayer();
         SolutionDisplayer s = new SolutionDisplayer();
         root.getChildren().addAll(player,m,s);
         Scene scene = new Scene(root);
         root.setBackground(new Background(new BackgroundFill(Color.WHEAT, null, null)));

         scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
         scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
         AnimationTimer timer = new AnimationTimer() {
             @Override
             public void handle(long now) {
                 update();
             }
         };
         timer.start();
         primaryStage.setTitle("My Test");
         primaryStage.setScene(scene);
         primaryStage.show();
    }

    public void update(){//TODO check if moovment is leagel here!!!!!!!!!!!
         if (isPressed(KeyCode.UP)){
             player.animation.play();
             player.animation.setOffsetY(0);//ToDo change value
             player.moveY(-2);
         }
         else if (isPressed(KeyCode.DOWN)){
             player.animation.play();
             player.animation.setOffsetY(128);//ToDo change value
             player.moveY(2);
         }
         else if (isPressed(KeyCode.RIGHT)){
             player.animation.play();
             player.animation.setOffsetY(192);//ToDo change value
             player.moveX(2);
         }
         else if (isPressed(KeyCode.LEFT)){
             player.animation.play();
             player.animation.setOffsetY(64);//ToDo change value
             player.moveX(-2);
         }
         else{
             player.animation.stop();
         }

    }

    private boolean isPressed(KeyCode key) {
         return keys.getOrDefault(key,false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
