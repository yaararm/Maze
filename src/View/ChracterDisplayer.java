/**
 * this class represent the character displays
 */
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
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    /**
     * this function set the array size
     * @param size
     */
    public void setArraySize(int size) {
        this.ArraySize = size;
        redraw();
    }

    /**
     * this function set the sizes of the character
     * @param newheight
     * @param newWidth
     */
    public void setSize(double newheight, double newWidth) {
        setHeight(Math.min(newheight, newWidth));
        setWidth(Math.min(newheight, newWidth));
        redraw();
    }
    /**
     * this function set the character Position
     * @param row
     * @param column
     */
    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    /**
     *
     * @return Character Position Row
     */
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }
    /**
     *
     * @return Character Position Column
     */
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    /**
     * this function draw the character on the maze
     */
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
   /**
     *
     * @return the file image of the character
     */
    public String getImageFileNameSolution() {
        return ImageFileNameCharacter.get();
    }
    /**
     * this function set the image of the character
     * @param imageFileNameSolution
     */
    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.ImageFileNameCharacter.set(imageFileNameSolution);
    }


    //endregion
}


