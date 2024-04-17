package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

import java.io.FileNotFoundException;

public class InvincibleState extends StateAdapter {
    public InvincibleState(Context context, EnvironmentManager data) {
        super(context, data);
    }


    /**
     * State evolve function, that changes the state of the states machine based on some possible transitions
     * if no transition occurs then calls the environmentManager evolve function
     */
    @Override
    public void evolve()
    {
        if(powerUpOver()){
            changeState(new NormalState(context,environmentManager));
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

        environmentManager.evolve();
    }

    private boolean powerUpOver() {
        return environmentManager.powerUpOver();
    }

    @Override
    public State getState() {
        return State.INVINCIBLE;
    }

    public boolean levelWon() {
        return environmentManager.levelWon();
    }

    public boolean isLastLevel() {
        return environmentManager.isLastLevel();
    }

    @Override
    public boolean pause() {
        changeState(new PausedState(context, environmentManager));
        return true;
    }


}
