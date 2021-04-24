package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.Gears;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ConveyorBeltView {


    public static void drawConveyorBelt(SpaceView spaceView, Space space){
        ConveyorBelt tempSpace = (ConveyorBelt) space;

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Heading h = tempSpace.getHeading();

        switch (h) {
            case NORTH:
                Image ConveyorImageNorth = new Image("/ConveyourBeltNorth.png", 75, 75, false, false);
                gc.drawImage(ConveyorImageNorth, 0, 0);
                break;

            case EAST:
                Image ConveyorImageEast = new Image("/ConveyourBeltEast.png", 75, 75, false, false);
                gc.drawImage(ConveyorImageEast, 0, 0);
                break;

            case SOUTH:
                Image ConveyorImageSouth = new Image("/ConveyourBeltSouth.png", 75, 75, false, false);
                gc.drawImage(ConveyorImageSouth, 0, 0);
                break;

            case WEST:
                Image ConveyorImageWest = new Image("/ConveyourBeltWest.png", 75, 75, false, false);
                gc.drawImage(ConveyorImageWest, 0, 0);
                break;
        }
        spaceView.getChildren().add(canvas);

    }
}
