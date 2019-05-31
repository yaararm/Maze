package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends Observable implements IModel {
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    Maze realMaze;
    private int[][] maze = { // a stub...//ToDo change to real maze
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1},
            {0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
            {1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1},
            {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1}
    };

    public MyModel() {
        //Raise the servers
        startServers();
    }

    public void startServers() {

    }

    public void stopServers() {

    }


    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    @Override
    public void generateMaze(int width, int height) {
        //Generate maze
        MyMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze m = mazeGenerator.generate(width, height);
        this.realMaze = m;

        setChanged();
        notifyObservers();

    }

    @Override
    public int[][] getMaze() {
        return maze;
    }

    @Override
    public void moveCharacter(KeyCode movement) { //ToDo make checks here!!
        switch (movement) {
            case UP:
                characterPositionRow--;
                break;
            case DOWN:
                characterPositionRow++;
                break;
            case RIGHT:
                characterPositionColumn++;
                break;
            case LEFT:
                characterPositionColumn--;
                break;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }
}
