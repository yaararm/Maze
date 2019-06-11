package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;


public interface IView {
    /**
     *
     * @param viewModel
     */
    void setViewModel(MyViewModel viewModel);

    /**
     *
     * @param maze
     */
    void displayMaze(int[][] maze);

    /**
     *
     * @param lenght
     */
    void displayCharecter(int lenght);

    /**
     *
     * @param i
     * @param j
     */
    void generateMaze(int i, int j);

    void generateEasyMaze();

    void generateMediumMaze();

    void generateHardMaze();

    void getHint();

    void revealSolution();

    void KeyPressed(KeyEvent keyEvent);

    void setResizeEvent(Scene scene);

    void AboutUs(ActionEvent actionEvent);

    void AboutTheGame(ActionEvent actionEvent);

    void HowToPlay(ActionEvent actionEvent);

    void setDifficulty(String diff);

    void generateFirstMaze();

    void saveMazeToFile(ActionEvent actionEvent);

    void openExistMaze(ActionEvent actionEvent);

    void mute(ActionEvent actionEvent);

    void quitFunction(ActionEvent actionEvent);


}
