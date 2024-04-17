package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;

public class Clyde extends Ghost {
    public static final char SYMBOL = 'c';
    public static final int INDEX = 3;

    public Clyde(Environment environment) {
        super(environment);
    }




    @Override
    public char getSymbol() {
        return SYMBOL;
    }
    public int getINDEX() {
        return INDEX;
    }

}
