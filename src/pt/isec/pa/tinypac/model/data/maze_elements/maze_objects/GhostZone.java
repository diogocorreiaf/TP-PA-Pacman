package pt.isec.pa.tinypac.model.data.maze_elements.maze_objects;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class GhostZone implements IMazeElement {
    public static final char SYMBOL = 'y';
    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
