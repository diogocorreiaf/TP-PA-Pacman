package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.data.utils.Direction;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.ui.gui.resources.images.ImageManager;

public class MazePane extends GridPane {
    private GameManager manager;

    private GridPane grid;
    private final ImageManager imageManager;
    private ImageView img;

    public MazePane(GameManager manager) {
        this.manager = manager;
        this.grid = new GridPane();
        this.imageManager = new ImageManager();
        setOnView();

        updateView();
        grid.setVisible(true);


        registerHandlers();
        setFocusTraversable(true);
        requestFocus();

    }

    /**
     * Will change the pacmans directions based on the arrow key that was pressed
     * Registers the event handler for the game state property and ui update property
     * When the game state property changes, it triggers the toggleVisible() method.
     * When ui uipdate property changes it triggers the updateView() method
     */
    public void registerHandlers() {
        this.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP -> {
                    manager.start();
                    manager.changePacmanDirection(Direction.UP);
                }
                case DOWN -> {
                    manager.start();
                    manager.changePacmanDirection(Direction.DOWN);
                }
                case LEFT -> {
                    manager.start();
                    manager.changePacmanDirection(Direction.LEFT);
                }
                case RIGHT -> {
                    manager.start();
                    manager.changePacmanDirection(Direction.RIGHT);
                }
                case ESCAPE -> {
                    manager.start();
                    manager.pause();
                }
                default -> {
                }
            }
        });

        manager.addClient(GameManager.PROP_UIUPDATE, evt -> updateView());
        manager.addClient(GameManager.PROP_STATE, evt -> toggleVisible());
    }

    /**
     * toggles the visibility of the pane in question based on the current state
     */
    private void toggleVisible() {
        Platform.runLater(() -> {
            this.setVisible(State.NORMAL == manager.getState().getState() || State.WAITING == manager.getState().getState() || State.INVINCIBLE == manager.getState().getState());
        });
    }


    /**
     * Sets the aligment of the pane and adds the grid to the view
     */
    private void setOnView() {
        setAlignment(Pos.CENTER);
        getChildren().addAll(grid);
        grid.setVisible(true);
    }


    /**
     * Clears the grid
     * adds images to the grid based on the char in the maze
     * if the current State is invincible it uses different images for Ghosts
     */
    public void updateView() {
        char[][] maze = manager.getMaze();
        State current = manager.getState().getState();
        if (current == State.NORMAL || current == State.WAITING) {
            Platform.runLater(() -> {
                grid.getChildren().clear();
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        img = imageManager.getImage(maze[i][j]);
                        grid.add(img, j, i);
                    }
                }
            });
        }
        if (current == State.INVINCIBLE) {
            Platform.runLater(() -> {
                grid.getChildren().clear();
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        char currentChar = maze[i][j];
                        if (currentChar == 'p' || currentChar == 'c' || currentChar == 'b' || currentChar == 'i') {
                            img = imageManager.getImage('E');

                        } else {
                            img = imageManager.getImage(maze[i][j]);
                        }
                        grid.add(img, j, i);
                    }
                }
            });
        }
    }
}