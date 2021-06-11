package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 * WallView is a class that draws wall lines on the spaces using canvas
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */
public class WallView {


    /**
     * Draw walls on spaces using x and y coordinates and size of spaces
     *
     * @param spaceView
     * @param space
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    public static void drawWall(SpaceView spaceView, Space space) {

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        for (Heading h : space.getWalls()) {
            switch (h) {
                case SOUTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                    break;

                case NORTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 72, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 72);
                    break;

                case EAST:
                    gc.strokeLine(72, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 72);
                    break;

                case WEST:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 72, SpaceView.SPACE_HEIGHT - 72);
                    break;

            }
            spaceView.getChildren().add(canvas);
        }

    }
}
