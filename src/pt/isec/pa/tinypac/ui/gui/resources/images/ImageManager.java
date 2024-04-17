package pt.isec.pa.tinypac.ui.gui.resources.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_characters.*;
import pt.isec.pa.tinypac.model.data.maze_elements.maze_objects.*;

public class ImageManager {
    // Maze Element images
    private final Image pacmanImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/PacMan.png");
    private final Image blinkyImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/BLINKY.png");
    private final Image inkyImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/INKY.png");
    private final Image pinkyImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/PINKY.png");
    private final Image clydeImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/CLYDE.png");
    private final Image edibleGhostImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/EDIBLEGHOST.png");
    private final Image fruitImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/FRUIT.png");
    private final Image wallImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/Wall.png");
    private final Image emptyImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/EmptyCell.png");
    private final Image ballImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/Ball.png");
    private final Image specialImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/Special.png");
    private final Image ghostzoneImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/GhostZone.png");
    private final Image warpImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/WARP.png");
    private final Image portalImage = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/PORTAL.png");
    private final Image pacmanLogo = new Image("pt/isec/pa/tinypac/ui/gui/resources/images/PacmanLogo.png");


    /**
     * @param symbol The symbol of the IMazeElement
     * @return the appropriate image for that Maze Element
     */
    public ImageView getImage(char symbol) {
        return new ImageView(switch (symbol) {
            case Pacman.SYMBOL -> pacmanImage;
            case Blinky.SYMBOL -> blinkyImage;
            case Pinky.SYMBOL -> pinkyImage;
            case Inky.SYMBOL -> inkyImage;
            case Clyde.SYMBOL -> clydeImage;
            case Fruit.SYMBOL -> fruitImage;
            case Wall.SYMBOL -> wallImage;
            case Empty.SYMBOL, FruitSpot.SYMBOL -> emptyImage;
            case Ball.SYMBOL -> ballImage;
            case Special.SYMBOL -> specialImage;
            case GhostZone.SYMBOL -> ghostzoneImage;
            case Warp.SYMBOL -> warpImage;
            case Portal.SYMBOL -> portalImage;
            case 'E' -> edibleGhostImage;
            default -> null;
        });
    }

    /**
     * @param imgTitle title of the image we want
     * @return the image based on the title
     */
    public ImageView getImage(String imgTitle){
        return new ImageView(switch(imgTitle){
                case "logo" -> pacmanLogo;
            default -> null;
        });
    }
}
