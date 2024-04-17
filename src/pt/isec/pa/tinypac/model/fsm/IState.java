package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;

import java.io.FileNotFoundException;

public interface IState {

    void evolve();
    boolean start();
    boolean nextLevel() throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException;
    boolean powerUp(long currentime);
    boolean powerDown();
    boolean freeGhosts();
    boolean restartLevel();
    boolean touchGhost();
    boolean quit();
    boolean pause();
    boolean error();
    boolean finishGame();



    State getState();


}
