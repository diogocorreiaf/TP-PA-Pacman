package pt.isec.pa.tinypac.model.data.maze_elements.maze_objects;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Special implements IMazeElement {
    public static final char SYMBOL = 'O';
    @Override
    public char getSymbol() {
        return SYMBOL;
    }


}
