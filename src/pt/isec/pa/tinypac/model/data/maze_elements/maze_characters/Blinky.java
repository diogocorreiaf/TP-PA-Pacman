package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;

public class Blinky extends Ghost {
    public static final char SYMBOL = 'b';
    public static final int INDEX = 0;

    public Blinky(Environment environment) {
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
