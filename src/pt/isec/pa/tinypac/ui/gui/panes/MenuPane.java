package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.ui.gui.resources.images.ImageManager;

public class MenuPane extends VBox {
    private GameManager manager;
    private Label lbTitle;
    private VBox vbButtonHolder;
    private Button btStart;
    private Button btLoad;
    private Button btTop5;
    private Button btExit;
    private ImageView pacmanLogo;
    private ImageManager imgManager;

    public MenuPane(Button btStart, Button btTop5, Button btLoad, GameManager manager) {
        this.manager = manager;
        this.imgManager = new ImageManager();
        createObjects(btStart, btTop5, btLoad);
        setOnView();
        setListeners();
    }

    /**
     * @param btStart Button Start created in the PaneOrganizer
     * @param btTop5 Button Top5 created in the PaneOrganizer
     */
    private void createObjects(Button btStart, Button btTop5, Button btLoad) {
        this.lbTitle = new Label("WELCOME TO TINYPAC");
        pacmanLogo = imgManager.getImage("logo");
        changeLabelAppearance();
        this.vbButtonHolder = new VBox();
        this.btStart = btStart;
        this.btExit = new Button("Exit");
        this.btTop5 = btTop5;
        this.btLoad = btLoad;

        changeButtonAppearance();

    }

    private void changeLabelAppearance() {
        lbTitle.getStyleClass().add("titleCSS");
    }

    private void changeButtonAppearance() {
        btExit.getStyleClass().add("buttonCSS");
    }



    /**
     * Sets the alignment and spacing of the vbButtonHolder and adds the buttons to it
     * Aligns the pane and adds the title, an image and vbButtonHolder to the view
     */
    private void setOnView() {
        vbButtonHolder.setAlignment(Pos.CENTER);
        vbButtonHolder.setSpacing(30);
        vbButtonHolder.getChildren().addAll(btStart, btLoad, btTop5, btExit);

        this.setAlignment(Pos.CENTER);
        //this.setSpacing(25);
        //VBox.setMargin(pacmanLogo, new Insets(5, 0, 0, 0));
        this.getChildren().addAll(lbTitle,pacmanLogo , vbButtonHolder);
    }

    /**
     * sets the action for the button Exit that closes the plataform
     */
    private void setListeners() {
        btExit.setOnAction(actionEvent -> {
            //encerrar a aplicação
            Platform.exit();
        });

        btLoad.setOnAction(actionEvent -> {
           manager.loadGame();
        });
    }

}
