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

    public double getCellHeight() {
        return cellHeight;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
        redraw();
    }
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

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

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
    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }
    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }






    //endregion
}

