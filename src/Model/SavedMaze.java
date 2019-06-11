/**
 * this class represent the object of the saved mazed- include maze itself and character position
 */
package Model;

import algorithms.mazeGenerators.Maze;

import java.io.Serializable;

public class SavedMaze implements Serializable {

    private Maze maze;
    private int rowPosition;
    private int columnPosition;

    public SavedMaze(Maze maze, int row, int column){
        this.maze = maze;
        this.columnPosition = column;
        this.rowPosition = row;
    }

    /**
     *
     * @return the maze
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     *
     * @return the row position of the character
     */
    public int getRowPosition() {
        return rowPosition;
    }

    /**
     *
     * @return the column position of the character
     */
    public int getColumnPosition() {
        return columnPosition;
    }

    /**
     * set the maze
     * @param maze
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * set row position
     * @param rowPosition
     */
    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    /**
     * set column position
     * @param columnPosition
     */
    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }
}
