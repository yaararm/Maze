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

public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private double cellHeight,cellWidth;
    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    /**
     *
     * @return cell height
     */
    public double getCellHeight() {
        return cellHeight;
    }
    /**
     *
     * @return cell width
     */
    public double getCellWidth() {
        return cellWidth;
    }
    /**
     * this function set the maze
     * @param maze
     */
    public void setMaze(int[][] maze) {
        this.maze = maze;
        redraw();
    }
    /**
     * this function set the size of the cell
     * @param newHeight
     * @param newWidth
     */
    public void setSize(double newHeight,double newWidth){
        setHeight(Math.min(newHeight,newWidth));
        setWidth(Math.min(newHeight,newWidth));
        redraw();
    }
    //public void setCharacterPosition(int row, int column) {
    //    characterPositionRow = row;
    //    characterPositionColumn = column;
    //    redraw();
    //}
    /**
     *
     * @return  Character Position Row
     */
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    /**
     *
     * @return CharacterPositionColumn
     */
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
    /**
     * this function draw the maze board
     */
    public void redraw() {
        if (maze != null) {
            double canvasHeight =getHeight();
            double canvasWidth = getWidth();


            cellHeight = canvasHeight / maze.length;
            cellWidth = canvasWidth / maze[0].length;

            try {
                Image goalImage = new Image(new FileInputStream(ImageFileNameGoal.get()));
                Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());


                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wallImage, i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        }
                        if (maze[i][j] == 2) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(goalImage, i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        }

                    }
                }

            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     *
     * @return the image of the wall
     */
    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }
    /**
     *
     * @return the target image for the end of the maze
     */
    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }
    /**
     * set the image file of the wall
     * @param imageFileNameWall
     */
    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }
    /**
     * set the image file of the end of the maze
     * @param imageFileNameGoal
     */
    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }




}

