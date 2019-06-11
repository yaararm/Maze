package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ChracterDisplayer extends Canvas {
    private int ArraySize;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private double cellHeight, cellWidth;

    public void setArraySize(int size) {
        this.ArraySize = size;
        redraw();
    }

    public void setSize(double newheight, double newWidth) {
        setHeight(Math.min(newheight, newWidth));
        setWidth(Math.min(newheight, newWidth));
        redraw();
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void redraw() {

        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / ArraySize;
        double cellWidth = canvasWidth / ArraySize;

        try {
            Image wallImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());

            //Draw character
            gc.drawImage(wallImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //region Properties
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();

    public String getImageFileNameSolution() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.ImageFileNameCharacter.set(imageFileNameSolution);
    }


    //endregion
}


