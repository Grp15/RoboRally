package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * StartFieldView is a class that loads the startfield png from the picture resource folder and draws it on the spaces using canvas
 *
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 */
public class StartFieldView{

    /**
     * Draw startfields on spaces using x and y coordinates and loading the assets from resource folder
     *
     * @param spaceView
     * @param space
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public static void drawStartField(SpaceView spaceView, Space space){

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image startImage = new Image("/pictures/startfield.png", 75, 75, false, false);
        gc.drawImage(startImage, 0, 0);
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);

    }
}
