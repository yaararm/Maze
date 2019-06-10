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

    public void setSolution(int[][] mazeSol) {
        this.solution = mazeSol;
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
                            gc.drawImage(wallImage, i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void clear() {
        if (solution != null) {

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());

        }
    }

    //region Properties
    private StringProperty imageFileNameSolution = new SimpleStringProperty();

    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }


    //endregion
}


