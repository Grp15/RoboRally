package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ConveyorBeltView {


    public static void drawConveyorBelt(SpaceView spaceView, Space space){

        Heading h = null;
        for (FieldAction c : space.getActions()){
            if (c instanceof ConveyorBelt){
                h =((ConveyorBelt) c).getHeading();
            }
        }


        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (h != null) {
            switch (h) {
                case NORTH:
                    Image ConveyorImageNorth = new Image("/pictures/ConveyourBeltNorth.png", 75, 75, false, false);
                    gc.drawImage(ConveyorImageNorth, 0, 0);
                    break;

                case EAST:
                    Image ConveyorImageEast = new Image("/pictures/ConveyourBeltEast.png", 75, 75, false, false);
                    gc.drawImage(ConveyorImageEast, 0, 0);
                    break;

                case SOUTH:
                    Image ConveyorImageSouth = new Image("/pictures/ConveyourBeltSouth.png", 75, 75, false, false);
                    gc.drawImage(ConveyorImageSouth, 0, 0);
                    break;

                case WEST:
                    Image ConveyorImageWest = new Image("/pictures/ConveyourBeltWest.png", 75, 75, false, false);
                    gc.drawImage(ConveyorImageWest, 0, 0);
                    break;
            }
        }
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);

    }
}
