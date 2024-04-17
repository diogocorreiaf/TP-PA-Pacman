package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

public class WaitingState extends StateAdapter {
    public WaitingState(Context context, EnvironmentManager environmentManager) {
        super(context, environmentManager);
    }

    @Override
    public State getState() {
        return State.WAITING;
    }

    /**
     * @return true
     * Changes the sate to a normal state so that the ghosts and pacman start moving
     */
    public boolean start() {
        context.starEngine();
        changeState(new NormalState(context, environmentManager));
        return true;
    }
}
