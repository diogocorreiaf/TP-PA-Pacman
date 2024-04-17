package pt.isec.pa.tinypac.model.data.maze_elements.maze_characters;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.*;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.data.utils.Position;

public class Pacman extends MazeCharacter  {
    public static final char SYMBOL = 'M';
    private static final int SPEED = 15;
    private static boolean warped;
    private static int evolverMoveN;

    public Pacman(Environment environment) {
        super(environment);
        overlapElement = new Empty();
        direction = Direction.LEFT;
        evolverMoveN = SPEED;
        warped = false;
    }

    public void clearOverlapElement() {
        overlapElement = new Empty();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    /**
     * @param environment
     * @param pos position pacman is trying to move to
     * Checks the type of Element in the next position
     * @return true if able to move to that position / false it not
     */
    @Override
    protected boolean canMove(Environment environment, Position pos) {
        IMazeElement element = environment.getMazeElement(pos.getY(), pos.getX());
        switch (element.getSymbol()) {
            case Wall.SYMBOL:
            case Portal.SYMBOL:
            case GhostZone.SYMBOL:
                return false;
        }
        return true;
    }

    /**
     * @param environment
     * @param currentTime
     * @param myPos current pacmanposition
     * @return the position the pacman moved to, even if its the same one
     */
    @Override
    protected Position move(Environment environment, long currentTime, Position myPos) {

        return updatePosition(environment, currentTime, myPos);
    }

    /**
     * Main movement function, calls on other functions to execute the move, and triggers certain events,
     * Like the pacman dying, adding points based the element eaten, or teleporting the pacman with warp;
     * Pacmanm moves every 15 times the function is called
     */
    @Override
    public void evolve() {
        if (evolverMoveN > 0)
            evolverMoveN--;
        else {
            Position myPos = environment.getPacmanPosition();
            Position next = move(environment, 0, myPos);
            environment.setPacmanPosition(next);
            //action based on element eaten

            if(overlapElement instanceof Ghost) {
               if( !((Ghost) overlapElement).isEdible()){
                   environment.setPacmanDied(true);
                   overlapElement = new Empty();
                   return;
               }
                else{
                    Position ghostZonePos = environment.getPosition(GhostZone.SYMBOL);
                    environment.eatGhost(overlapElement);
                    environment.moveGhostToGhostZone(((Ghost) overlapElement),ghostZonePos);
                }
            }
            switch (overlapElement.getSymbol()) {
                case Ball.SYMBOL -> {
                    environment.addPoints(Ball.SYMBOL);
                }
                case Fruit.SYMBOL -> {
                    environment.addPoints(Fruit.SYMBOL);
                    environment.setFruit(false);
                    environment.resetFruitTimer();
                }
                case Special.SYMBOL -> {
                    environment.addPoints(Special.SYMBOL);
                    environment.eatSpecial();
                    environment.addCharacter(new Empty(), myPos);
                }
                case Warp.SYMBOL -> {
                    environment.warpPacman();
                    environment.addCharacter(new Warp(),next);
                    warped = true;
                }
                default -> {
                }
            }
            evolverMoveN = SPEED;
        }
    }

    /**
     * @param environment
     * @param currentTime
     * @param myPos current pacman position
     * @return the position pacman moved to, even if its the same one
     * If pacman has no direction then it wont move
     * Checks if pacman can move to the direction it has set, if so deals with the maze accordingly, stores the element
     * it overlapped
     */
    protected Position updatePosition(Environment environment, long currentTime, Position myPos) {
        if(direction == Direction.NONE) {
            return myPos;
        }
        Position next = myPos.nextPosition(direction);
        if(canMove(environment, next)){
            IMazeElement element = environment.getMazeElement(next.getY(), next.getX());
            environment.addCharacter(this,next);
            switch (overlapElement.getSymbol()){
                case Fruit.SYMBOL -> environment.addCharacter(new FruitSpot(),myPos);
                case Warp.SYMBOL -> environment.addCharacter(new Warp(),myPos);
                default -> environment.addCharacter(new Empty(),myPos);
            }
            overlapElement = element;
            return next;
        }
        overlapElement = new Empty();
        return myPos;
    }
}
