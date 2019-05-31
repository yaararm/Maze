package Model;

import Server.Server;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Server.Server;

public class MyModel extends Observable implements IModel {

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    Maze realMaze;
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;

    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private int characterPositionX = 0;
    private int characterPositionY = 0;

    public MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
    }


    public void startServers() {
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
    }

    public void stopServers() {
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }


    @Override
    public void generateMaze(int width, int height) {
        //Generate maze
        MyMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze m = mazeGenerator.generate(width, height);
        this.realMaze = m;

        setChanged();
        notifyObservers();

    }

    // public boolean isAWall() {
    //     return realMaze.
    // }

    @Override
    public int[][] getMaze() {
        int[][] mazeToreturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        for (int i = 0; i < realMaze.getColumnLength(); i++) {
            for (int j = 0; j < realMaze.getRowLength(); j++) {
                if (realMaze.isWall(i, j)) mazeToreturn[j][i] = 1; //Todo !!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
        realMaze.print();
        return mazeToreturn;
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
