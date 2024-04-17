package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.GameManager;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidFileFormatException;
import pt.isec.pa.tinypac.model.data.exceptions.InvalidMazeElementException;
import pt.isec.pa.tinypac.ui.gui.panes.PaneOrganizer;

import java.io.FileNotFoundException;

public class MainJFX extends Application {

    @Override
    public void start(Stage primaryStage) throws InvalidFileFormatException, FileNotFoundException, InvalidMazeElementException {

        GameManager manager;
        manager = new GameManager();
        Scene scene = new Scene(new PaneOrganizer(manager));

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setX(0);
        primaryStage.setY(0);
        scene.getStylesheets().add("pt/isec/pa/tinypac/ui/gui/resources/CSS/Styles.css");
        primaryStage.setTitle("Tinypac");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}