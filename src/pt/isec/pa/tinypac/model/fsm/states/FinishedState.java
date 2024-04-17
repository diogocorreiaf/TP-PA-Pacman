package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;

public class FinishedState extends StateAdapter {
    public FinishedState(Context context, EnvironmentManager data) {
        super(context, data);
    }

    @Override
    public State getState() {
        return State.FINISHED;
    }


}
