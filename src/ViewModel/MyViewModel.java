package ViewModel;


import Model.IModel;

import View.MazeDisplayer;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding

    public MyViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            if ((int) arg == 1) {
                characterPositionRowIndex = model.getCharacterPositionRow();
                characterPositionRow.set(model.getCharacterPositionRow() + "");
                characterPositionColumnIndex = model.getCharacterPositionColumn();
                characterPositionColumn.set(characterPositionColumnIndex + "");
                setChanged();
                notifyObservers(1);
            } else if ((int) arg == 2) {
                characterPositionRowIndex = model.getCharacterPositionRow();
                characterPositionRow.set(model.getCharacterPositionRow() + "");
                characterPositionColumnIndex = model.getCharacterPositionColumn();
                characterPositionColumn.set(characterPositionColumnIndex + "");
                setChanged();
                notifyObservers(2);
            }

        }
    }

    public void generateMaze(int width, int height) {
        model.generateMaze(width, height);
    }

    public int[][] getNextStep() {
        return model.getNextStep();
    }

    public int[][] getAllSolution() {
        return model.getAllSolution();
    }


    public void moveCharacter(KeyCode movement) {
        model.moveCharacter(movement);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public int getCharacterPositionRow() {
        return characterPositionRowIndex;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumnIndex;
    }

    public void scroll(ScrollEvent event, MazeDisplayer mazeDisplayer) { //ToDo
    }

    public void saveMazeToFile() {
        model.saveMazeToFile();
    }

    public void openExistMaze() {
        model.openExistMaze();
    }
}
