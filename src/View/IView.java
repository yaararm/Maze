package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;


public interface IView {

    /**
     * init viewModel and connect it to view layout
     * @param viewModel - viewModel
     */
    void setViewModel(MyViewModel viewModel);

    /**
     * display Maze On App Screen
     * @param maze - 2D represent of the maze
     */
    void displayMaze(int[][] maze);

    /**
     *
     * @param lenght
     */
    void displayCharecter(int lenght);

    /**
     * generate new maze
     * @param i - row length
     * @param j - column length
     */
    void generateMaze(int i, int j);

    /**
     * get hint. show next recommended step
     */
    void getHint();

    /**
     * reveal all solution path to the goal position of the maze
     */
    void revealSolution();

    /**
     * event handler for Keys Press
     * @param keyEvent key event
     */
    void KeyPressed(KeyEvent keyEvent);

    /**
     * set action for stage resize event
     * @param scene
     */
    void setResizeEvent(Scene scene);

    /**
     * configure "About Us" display
     * @param actionEvent
     */
    void AboutUs(ActionEvent actionEvent);

    /**
     * configure "About The Game" display
     * @param actionEvent action event
     */
    void AboutTheGame(ActionEvent actionEvent);

    /**
     * configure "How To Play" display
     * @param actionEvent action event
     */
    void HowToPlay(ActionEvent actionEvent);

    /**
     * set game difficulty level
     * @param diff - new difficulty level
     */
    void setDifficulty(String diff);

    /**
     * Save current maze to file using file chooser
     * @param actionEvent - action event
     */
    void saveMazeToFile(ActionEvent actionEvent);

    /**
     * Load current maze to file using file chooser
     * @param actionEvent- action event
     */
    void openExistMaze(ActionEvent actionEvent);

    /**
     * configure mute button action
     * @param actionEvent - action event
     */
    void mute(ActionEvent actionEvent);

    /**
     * configure quit (exit) the game
     * @param actionEvent
     */
    void quitFunction(ActionEvent actionEvent);


}
