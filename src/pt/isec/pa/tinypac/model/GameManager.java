package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.IState;
import pt.isec.pa.tinypac.model.fsm.State;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager implements IGameEngineEvolve, Serializable {

    public static final String PROP_STATE = "_state_";
    public static final String PROP_UIUPDATE = "_ui_";
    private Context context;
    private final GameEngine engine;
    private int myScore;
    private boolean gameEnd;
    private String myName;

    private PropertyChangeSupport pcs;

    public GameManager() throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException {
        this.engine = new GameEngine();
        this.context = new Context(engine);
        this.pcs = new PropertyChangeSupport(context);
        engine.registerClient(this);
        gameEnd = false;
    }

    public void addClient(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }

    public void removeClient(String property, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(property, listener);
    }


    public boolean start() {
        gameEnd = false;
        return context.getState().start();
    }

    public void pause() {
        context.getState().pause();
    }

    public boolean quit() {
        return context.getState().quit();
    }

    public int getLives() {
        return context.getLives();
    }

    public int getScore() {
        return context.getScore();
    }

    public boolean existsSaveFile() {
        return context.existsSaveFile();
    }

    public void loadGame() {
        context.load();
        pcs.firePropertyChange(PROP_UIUPDATE, null, context.getMaze().getMaze());
    }

    public void save() {
        context.save();
    }

    /**
     * @param gameEngine the engine that will calls on this evolve state
     * @param currentTime
     * Calls on the state machine Context to evolve the game state
     * if the State is finished meaning the game is over it will check it calls on the top 5 method
     * calls the firePropertyChange methods so that the UI will update
     */
    public void evolve(IGameEngine gameEngine, long currentTime) {
        context.evolve(currentTime);
        pcs.firePropertyChange(PROP_UIUPDATE, null, context.getMaze().getMaze());
        pcs.firePropertyChange(PROP_STATE, null, null);


        if (context.getState().getState() == State.FINISHED) {
            if (!gameEnd) {
                gameEnd = true;
                top5();
                context.stopEngine();
            }
        }

    }

    public void changePacmanDirection(Direction direction) {
        context.changePacmanDirection(direction);
    }

    public char[][] getMaze() {
        return context.getMaze().getMaze();
    }


    /**
     * Function that opens the file top5.bin and reads it if any of the scores set on the file is lower than the current game's score
     * then it will update the top5 file
     */
    public void top5() {
        String filePath = "top5.bin";
        Scanner s = new Scanner(System.in);
        myScore = getScore();
        boolean updated = false;
        List<Integer> scores = getTop5Scores();
        List<String> names = getTop5Names();

        for (int i = 0; i < scores.size(); i++) {
            if (myScore > scores.get(i)) {
                scores.set(i, myScore);
                System.out.println("Congrats! Your score is good enough for the TOP5.\nPlease insert your name below:");
                String name = s.nextLine();
                names.set(i, name);
                updated = true;
                break;
            }
        }
        if (updated)
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(names);
                oos.writeObject(scores);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    /**
     * @return the list with all the names in the top5 file
     */
    public List getTop5Names() {

        String filePath = "top5.bin";
        List<String> names;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            names = (List<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            names = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            names = new ArrayList<>();
        }
        return names;
    }

    /**
     * @return the list with all the scores in the top5 file
     */
    public List getTop5Scores() {
        String filePath = "top5.bin";
        List<Integer> scores;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            ois.readObject();
            scores = (List<Integer>) ois.readObject();
        } catch (FileNotFoundException e) {
            scores = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            scores = new ArrayList<>();
        }
        return scores;

    }


    public int getLevel() {
        return context.getLevel();
    }

    public IState getState() {
        return context.getState();
    }

}
