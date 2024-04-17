package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.model.data.utils.Direction;

import java.io.*;

public class EnvironmentManager {
    private static final String FILENAME_DATA = "pacman.bin";

    Environment environment;

    public EnvironmentManager() throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException {
        environment = new Environment();
    }
public char[][] getEnvironment(){
        return environment.getMazeChar();
}

public void evolve(){
        if(environment == null)
            return;
        environment.evolve();
        return;
}

    public void resetLevel() {
        environment.resetLevel();
    }

    public void reduceLives() {
        environment.reduceLives();
    }

    public Maze getMaze() {
        return environment.getMaze();
    }
    public int getScore() {
        return environment.getScore();
    }

    public void loadNextLevel() throws FileNotFoundException, InvalidFileFormatException, InvalidMazeElementException {
        environment.loadNextLevel();
    }

    public boolean levelWon() {
        return environment.levelWon();
    }

    public boolean isLastLevel() {
        return environment.isLastLevel();
    }

    public boolean gameLost() {
        return environment.gameLost();
    }

    public int getLives(){
        return environment.getLives();
    }
    public void changePacmanDirection(Direction direction){
        environment.changePacmanDirection(direction);
    }

    public boolean specialEaten() {
        return environment.specialEaten();
    }

    public boolean pacmanDied() {

        return environment.isPacmanDied();
    }

    public boolean powerUpOver() {
         return !environment.specialEaten();
    }

    public int getLevel() {
        return environment.getLevel();
    }

    /**
     * saves the current environment to file called pacman.bin
     */
    public void saveGame() {
        String filePath = FILENAME_DATA;

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(environment);

            fos.close();
            os.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a new environment from the a file pacman.bin
     */
    public void loadGame(){
        String filePath = FILENAME_DATA;

        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream is = new ObjectInputStream(fis);
            Environment newEnv = (Environment) is.readObject();
            environment = newEnv;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true if the save file exists/false otherwise
     */
    public boolean existsSaveFile() {
        File file = new File(FILENAME_DATA);
        return file.exists();
    }
}

