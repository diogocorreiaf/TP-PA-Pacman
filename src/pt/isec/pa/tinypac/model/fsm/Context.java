package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.fsm.states.PausedState;
import pt.isec.pa.tinypac.model.fsm.states.WaitingState;

import java.io.FileNotFoundException;

public class Context {

    private EnvironmentManager environmentManager;
    private IGameEngine engine;
    private IState state;

    Exception errorException;

    public Context(IGameEngine engine) throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException {
        this.environmentManager = new EnvironmentManager();
        state = new WaitingState(this, environmentManager); // Initial state
        this.engine = engine;
        errorException = null;
    }

    /**
     * @param currentTime main function responsible for running the game,
     *                    everytime its called calls on the current state to use its evolve method
     */
    public void evolve(long currentTime) {
        state.evolve();
    }

    public IState getState() {
        return state;
    }

    /**
     * @param newState the state that its meant to be set as
     *                 Changes the state to a new state, if the current state is PAUSED then it will instead load the previous state
     */
    void changeState(IState newState) {
        this.state = newState;
    }

    public Maze getMaze() {
        return environmentManager.getMaze();
    }

    public Exception getError() {
        return errorException;
    }

    public void changePacmanDirection(Direction direction) {
        environmentManager.changePacmanDirection(direction);
    }


    /**
     * Changes State to a paused State
     */
    public void pause() {
        state.pause();
        changeState(new PausedState(this, environmentManager));
    }

    public void starEngine() {
        engine.start(50);
    }

    public void stopEngine() {
        engine.stop();
    }


    public int getLives() {
        return environmentManager.getLives();
    }

    public int getScore() {
        return environmentManager.getScore();
    }

    public int getLevel() {
        return environmentManager.getLevel();
    }

    public void save() {
        environmentManager.saveGame();
    }

    public void load() {
        environmentManager.loadGame();
        changeState(new WaitingState(this, environmentManager));
    }

    public boolean existsSaveFile() {
        return environmentManager.existsSaveFile();
    }
}
