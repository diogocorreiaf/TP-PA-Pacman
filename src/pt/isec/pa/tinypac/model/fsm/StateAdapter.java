package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;

import java.io.FileNotFoundException;

public abstract class StateAdapter implements IState {

        protected Context context;
        protected EnvironmentManager environmentManager;

        protected StateAdapter(Context context, EnvironmentManager environmentManager) {
            this.context = context;
            this.environmentManager = environmentManager;
        }

        protected void changeState(IState newState) {
            context.changeState(newState);
        }


    public void evolve() {}

    @Override
    public boolean start() { return false;}
    @Override
    public boolean nextLevel() throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException { return false; }
    @Override
    public boolean powerUp(long currentime) { return false; }
    @Override
    public boolean powerDown() { return false; }
    @Override
    public boolean freeGhosts() { return false; }
    @Override
    public boolean touchGhost() { return false; }
    @Override
    public boolean quit() { return false; }
    @Override
    public boolean pause() { return false; }
    @Override
    public boolean error() { return false; }

    @Override
    public boolean finishGame() {
        return false;
    }

    @Override
    public boolean restartLevel() { return false; };


}
