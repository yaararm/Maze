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

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public Maze getMaze() {
        return maze;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }
}