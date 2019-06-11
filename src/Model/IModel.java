/**
 * this Interface represent the demands of the model layer
 */
package Model;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public interface IModel {
    /**
     * this function generate the maze
     * @param width
     * @param height
     */
    void generateMaze(int width, int height);

    /**
     * this function move the character acording to key data
     * @param movement
     */
    void moveCharacter(KeyCode movement);

    /**
     *
     * @return the maze as matrix
     */
    int[][] getMaze();

    /**
     *
     * @return the character position row
     */
    int getCharacterPositionRow();

    /**
     *
     * @returnthe character position column
     */
    int getCharacterPositionColumn();

    /**
     *
     * @return return the hint step
     */
    int[][] getNextStep();

    /**
     *
     * @return the solution path
     */
    Solution getSolution();

    /**
     *
     * @return return full solution as matrix
     */
    int[][] getAllSolution();

    /**
     * this function save the maze
     */
    void saveMazeToFile() ;

    /**
     * this function load the maze from existing file
     */
    void openExistMaze();

    }

