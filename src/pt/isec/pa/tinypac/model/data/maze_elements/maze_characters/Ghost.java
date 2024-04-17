package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.GhostZone;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.Portal;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.Wall;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.Warp;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.data.utils.Position;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Ghost extends MazeCharacter {
    protected Position next;
    private static int SPEED = 20;
    private  int evolverMoveN;

    protected static final int START_TIME = 5;
    protected boolean waiting;
    protected long start;

    protected Stack<Position> path;

    protected boolean edible;

    public Ghost(Environment environment) {
        super(environment);
        overlapElement = new GhostZone();
        waiting = true;
        start = 0;
        path = new Stack<>();
        next = null;
        edible = false;
        direction = getRandomDirection();
        evolverMoveN = SPEED;
    }


    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public boolean isEdible() {
        return edible;
    }


    /**
     * clears the path that the ghosts have stored
     */
    public void resetPath() {
        path.clear();
    }

    abstract public int getINDEX();

    /**
     * @param environment the class that stores the maze and ghosts and its positions
     * @param currentTime unused
     * @param myPos the current position of the ghost trying to move
     * Calls on the updatePosition function to try to move the gost to a new position,
     * Depending on updatePosition's return it will try again with a new direction, if all directions fail it will invert and make the ghost turn back
     * @return the position that ghost was able to move to
     */
    @Override
    protected Position move(Environment environment, long currentTime, Position myPos) {
        Position next = updatePosition(environment, currentTime, myPos);

        if (myPos == next) {
            // Not possible to move in the current direction
            ArrayList<Direction> possibleDirection = new ArrayList<>();
            switch (getDirection()) {
                case UP, DOWN -> {
                    possibleDirection.add(Direction.LEFT);
                    possibleDirection.add(Direction.RIGHT);
                }
                case LEFT, RIGHT -> {
                    possibleDirection.add(Direction.UP);
                    possibleDirection.add(Direction.DOWN);
                }
            }
            while (!possibleDirection.isEmpty()) {
                Direction dir = getRandomDirection(possibleDirection);
                possibleDirection.remove(dir);
                setDirection(dir);
                next = updatePosition(environment, currentTime, myPos);
                if (myPos != next) {
                    return next;
                }
            }
            // Not possible to move, needs to get back
            invertDirection();
            next = updatePosition(environment, currentTime, myPos);
            return next;
        }
        return next;
    }

    /**
     * @param environment class where the maze and its elements are stored
     * @param pos position that the Ghost is attempting to move
     * @return true if able to move to that position / false if not possible
     */
    @Override
    protected boolean canMove(Environment environment, Position pos) {

        IMazeElement element = environment.getMazeElement(pos.getY(), pos.getX());

        switch (element.getSymbol()) {
            case Wall.SYMBOL, Warp.SYMBOL -> {
                return false;
            }
        }
        if (overlapElement.getSymbol() == GhostZone.SYMBOL) {
            // inside GhostZone it can move inside and go to the portal
            switch (element.getSymbol()) {
                case GhostZone.SYMBOL, Portal.SYMBOL -> {
                    return true;
                }
            }
            // Ghost can't overlap each other
            return !(element instanceof Ghost);
        } else {
            switch (element.getSymbol()) {
                case GhostZone.SYMBOL, Portal.SYMBOL -> {
                    return false;
                }
            }

            // Ghost can't overlap each other
            return !(element instanceof Ghost);
        }
    }

    /**
     * resets the element the Ghost overlapped, used when moving the ghost to a GhostZone,
     */
    public void resetOverlap(){
        overlapElement = new GhostZone();
    }

    /**
     * Main movement function, responsible for moving the ghost to a new position, storing its the element its going to overlap
     * setting the previous overlapped element,and dealing with the pacman
     * Theres 2 types of movement based on the var Edible, if not edible then it moves as normal and if it touches the pacman
     * causes the level to reset,
     */
    @Override
    public void evolve() {
        if (evolverMoveN > 0) {
            evolverMoveN--;
            return;
        }
        if (!isEdible()) {
            Position myPos = environment.getGhostPosition(getINDEX());
            Position next = move(environment, 0, myPos);
            if (overlapElement instanceof Pacman) {
                environment.setPacmanDied(true);
                overlapElement = new GhostZone();
                return;
            }
            environment.setGhostsPositions(getINDEX(), next);
            path.add(myPos);
        } else {
            if(!path.empty()){
                Position myPos = environment.getGhostPosition(getINDEX());
                Position next = path.pop();
                moveEdible(environment,next,myPos);
            }
            else {
                this.edible = false;
            }
        }
        evolverMoveN = SPEED;
    }

    /**
     * @param environment
     * @param next position for the Ghost to move to
     * @param myPos current ghost position
     * Deals with Ghost movement when in an Edible state back to its original position one step at a time,
     * until it reaches, when it does toggles the edible state to false.
     * Again stores its overlapped element and sets the one it had stored
     */
    private void moveEdible(Environment environment, Position next, Position myPos) {
        IMazeElement element = environment.getMazeElement(next.getY(),next.getX());
        if (element instanceof Ghost) return;

        if(element instanceof Pacman){
            Position ghostZonePos = environment.getPosition(GhostZone.SYMBOL);
            environment.moveGhostToGhostZone(this,ghostZonePos);
            environment.eatGhost(this);
        }
        else{
            environment.setGhostsPositions(getINDEX(),next);
            environment.addCharacter(this,next);
            environment.addCharacter(overlapElement,myPos);
            overlapElement = element;
        }

    }

    /**
     * @param environment
     * @param currentTime
     * @param myPos current position of ghost
     * Checks if Ghost can move in Moves the Ghost on the map, sets its previous position with the overlapelement,
     * stores a new overlap element
     * @return my Pos if ghost was unable to move / Next the position the ghost was able to move to
     */
    protected Position updatePosition(Environment environment, long currentTime, Position myPos) {
        if(direction == Direction.NONE) {
            return myPos;
        }
        //Position myPos = getPosition(maze, getSymbol());
        Position next = myPos.nextPosition(direction);
        if(canMove(environment, next)){
            //position.updatePosition(direction, speed, elapsedTime(currentTime));
            IMazeElement element = environment.getMazeElement(next.getY(), next.getX());
            environment.addCharacter(this,next);
            environment.addCharacter(overlapElement,myPos);
            overlapElement = element;
            return next;
        }
        return myPos;
    }
    public void reduceSpeed(){
        SPEED --;
    }
}
