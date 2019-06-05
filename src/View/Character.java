package View;


import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {

    //character configuration
    ImageView imageview;
    int count = 9;
    int COLUMNS = 9;
    int OFFSET_X = 0;
    int OFFSET_Y = 0;
    int WIDTH = 64;
    int HEIGHT = 64;
    private StringProperty ImageFileName = new SimpleStringProperty();


    SpriteAnimation animation;

    public Character() {
        this.imageview = new ImageView(new Image("file:resources/Images/download.png"));
        this.imageview.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        animation = new SpriteAnimation(imageview, Duration.millis(500), count, COLUMNS, OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
        getChildren().addAll(imageview);

    }

    public void moveX(int x) {
        boolean right = x > 0 ? true : false;
        for (int i = 0; i < Math.abs(x); i++) {
            if (right) {
                //  if (this.getTranslateX() <= this.get)
                    this.setTranslateX(this.getTranslateX() + 1); //ToDo calculate value for moovment
            } else if (this.getTranslateX() >= 0) { //prevent leave the screen
                this.setTranslateX(this.getTranslateX() - 1);
            }
        }


    }

    public void moveY(int y) {
        boolean right = y > 0 ? true : false;
        for (int i = 0; i < Math.abs(y); i++) {
            if (right) this.setTranslateY(this.getTranslateY() + 1); //ToDo avoid leave the screen some way too here!
            else if (this.getTranslateY() >= 0) { //prevent leave the screen
                this.setTranslateY(this.getTranslateY() - 1);
            }

        }

    }
    public void scale() {
        ScaleTransition st = new ScaleTransition(Duration.millis(1), this);
        st.setToX(5);
        st.setToY(5);
        st.play();
        //ToDo re-set move lenght now
    }
    //region Properties

    public String getImageFileNameWall() {
        return ImageFileName.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileName.set(imageFileNameWall);
    }


    //endregion
}
