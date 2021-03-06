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
    private double offsetX;
    private double offsetY;
    private double posX;
    private double posY;
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();

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
            //gc.drawImage(wallImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
            gc.drawImage(wallImage, (characterPositionColumn * cellHeight)+ offsetX + posX, (characterPositionRow * cellWidth)+ offsetY + posY, cellHeight, cellWidth);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * set current maze location on screen
     * @param posX position X
     * @param posY position Y
     */
    public void setPos(double posX, double posY) {
        this.posX += posX;
        this.posY += posY;
    }

    /**
     * set offset location from current one. triggerd on mouse drag event
     * @param x - offset X
     * @param y - offset Y
     */
    public void setOffset(double x, double y) {//int row,int col, double paneW, double paneH) {
        this.offsetX = x;
        this.offsetY = y;
        redraw();
    }

    /**
     * reset position and offset values
     */
    public void resetLocation() {
        posY = 0;
        posX = 0;
        offsetY = 0;
        offsetX = 0;
    }

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
     * @param newHeight
     * @param newWidth
     */
    public void setSize(double newHeight, double newWidth) {
        setHeight(Math.min(newHeight, newWidth));
        setWidth(Math.min(newHeight, newWidth));
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

    //region String Property for Binding

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


