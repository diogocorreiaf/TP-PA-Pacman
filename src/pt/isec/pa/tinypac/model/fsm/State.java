package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.states.*;

public enum State {
    WAITING, NORMAL, INVINCIBLE, PAUSED, FINISHED;

    IState getInstance(Context context, EnvironmentManager environmentManager) {
        return switch (this) {
            case WAITING  -> new WaitingState(context,environmentManager);
            case NORMAL -> new NormalState(context,environmentManager);
            case INVINCIBLE -> new InvincibleState(context, environmentManager);
            case PAUSED -> new PausedState(context, environmentManager);
            case FINISHED -> new FinishedState(context, environmentManager);
        };
    }
}

