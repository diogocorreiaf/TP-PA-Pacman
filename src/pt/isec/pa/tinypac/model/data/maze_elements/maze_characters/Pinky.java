package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;

public class Pinky extends Ghost {
    public static final char SYMBOL = 'p';
    public static final int INDEX = 2;

    public Pinky(Environment environment) {
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
