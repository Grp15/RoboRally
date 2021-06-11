package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * PriorityAnetnnaView is a class that loads the antenna png from the picture resource folder and draws it on the spaces using canvas
 *
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 */

public class PriorityAntennaView {

    /**
     * Draw the priority antenna on a space using x and y coordinates and loading the asset from resource folder
     *
     * @param spaceView
     * @param space
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public static void drawPriorityAntenna(SpaceView spaceView, Space space){

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image antennaImage = new Image("/pictures/antenna.png", 75, 75, false, false);
        gc.drawImage(antennaImage, 0, 0);
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);
    }
}
