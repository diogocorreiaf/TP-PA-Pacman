package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.fsm.State;

public class PausePane extends VBox {
    private GameManager manager;
    private Label lbPause;
    private Button btResume;
    private Button btSave;
    private Button btExit;
    private VBox vbBtHolder;

    public PausePane(GameManager manager) {
        this.manager = manager;
        createObjects();
        setOnView();

        setListeners();
        changeLabelAppearance();
        changeButtonAppearance();
        registerHandlers();
    }

    private void createObjects() {
        this.lbPause = new Label("Game paused");
        this.btResume = new Button("Resume");
        this.btSave = new Button("Save");
        this.btExit = new Button("Exit");
        this.vbBtHolder = new VBox();
    }

    /**
     * Sets the alignment of the of the pane, and the vbBtHolder also its spacing and adds everything to the view
     */
    private void setOnView() {
        setAlignment(Pos.CENTER);
        vbBtHolder.setAlignment(Pos.CENTER);
        vbBtHolder.setSpacing(30);
        vbBtHolder.getChildren().addAll(btResume, btSave,btExit);
        getChildren().addAll(lbPause, vbBtHolder);
    }

    /**
     * Sets the actions of the buttons
     * btSave saves the game
     * btResume will resume the game to its previous state
     */
    private void setListeners() {
        btSave.setOnAction(event -> {
            manager.save();
        });

        btResume.setOnAction(event -> {
            manager.start();
        });
        btExit.setOnAction(event -> {
            manager.quit();
        });
    }

    /**
     *Registers the event handler for the game state property.
     *When the game state property changes, it triggers the toggleVisible() method.
     */
    private void registerHandlers() {
        manager.addClient(GameManager.PROP_STATE, evt -> toggleVisible());
    }

    /**
     * toggles the visibility based on the state
     */
    private void toggleVisible() {
        this.setVisible(State.PAUSED == manager.getState().getState());
    }

    /**
     * changes the appearance of the label
     */
    private void changeLabelAppearance() {
        lbPause.getStyleClass().add("titleCSS");
    }

    /**
     * changes the appearance of Buttons
     */
    private void changeButtonAppearance() {
        btResume.getStyleClass().add("buttonCSS");
        btSave.getStyleClass().add("buttonCSS");
        btExit.getStyleClass().add("buttonCSS");
    }
}
