package pt.isec.pa.tinypac.model.data.maze_elements.maze_objects;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class FruitSpot implements IMazeElement {
    public static final char SYMBOL = 'F';
    @Override
    public char getSymbol() {
        return 'F' ;
    }
}
