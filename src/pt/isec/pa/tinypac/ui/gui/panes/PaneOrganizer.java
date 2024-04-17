package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.model.GameManager;

public class PaneOrganizer extends StackPane {
    private GameManager manager;
    private Button btStart;
    private Button btLoad;
    private Button btTop5;
    private Button btQuit;
    private Button btGoBack;
    private MazePane maze;
    private MenuPane mainMenu;
    private StatsPane statsPane;
    private GameEndPane gameEndPane;
    private PausePane pausePane;

    private Top5Pane top5;

    public PaneOrganizer(GameManager manager) {
        this.manager = manager;

        setBackground(Color.BLACK);

        createObjects();
        setOnView();
        setListeners();


    }
    private void setBackground(Color color) {

        setBackground(new Background(new BackgroundFill(color,null,null)));
    }

    /**
     * Creates Buttons that are used in other panes, and creates all the other panes that will be shown in the application
     */
    private void createObjects() {
        //Buttons

        btStart = new Button("Start");
        btLoad = new Button("Load");
        btQuit = new Button("Quit");
        btTop5 = new Button("Top 5");
        btGoBack = new Button("Go Back");
        changeButtonAppearance();
        //Panes

        mainMenu = new MenuPane(btStart, btTop5, btLoad, manager);
        top5 = new Top5Pane(manager, btGoBack);
        maze = new MazePane(manager);
        statsPane = new StatsPane(manager);
        gameEndPane = new GameEndPane(manager,btQuit);
        pausePane = new PausePane(manager);

    }

    private void changeButtonAppearance() {

        btStart.getStyleClass().add("buttonCSS");
        btLoad.getStyleClass().add("buttonCSS");
        btTop5.getStyleClass().add("buttonCSS");
        btQuit.getStyleClass().add("buttonCSS");


    }

    /**
     * adds all panes to the view and sets their visiblity to false
     * The mainMenu pane is the only one who starts as visible
     */
    private void setOnView() {
        getChildren().addAll(mainMenu,top5,maze,statsPane,gameEndPane,pausePane);
        showMainMenu();
        top5.setVisible(false);
        maze.setVisible(false);
        statsPane.setVisible(false);
        gameEndPane.setVisible(false);
        pausePane.setVisible(false);
    }

    /**
     * Sets the actions for multiple buttons
     * btStart will start the game and the gameEngine
     * btTop5 shows the current Top5 best scores recorded
     * btQuit will return the player to the main menu
     * btGoBack will also return the plaeyer to the main menu
     */
    private void setListeners() {
        btStart.setOnAction(event -> {
            //getChildren().add(gameEndPane);
            mainMenu.setVisible(false);
            maze.setVisible(true);
            maze.requestFocus();
            statsPane.setVisible(true);

        });

        btLoad.setOnAction(event -> {
            manager.loadGame();
            mainMenu.setVisible(false);
            maze.setVisible(true);
            maze.requestFocus();
            statsPane.setVisible(true);
        });

        btTop5.setOnAction(event -> {
            mainMenu.setVisible(false);
            top5.setVisible(true);
            top5.updateTop5();
        });


        btQuit.setOnAction(event -> {
            showMainMenu();
            maze.setVisible(false);
            statsPane.setVisible(false);
            gameEndPane.setVisible(false);

        });

        btGoBack.setOnAction(event -> {
            top5.setVisible(false);
            showMainMenu();
        });
    }

    private void showMainMenu() {
        mainMenu.setVisible(true);
        if(manager.existsSaveFile()) {
            btLoad.setVisible(true);
        }
        else {
            btLoad.setVisible(false);
        }
    }


}