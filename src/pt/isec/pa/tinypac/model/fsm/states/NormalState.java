package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

import java.io.FileNotFoundException;

public class NormalState extends StateAdapter {
    public NormalState(Context context, EnvironmentManager environmentManager) {
        super(context, environmentManager);
    }

    @Override
    public State getState() {
        return State.NORMAL;
    }


    /**
     * State evolve function, that changes the state of the states machine based on some possible transitions
     * if no transition occurs then calls the environmentManager evolve function
     */
    public void evolve(){
        if(specialEaten()){
            changeState(new InvincibleState(context, environmentManager));
        }
        if(levelWon()){
            if(isLastLevel()){
                context.stopEngine();
                changeState(new FinishedState(context,environmentManager));
            }
            try {
                environmentManager.loadNextLevel();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InvalidFileFormatException e) {
                throw new RuntimeException(e);
            } catch (InvalidMazeElementException e) {
                throw new RuntimeException(e);
            }
            changeState(new WaitingState(context,environmentManager));
        }
        if(pacmanDied()){
            if(gameLost()){
                context.stopEngine();
                changeState(new FinishedState(context,environmentManager));
            }
            else {
                environmentManager.resetLevel();
                changeState(new WaitingState (context,environmentManager));
            }
        }
        else {
            environmentManager.evolve();
        }
    }

    private boolean isLastLevel() {
        return environmentManager.isLastLevel();
    }

    private boolean specialEaten() {
        return environmentManager.specialEaten();
    }

    private boolean pacmanDied() {
        return environmentManager.pacmanDied();
    }

    public void reduceLives() {
        environmentManager.reduceLives();
    }

    public Maze getMaze() {
        return environmentManager.getMaze();
    }

    public boolean levelWon() {
        return environmentManager.levelWon();
    }

    public boolean gameLost() {
        return environmentManager.gameLost();
    }

    @Override
    public boolean pause() {
        changeState(new PausedState(context, environmentManager));
        return true;
    }

}
