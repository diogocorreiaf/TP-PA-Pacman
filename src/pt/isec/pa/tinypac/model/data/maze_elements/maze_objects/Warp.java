package pt.isec.pa.tinypac.model.data.maze_elements.maze_objects;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Warp implements IMazeElement {
    public static final char SYMBOL = 'W';
    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
