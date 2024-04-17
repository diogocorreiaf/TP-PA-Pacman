package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.data.utils.Position;

import java.util.ArrayList;
import java.util.Random;

public abstract class MazeCharacter implements IMazeElement{

    protected Environment environment;
    protected Direction direction; //direction caracter moving
    //protected double speed; // Number of squares it can move per second
    //protected long previousMoveTime;
    protected IMazeElement overlapElement; // Symbol that is below the character.

    public MazeCharacter(Environment environment) {
        this.direction = Direction.NONE;
        //this.speed = 1;
        //this.previousMoveTime = 0;
        this.overlapElement = null;
        this.environment = environment;
    }

    public MazeCharacter(double speed) {
        this.direction = Direction.NONE;
        //this.speed = speed;
        //this.previousMoveTime = 0;
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public IMazeElement getOverlapElement() {
        return overlapElement;
    }

    /**
     * based on the direction of the Maze Character, it updates its new the direction with the opposite of what it had initially
     */
    public void invertDirection() {
        switch (direction) {
            case RIGHT -> {direction = Direction.LEFT;}
            case LEFT -> {direction = Direction.RIGHT;}
            case DOWN -> {direction = Direction.UP;}
            case UP -> {direction = Direction.DOWN;}
        }
    }





    /**
     * creates an array list of possible directions for the ghost to move to at level start
     * @return  a random direction from the array list
     */
    protected Direction getRandomDirection() {
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);
        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        return getRandomDirection(possibleDirections);
    }

    /**
     * @param possibleDirections an array list with all possible directions
     * @return a direction chosen at random, or none if none is possible
     */
    protected Direction getRandomDirection(ArrayList<Direction> possibleDirections)
    {
        Random random = new Random();
        if(!possibleDirections.isEmpty()) {
            return possibleDirections.get(random.nextInt(possibleDirections.size()));
        }
        return Direction.NONE;
    }

    abstract protected Position updatePosition(Environment environment, long currentTime, Position myPos);


    abstract protected boolean canMove(Environment environment, Position pos);

    abstract protected Position move(Environment environment, long currentTime, Position myPos);

    abstract public void evolve();

    @Override
    public char getSymbol() {
        return '_'; // Invalid symbol
    }
}
