package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fsm.State;

public class StatsPane extends VBox {
    private GameManager manager;
    private final Label lbTitle;

    private VBox vbLbHolder;
    private  Label lbLives;
    private Label lbLevel;
    private  Label lbScore;

    public StatsPane(GameManager manager) {
        this.manager = manager;
        lbTitle = new Label("Current Stats:");

        createObjects();
        changeLabelAppearance();
        registerHandlers();

        setOnView();
        updateStats();
    }

    /**
     * Registers the event handler for the game state property and ui update property
     * When the game state property changes, it triggers the toggleVisible() method.
     * When ui uipdate property changes it triggers the updateStats() method
     */
    private void registerHandlers() {
        manager.addClient(GameManager.PROP_UIUPDATE, evt -> updateStats());
        manager.addClient(GameManager.PROP_STATE, evt -> toggleVisibility());
    }

    /**
     * toggles the visibility of the pane based on the state
     */
    private void toggleVisibility() {
        setVisible(State.NORMAL == manager.getState().getState() || State.INVINCIBLE == manager.getState().getState()|| State.WAITING == manager.getState().getState());
    }

    /**
     * updates the stats shown on screen
     */
    private void updateStats() {
        Platform.runLater(() -> {
            lbScore.setText("Score: " + manager.getScore());
            lbLevel.setText("Level: " + manager.getLevel());
            lbLives.setText("Lives: " + manager.getLives());
        });
    }

    private void createObjects() {
        this.vbLbHolder = new VBox();
        lbLives = new Label("Lives: ");
        lbLevel = new Label("Level: ");
        lbScore = new Label("Score:  ");
    }

    /**
     * Sets the alignment of the of the pane, and the vbLbHolder also its spacing and adds everything to the view
     */
    private void setOnView() {

        setAlignment(Pos.TOP_RIGHT);
        vbLbHolder.setAlignment(Pos.TOP_RIGHT);
        vbLbHolder.setSpacing(20);
        vbLbHolder.getChildren().addAll(lbLives,lbLevel,lbScore);
        lbTitle.setAlignment(Pos.TOP_CENTER);

        getChildren().addAll(lbTitle,vbLbHolder);
    }

    /**
     * changes the appearance of the label
     */
    private void changeLabelAppearance() {
        lbTitle.getStyleClass().add("titleCSS");
        lbLives.getStyleClass().add("labelCSS");
        lbLevel.getStyleClass().add("labelCSS");
        lbScore.getStyleClass().add("labelCSS");

    }
}

