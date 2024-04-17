package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.IState;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

public class PausedState extends StateAdapter {
    public PausedState(Context context, EnvironmentManager data) {

        super(context, data);
        previousState = context.getState();
    }

    private IState previousState;

    @Override
    public State getState() {
        return State.PAUSED;
    }

    /**
     * @return true
     * Changes the state to the Normal State
     */
    public boolean start() {
        changeState(previousState);
        return true;
    }

    public boolean quit(){

        changeState(new FinishedState(context,environmentManager));
        //context.stopEngine();
        return true;
    }

}
