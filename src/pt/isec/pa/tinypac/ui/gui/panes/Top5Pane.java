package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.GameManager;

import java.util.List;

public class Top5Pane extends VBox {
    GameManager manager;
    private Button btGoBack;
    private final Label lbTitle;
    private  Label lb1stPlace;
    private Label lb2ndPlace;
    private  Label lb3rdPlace;
    private  Label lb4ndPlace;
    private  Label lb5thPlace;
    private VBox vbLbHolder;

    public Top5Pane(GameManager manager, Button btGoBack) {
        lbTitle = new Label("Top 5 Highscores ");
        createObjects(manager,btGoBack);
        changeLabelAppearance();
        changeButtonAppearance();
        setOnView();

    }


    /**
     * @param manager Object of the GameManager class
     * @param btGoBack button that changes back to the main menu
     */
    private void createObjects(GameManager manager, Button btGoBack) {
        this.manager = manager;
        vbLbHolder = new VBox();
        this.btGoBack = btGoBack;

        lb1stPlace = new Label();
        lb2ndPlace = new Label();
        lb3rdPlace = new Label();
        lb4ndPlace = new Label();
        lb5thPlace = new Label();

    }

    /**
     * changes the apperance of the labels
     */
    private void changeLabelAppearance() {
        lbTitle.getStyleClass().add("titleCSS");
        lb1stPlace.getStyleClass().add("scoreLabel");
        lb2ndPlace.getStyleClass().add("scoreLabel");
        lb3rdPlace.getStyleClass().add("scoreLabel");
        lb4ndPlace.getStyleClass().add("scoreLabel");
        lb5thPlace.getStyleClass().add("scoreLabel");

    }

    /**
     * changes button appearance
     */
    private void changeButtonAppearance() {
        btGoBack.getStyleClass().add("buttonCSS");
    }

    /**
     * sets the alignment spacing and adds everything to the view
     */
    private void setOnView() {
        vbLbHolder.setAlignment(Pos.BOTTOM_CENTER);
        vbLbHolder.setSpacing(20);
        vbLbHolder.getChildren().addAll(lb1stPlace,lb2ndPlace,lb3rdPlace,lb4ndPlace,lb5thPlace);
        setAlignment(Pos.TOP_CENTER);
        setSpacing(50);
        getChildren().addAll(lbTitle,vbLbHolder,btGoBack);
    }

    /**
     * updates the top5 scores
     */
    public void updateTop5(){
        List scores = manager.getTop5Scores();
        List names = manager.getTop5Names();
        lb1stPlace.setText(names.get(0) + "       -         " + scores.get(0));
        lb2ndPlace.setText(names.get(1) + "       -         " + scores.get(1));
        lb3rdPlace.setText(names.get(2) + "       -         " + scores.get(2));
        lb4ndPlace.setText(names.get(3) + "       -         " + scores.get(3));
        lb5thPlace.setText(names.get(4) + "       -         " + scores.get(4));
    }
}