/**
 * this class represent the solution displays
 */
package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class SolutionDisplayer extends Canvas {
    private int[][] solution;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private double offsetX;
    private double offsetY;
    private double posX;
    private double posY;

    public void setPos(double posX, double posY) {
        this.posX += posX;
        this.posY += posY;
    }
    public void setOffset(double x, double y) {//int row,int col, double paneW, double paneH) {
        this.offsetX = x;
        this.offsetY = y;
        redraw();
    }
    public void resetLocation() {
        posY = 0;
        posX = 0;
        offsetY = 0;
        offsetX = 0;
    }
    //region Properties
    private StringProperty imageFileNameSolution = new SimpleStringProperty();

    /**
     * this function set the matrix of the solution
     * @param mazeSol
     */
    public void setSolution(int[][] mazeSol) {
        this.solution = mazeSol;
        redraw();
    }
    /**
     * this function set the size of the cell of the solution
     * @param newheight
     * @param newWidth
     */
    public void setSize(double newheight, double newWidth) {
        setHeight(Math.min(newheight, newWidth));
        setWidth(Math.min(newheight, newWidth));
        redraw();
    }
    /**
     * this function set the Character Position
     * @param row
     * @param column
     */
    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }
    /**
     * @return the Character Position row
     */
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }
    /**
     * @return the Character Position column
     */
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
    /**
     * this function draw the solution path on the maze board
     */
    public void redraw() {
        if (solution != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / solution.length;
            double cellWidth = canvasWidth / solution[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(imageFileNameSolution.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < solution.length; i++) {
                    for (int j = 0; j < solution[i].length; j++) {
                        if (solution[i][j] == 1) {
                            //gc.drawImage(wallImage, i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, (i * cellHeight) + offsetX + posX, (j * cellWidth) + offsetY + posY, cellHeight, cellWidth);

                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  this function erase the solution path on the maze board
     */
    public void clear() {
        if (solution != null) {

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            solution = null ;
        }
    }
    /**
     *
     * @return the image file of the solution path
     */
    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    /**
     * this function set the image file of the solution path
     * @param imageFileNameSolution
     */
    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }


    //endregion
}


