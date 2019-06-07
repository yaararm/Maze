package Model;

import Server.Server;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.DepthFirstSearch;
import algorithms.search.MazeState;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
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
import test.PrintableMazeSolution;

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
        characterPositionRow = realMaze.getStartPosition().getRowIndex();
        characterPositionColumn = realMaze.getStartPosition().getColumnIndex();

        setChanged();
        notifyObservers(1);

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
        return mazeToreturn;
    }

    @Override
    public void moveCharacter(KeyCode movement) { //ToDo make checks here!!
        try {
            switch (movement) {
                case UP:
                    if (!realMaze.isWall(characterPositionRow - 1, characterPositionColumn)) {
                        characterPositionRow--;
                    }
                    break;
                case DOWN:
                    if (!realMaze.isWall(characterPositionRow + 1, characterPositionColumn)) {
                        characterPositionRow++;
                    }
                    break;
                case RIGHT:
                    if (!realMaze.isWall(characterPositionRow, characterPositionColumn + 1)) {
                        characterPositionColumn++;
                    }
                    break;
                case LEFT:
                    if (!realMaze.isWall(characterPositionRow, characterPositionColumn - 1)) {
                        characterPositionColumn--;
                    }
                    break;
            }
        }
        catch (Exception e){
            System.out.println(e+"");
        }
        setChanged();
        notifyObservers(2);

    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public Solution getSolution() {
        Position realStartPoint = realMaze.getStartPosition();
        realMaze.setStartPosition(new Position(characterPositionRow, characterPositionColumn));
        //ToDo check Preferences here!!!!
        DepthFirstSearch d = new DepthFirstSearch();
        Solution s = d.solve(new SearchableMaze(realMaze));
        realMaze.setStartPosition(realStartPoint);
        return s;
    }

    public int[][] getAllSolution() {
        int[][] mazeToreturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        Solution s = getSolution();
        for (int i = 1; i < s.getSolutionPath().size(); i++) {
            Position p = ((MazeState) s.getSolutionPath().get(i)).getCurrentP();
            mazeToreturn[p.getColumnIndex()][p.getRowIndex()] = 1;
        }
        return mazeToreturn;
    }

    public int[][] getNextStep() {
        int[][] mazeToreturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        Solution s = getSolution();
        Position p = ((MazeState) s.getSolutionPath().get(1)).getCurrentP();
        mazeToreturn[p.getColumnIndex()][p.getRowIndex()] = 1;

        //ToDo change part B and set printableMaze mehod to be public- maybe set new func to return the new sol

        return mazeToreturn;

    }
}
