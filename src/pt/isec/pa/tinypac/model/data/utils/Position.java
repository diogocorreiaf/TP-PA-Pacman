package pt.isec.pa.tinypac.model.data.utils;

import java.io.Serializable;

public class Position implements Serializable {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.y = y;
        this.x = x;
    }

    /**
     * @param direction the direction a character is moving
     * Based on the direction of the character is returns the coordinates of the next position
     * @return Position(x,y) the coordinates of the next position
     */
    public Position nextPosition(Direction direction)
    {
        switch (direction) {
            case UP: return new Position(x, y - 1);
            case RIGHT: return new Position(x + 1, y);
            case DOWN: return new Position(x, y + 1);
            case LEFT: return new Position(x - 1, y);
        }
        return new Position(x, y);
    }

    /**
     * @param obj
     * @return true if the positions are equal/ false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position))
            return false;

        return x == ((Position)obj).getX() && y == ((Position)obj).getY();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
