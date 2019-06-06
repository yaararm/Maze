package Model;

import Server.Server;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.DepthFirstSearch;
import algorithms.search.MazeState;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Server.Server;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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

    public Solution getSolution(){
        Position realStartPoint = realMaze.getStartPosition();
        realMaze.setStartPosition(new Position(characterPositionRow,characterPositionColumn));
        //ToDo check Preferences here!!!!
        DepthFirstSearch d = new DepthFirstSearch();
        Solution s =d.solve(new SearchableMaze(realMaze));
        realMaze.setStartPosition(realStartPoint);
        return s;
    }
    public int[][] getNextStep(){
        int[][] mazeToreturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        Solution s = getSolution();
        Position p =((MazeState)s.getSolutionPath().get(1)).getCurrentP();
        mazeToreturn[p.getColumnIndex()][p.getRowIndex()]=1;

        //ToDo change part B and set printableMaze mehod to be public- maybe set new func to return the new sol

        return mazeToreturn;

    }
    public Object getMyObject(){
        return realMaze;
    }
@Override
    public void saveMazeToFile (){

    SavedMaze mySave = new SavedMaze(realMaze, getCharacterPositionRow(), getCharacterPositionColumn());

    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    fileChooser.getExtensionFilters().add(extFilter);
    fileChooser.setTitle("Save the Maze");
    Stage window = new Stage();
    File file = fileChooser.showSaveDialog(window);
    try {
        if (file != null) {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(mySave);
            oos.close();
        }


    } catch (IOException e) {
        showAlert("File could not be saved");

    }
}
    @Override
    public void openExistMaze() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load the Maze");
    Stage window = new Stage();

       File file = fileChooser.showOpenDialog(window);

        try {
            if (file != null) {
             ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file)) ;
             SavedMaze mySave = (SavedMaze)oin.readObject();
             this.realMaze = mySave.getMaze();
             this.characterPositionRow = mySave.getRowPosition();
             this.characterPositionColumn = mySave.getColumnPosition();
                setChanged();
                notifyObservers();

         }
        } catch (IOException | ClassNotFoundException e) {
            showAlert("File could not be upload");

        }
    }

//ToDo show alert when file not exist/ file not saved
    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
}
