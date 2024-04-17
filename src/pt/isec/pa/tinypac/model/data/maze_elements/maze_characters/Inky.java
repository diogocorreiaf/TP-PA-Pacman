package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;

public class Inky extends  Ghost {
    public static final char SYMBOL = 'i';
    public static final int INDEX = 1;

    public Inky(Environment environment) {
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
