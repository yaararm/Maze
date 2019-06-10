package Model;

import Server.Server;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
    private Solution currentSol;
    private boolean solved;

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
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{width, height};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[1000000]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        realMaze = maze;
                        //maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (
                UnknownHostException e) {
            e.printStackTrace();
        }
            characterPositionRow = realMaze.getStartPosition().getRowIndex();
            characterPositionColumn = realMaze.getStartPosition().getColumnIndex();
            solved = false;
            setChanged();
            notifyObservers(1);
    }


    @Override
    public int[][] getMaze() {
        int[][] mazeToreturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        for (int i = 0; i < realMaze.getColumnLength(); i++) {
            for (int j = 0; j < realMaze.getRowLength(); j++) {
                if (realMaze.isWall(i, j)) mazeToreturn[j][i] = 1; //Todo !!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
        mazeToreturn[realMaze.getGoalPosition().getColumnIndex()][realMaze.getGoalPosition().getRowIndex()] = 2;
        return mazeToreturn;
    }

    @Override
    public void moveCharacter(KeyCode movement) { //ToDo make checks here!!
        if(!solved) {
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
            } catch (Exception e) {
                System.out.println(e + "");
            }

        }
        if(characterPositionColumn == realMaze.getGoalPosition().getColumnIndex() &&
                characterPositionRow == realMaze.getGoalPosition().getRowIndex()){
            solved = true;
            setChanged();
            notifyObservers(3); //solved the maze!!!
        }
        else{
            setChanged();
            notifyObservers(2);
        }


    }

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }
    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    //ToDo check properties here!!!
    public  Solution getSolution() {
        Position realStartPoint = realMaze.getStartPosition();
        realMaze.setStartPosition(new Position(characterPositionRow, characterPositionColumn));
        Solution s;
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        Maze maze = realMaze;
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject();
                        realMaze.setStartPosition(realStartPoint);
                        currentSol = mazeSolution;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return currentSol;
    }

    public int[][] getAllSolution() {
        int[][] mazeToReturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        Solution s = getSolution();
        for (int i = 1; i < s.getSolutionPath().size()-1; i++) {
            Position p = ((MazeState) s.getSolutionPath().get(i)).getCurrentP();
            mazeToReturn[p.getColumnIndex()][p.getRowIndex()] = 1;
        }
        return mazeToReturn;

    }

    public int[][] getNextStep() {
        int[][] mazeToreturn = new int[realMaze.getRowLength()][realMaze.getColumnLength()];
        Solution s = getSolution();
        Position p = ((MazeState) s.getSolutionPath().get(1)).getCurrentP();
        mazeToreturn[p.getColumnIndex()][p.getRowIndex()] = 1;

        //ToDo change part B and set printableMaze mehod to be public- maybe set new func to return the new sol

        return mazeToreturn;

    }

@Override
    public void saveMazeToFile (){

    SavedMaze mySave = new SavedMaze(realMaze, getCharacterPositionRow(), getCharacterPositionColumn());

    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Maze files ", "*.maze");
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
                notifyObservers(1);

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
