package Model;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public interface IModel {
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    int[][] getNextStep();
    Solution getSolution();
    public int[][] getAllSolution();
    void saveMazeToFile() ;
    void openExistMaze();

    }

