package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fsm.State;

public class GameEndPane extends VBox {
    private GameManager manager;
    private Label lbScore;
    private Label lbGameOver;
    private Button btQuit;
    private VBox menuHolder;

    public GameEndPane(GameManager manager, Button btQuit) {
        this.manager = manager;

        createObjects(btQuit);
        setOnView();

        changeLabelAppearance();
        changeButtonAppearance();
        registerHandlers();
    }



    /**
     * Sets the Objects on the pane and aligns them
     */
    private void setOnView() {
        setAlignment(Pos.CENTER);
        lbGameOver.setAlignment(Pos.TOP_CENTER);
        menuHolder.setAlignment(Pos.CENTER);
        menuHolder.getChildren().addAll(lbScore, btQuit);
        getChildren().addAll(lbGameOver,menuHolder);

    }

    /**
     * @param btQuit the Quit used to quit the app
     *               Creates the buttons, labels and the menuholder
     */
    private void createObjects(Button btQuit) {
        this.btQuit = btQuit;
        lbGameOver = new Label("GAME OVER");
        lbScore = new Label("");
        menuHolder = new VBox();
    }

    /**
     * edits the appearence of labels
     */
    private void changeLabelAppearance()
    {
        lbScore.getStyleClass().add("scoreLabel");
        lbGameOver.getStyleClass().add("gameOverCSS");
    }

    /**
     * edits the appearence of buttons
     */
    private void changeButtonAppearance() {
        btQuit.getStyleClass().add("buttonCSS");
    }

    /**
     *  Registers the event handler for the game state property.
     *  When the game state property changes, it triggers the Update() method.
     */
    private void registerHandlers() {
        manager.addClient(GameManager.PROP_STATE, evt -> Update());
    }

    /**
     * updates the labels and visibility of the pane
     */
    private void Update() {
        if (State.FINISHED == manager.getState().getState()) {
            Platform.runLater(() -> {
                this.setVisible(true);
                lbScore.setText("Your Final Score is : " + manager.getScore());
            });
        }
        else{
            this.setVisible(false);
        }
    }
}