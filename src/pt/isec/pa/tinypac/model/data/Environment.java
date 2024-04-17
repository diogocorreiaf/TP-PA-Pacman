package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.exceptions.CantSpawnFruitException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_characters.*;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.*;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.data.utils.Position;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Environment implements Serializable {
    private static final int MAX_LVL = 20;
    private static final int EXPECTED_PACMAN = 1;
    private static final int EXPECTED_WARPS = 2;
    private static final int EXPECTED_SPECIALS = 4;
    private static final int EXPECTED_FRUITSPOTS = 1;
    private static final int FRUIT_SCORE = 25;
    private static final int SPECIAL_SCORE = 10;
    private static final int BALL_SCORE = 1;
    private static final int FRUITTIME = 50;
    private static final int GHOST_POINTS = 50;
    private int INVINCIBLE_DURATION = 200;

    private int ghostWaitTime;
    public static int lineLength = 0;


    private boolean specialEaten = false; //controls the invencible state
    private boolean pacmanDied = false;


    private Ghost[] ghosts;
    private Pacman pacman;

    private Position pacmanPosition;


    private Position[] ghostsPositions;
    private Maze maze;
    private int level;

    private int score;
    private long powerupduration;
    private int fruitTimer;
    private boolean powerUpState = false;
    private boolean fruit;
    private int lives;
    private int ghostsEaten;


    public Environment() throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException {
        ghosts = new Ghost[4];
        ghostsPositions = new Position[4];
        initGhosts();
        level = 1;
        //durationPowerUp = 20000; // 10 seconds
        maze = null;
        loadMaze(level);
        powerupduration = INVINCIBLE_DURATION;
        lives = 2;
        fruit = false;
        fruitTimer = FRUITTIME;
        ghostsEaten = 0;
        ghostWaitTime = 50;
    }

    /**
     * used to reset the level when the pacman is eaten by a ghost
     * Reloads the maze based on the level,
     * resets all variables to its default, except lives and score
     * lives are decreased
     */
    public void resetLevel() {
        //durationPowerUp = 20000;
        ghostWaitTime = 10;
        maze = null;
        try {
            loadMaze(level);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvalidFileFormatException e) {
            throw new RuntimeException(e);
        } catch (InvalidMazeElementException e) {
            throw new RuntimeException(e);
        }
        reduceLives();
        powerupduration = INVINCIBLE_DURATION;
        fruit = false;
        fruitTimer = FRUITTIME;
        specialEaten = false;
        pacmanDied = false;
        ghostsEaten = 0;
        for(Ghost ghost : ghosts){
            ghost.resetOverlap();
        }
    }

    /**
     * @throws FileNotFoundException
     * @throws InvalidFileFormatException
     * @throws InvalidMazeElementException
     * loads the maze for the next level
     * updates level variable to match the current level
     * resets variables so the came can resume
     */
    public void loadNextLevel() throws FileNotFoundException, InvalidFileFormatException, InvalidMazeElementException {
        INVINCIBLE_DURATION -= 10;

        for(Ghost ghost : ghosts){
            ghost.reduceSpeed();
        }
        maze = null;
        level++;
        loadMaze(level);
        powerupduration = INVINCIBLE_DURATION;
        fruit = false;
        fruitTimer = FRUITTIME;
        specialEaten = false;
        pacmanDied = false;
        ghostWaitTime = 10;
        ghostsEaten = 0;
    }

    /**
     * Main function that runs the game
     * will spawn fruits on fruitspots every couple of iterations
     * if the special was eaten it will count a timer, and when its done it will set every ghosts edible state to false
     * and change the specialEaten variable to false
     * Calls on the pacmans and ghosts evolve functions for them to move
     */
    public void evolve() {
        if (!fruit) {
            fruitTimer--;
            if (fruitTimer == 0) {
                setFruit(true);
                    spawnFruit();
            }
        }
        if (specialEaten) {
            powerupduration--;
            if (powerupduration == 0) {
                specialEaten = false;
                for (Ghost ghost : ghosts) {
                    ghost.setEdible(false);
                }
            }
        }
        pacman.evolve();
        if (ghostWaitTime > 0) {
            ghostWaitTime--;
        } else {
            for (Ghost ghost : ghosts) {
                ghost.evolve();
            }
        }
    }


    /**
     * Searches for the position of a fruitspot on a map,
     * Spawns a fruit in its position,
     * If it cant find a FruitSpot on a map, because some other character is on top of it throws an exception;
     */
    private void spawnFruit() {
        try {
            Position pos = getPosition(FruitSpot.SYMBOL);
            if (pos == null)
                throw new CantSpawnFruitException("Someone is on top of the Fruit");
            addCharacter(new Fruit(), pos);
        }catch (CantSpawnFruitException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean Fruit() {
        return fruit;
    }

    public void setFruit(boolean fruit) {
        this.fruit = fruit;
    }

    public void resetFruitTimer() {
        fruitTimer = FRUITTIME;
    }

    public boolean isPacmanDied() {
        return pacmanDied;
    }

    public void setPacmanDied(boolean value) {
        pacmanDied = value;
    }

    public boolean specialEaten() {
        return specialEaten;
    }

    /**
     * Function called when the pacman eats a special ball
     * sets all ghosts edible state to true
     * sets the specialEaten variable to true
     * sets powerupduration variable
     */
    public void eatSpecial() {
        setGhostEdible(true);
        specialEaten = true;
        powerupduration = INVINCIBLE_DURATION;

    }

    public void resetSpecial() {
        specialEaten = false;
    }

    public int getNRows() {
        return maze.getMaze().length;
    }

    public int getNCols() {
        return maze.getMaze()[0].length;
    }

    public int getLives() {
        return lives;
    }

    /**
     * reduces the available lives
     */
    public void reduceLives() {
        lives--;
    }

    public Maze getMaze() {
        return maze;
    }

    public char[][] getMazeChar() {
        return maze.getMaze();
    }

    public Ghost[] getGhosts() {
        return ghosts;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getScore() {
        return score;
    }

    /**
     * @param symbol the symbol of a characther we want to find
     * @return the character position
     * looks in the maze for a specific symbol
     */
    public Position getPosition(char symbol) {
        char[][] board = maze.getMaze();

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] == symbol)
                    return new Position(j, i);

        return null;
    }

    public Position getPosition(char[][] board, char symbol) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] == symbol)
                    return new Position(j, i);

        return null;
    }

    /**
     * @param element the element were trying to look for
     * @return the position of the element
     */
    private Position getPosition(IMazeElement element) {

        return getPosition(element.getSymbol());
    }

    /**
     * @param element element were going to add to the maze
     * @param pos position we want the element to be in
     * Sets a character on a position
     */
    public void addCharacter(IMazeElement element, Position pos) {
        maze.set(pos.getY(), pos.getX(), element);
    }

    /**
     * @param y y coordinate
     * @param x x coordinate
     * @return the element in those coordinates
     */
    public IMazeElement getMazeElement(int y, int x) {
        IMazeElement element = maze.get(y, x);
        return element;
    }


    /**
     * @param direction calls the method for pacman to change his direction
     */
    public void changePacmanDirection(Direction direction) {
        pacman.setDirection(direction);
    }

    public void setPacmanPosition(Position pos) {
        this.pacmanPosition = pos;
    }

    public void setGhostsPositions(int index, Position pos) {
        ghostsPositions[index] = pos;
    }

    public int getLevel() {
        return level;
    }

    public boolean isPowerUpState() {
        return powerUpState;
    }

    public void setPowerUpState(boolean powerUpState) {
        this.powerUpState = powerUpState;
    }


    /**
     * @param mazeLevel the level of the game, that is used to find the right filename for the maze
     * loads the appropriate maze based on the level, if the current level doesnt have its own file it loads the previous
     * @throws FileNotFoundException       if the file isnt found
     * @throws InvalidFileFormatException  the format of the file isnt valid
     * @throws InvalidMazeElementException one of the chars in the file isnt a valid maze element
     */
    private void loadMaze(int mazeLevel) throws FileNotFoundException, InvalidFileFormatException, InvalidMazeElementException {
        String fileName;

        // Generate file name
        fileName = "Level";
        if (mazeLevel < 10)
            fileName += '0';
        fileName += mazeLevel + ".txt";

        try {
            maze = readMaze(fileName);
        } catch (FileNotFoundException e) {
            if (mazeLevel == 1)
                throw e;

            loadMaze(mazeLevel - 1);
        }
        // Add ghost in the ghost Zone
        for (Ghost ghost : ghosts) {
            moveGhostToGhostZone(ghost, getPosition(GhostZone.SYMBOL));
        }
        pacmanPosition = getPosition(pacman);
    }


    /**
     * @param symbol the symbol of a character were going to create
     * @param position
     * @return a new ImazeElement based on the symbol
     */
    private IMazeElement factory(char symbol, Position position) {
        switch (symbol) {
            case Wall.SYMBOL -> {
                return new Wall();
            }
            case Ball.SYMBOL -> {
                return new Ball();
            }
            case Special.SYMBOL -> {
                return new Special();
            }
            case GhostZone.SYMBOL -> {
                return new GhostZone();
            }
            case Warp.SYMBOL -> {
                return new Warp();
            }
            case Pacman.SYMBOL -> {
                pacman = new Pacman(this);
                return pacman;
            }
            case FruitSpot.SYMBOL -> {
                return new FruitSpot();
            }
            case Portal.SYMBOL -> {
                return new Portal();
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * @param fileName reads the creates a maze based on the .txt file
     * @return the maze
     * @throws FileNotFoundException       if the file isnt found
     * @throws NoSuchElementException
     * @throws InvalidFileFormatException  the format of the file isnt valid
     * @throws InvalidMazeElementException one of the chars in the file isnt a valid maze element
     */
    private Maze readMaze(String fileName) throws FileNotFoundException, NoSuchElementException, InvalidFileFormatException, InvalidMazeElementException {
        File file;
        Scanner scanner;
        char[] line;
        Stack<char[]> lines = new Stack<>();
        int row;

        // Open file
        file = new File(fileName);
        scanner = new Scanner(file);

        line = scanner.nextLine().toCharArray();
        lineLength = line.length;
        lines.push(line);

        while (scanner.hasNext()) {
            lines.push(scanner.nextLine().toCharArray());

            if (lines.peek().length < lineLength)
                throw new InvalidFileFormatException(fileName);
        }

        Maze maze = new Maze(lines.size(), lineLength);
        row = lines.size() - 1;

        while (!lines.empty()) {
            line = lines.pop();

            for (int col = 0; col < line.length; col++) {
                IMazeElement element = factory(line[col], new Position(col, row));

                if (element == null)
                    throw new InvalidMazeElementException(line[col]);

                maze.set(row, col, element);
            }

            row--;
        }

        if(!isValid(maze.getMaze())) {
            throw new InvalidFileFormatException("Content of maze file does not match the requirements.");
        }

        return maze;
    }

    /**
     * initializes ghosts
     */
    private void initGhosts() {
        ghosts[Blinky.INDEX] = new Blinky(this);
        ghosts[Inky.INDEX] = new Inky(this);
        ghosts[Pinky.INDEX] = new Pinky(this);
        ghosts[Clyde.INDEX] = new Clyde(this);
    }


    /**
     * Checks if the borders of the maze are all walls
     * @return True if theyre all walls, false if not
     */
    private boolean checkBorders(char[][] board) {
        if (board.length == 0 || board[0].length == 0)
            return false;

        // Check first and last rows
        for (int i = 0; i < board[0].length; i++)
            if (board[0][i] != Wall.SYMBOL ||
                    board[board.length - 1][i] != Wall.SYMBOL)
                return false;

        // Check first and last cols
        for (char[] chars : board) // Get all lines
            if (chars[0] != Wall.SYMBOL ||
                    chars[chars.length - 1] != Wall.SYMBOL)
                return false;

        return true;
    }

    /**
     * Checks if the number of maze objects is within the expected number such as warps and specials
     *
     * @param symbol        Representing the object intended to check
     * @param expectedCount Number of times the object is expected to show up
     * @return
     */
    private boolean validCount(char[][] board, char symbol, int expectedCount) {
        int count = 0;

        for (char[] row : board)
            for (char element : row)
                if (element == symbol)
                    count++;

        return count == expectedCount;
    }

    /**
     * Checks if the portal the ghosts pass through is adjacent to the GhostZone
     */
    private boolean validPortal(char[][] board) {
        Position position = getPosition(board, Portal.SYMBOL);
        if (position == null)
            return false;

        return (position.getY() - 1 >= 0 && board[position.getY() - 1][position.getX()] == GhostZone.SYMBOL) ||
                (position.getY() + 1 < board.length && board[position.getY() + 1][position.getX()] == GhostZone.SYMBOL) ||
                (position.getX() - 1 >= 0 && board[position.getY()][position.getX() - 1] == GhostZone.SYMBOL) ||
                (position.getX() + 1 < board[0].length && board[position.getY()][position.getX() + 1] == GhostZone.SYMBOL);
    }

    /**
     * @return cheks if the number of each element is within the expected count
     */
    public boolean isValid(char[][] board) {
        return checkBorders(board) &&
                validCount(board, Pacman.SYMBOL, EXPECTED_PACMAN) &&
                validCount(board, Warp.SYMBOL, EXPECTED_WARPS) &&
                validCount(board, FruitSpot.SYMBOL, EXPECTED_FRUITSPOTS) &&
                validCount(board, Special.SYMBOL, EXPECTED_SPECIALS) &&
                validPortal(board);
    }



    /**
     * @param edible the state of the ghosts on the maze if they can be eaten or not by pacman
     * Changes the states of the ghosts based on the parameter
     */
    public void setGhostEdible(boolean edible) {
        for (Ghost ghost : ghosts) {
            ghost.setEdible(edible);
        }
    }


    /**
     * @param ghost the ghost character we want to send to the ghost zone
     * @param ghostPosition position of a ghostZone to move the character to
     *Moves a character to a GhostZone, resets its Path, and stores its position
     */
    public void moveGhostToGhostZone(Ghost ghost, Position ghostPosition) {

        IMazeElement element = ghost.getOverlapElement();

        Position ghostZonePos = getPosition(GhostZone.SYMBOL);

        addCharacter(ghost, ghostZonePos);

        ghostsPositions[ghost.getINDEX()] = ghostPosition;

        ghost.resetPath();

        if (!ghostPosition.equals(ghostZonePos)) {
            addCharacter(element, ghostPosition);
        }
    }

    /**
     * @return false if the level was not Won/ if level was won
     * checks the maze for every symbol, if it finds any Ball or Special then level hasnt been beaten yet
     */
    public boolean levelWon() {
        for (char[] row : maze.getMaze())
            for (char c : row)
                if (c == Ball.SYMBOL || c == Special.SYMBOL)
                    return false;

        return true;
    }

    /**
     * @return true if lives is at 0 so the game was lost/ false if lives >0 so the game can continue
     */
    public boolean gameLost() {
        return lives <= 0;
    }

    public boolean isLastLevel() {
        return level == MAX_LVL;
    }

    public long getPowerupduration() {
        return powerupduration;
    }
    

    /**
     * @param Symbol adds point based on the symbol of the element eaten
     */
    public void addPoints(char Symbol) {
        switch (Symbol) {
            case Fruit.SYMBOL -> score += FRUIT_SCORE;
            case Ball.SYMBOL -> score += BALL_SCORE;
            case Special.SYMBOL -> score += SPECIAL_SCORE;
        }
    }


    public Position getPacmanPosition() {
        return pacmanPosition;
    }

    public Position getGhostPosition(int index) {
        return ghostsPositions[index];
    }


    /**
     * Will teleport the pacman to the other Warp element on the map
     */
    public void warpPacman() {
        Position pos = getPosition(Warp.SYMBOL);
        setPacmanPosition(pos);
    }

    /**
     * @param ghost the ghost that was eaten by the pacman
     *              Resets the Ghost that was eaten, clears its path, its overlap and moves it to ghostzone
     *              updates the ghostEaten count and updates the score
     */
    public void eatGhost(IMazeElement ghost) {
        ((Ghost)ghost).resetPath();
        ((Ghost)ghost).resetOverlap();
        ghostsEaten++;
        score += GHOST_POINTS * (ghostsEaten);
    }

}
