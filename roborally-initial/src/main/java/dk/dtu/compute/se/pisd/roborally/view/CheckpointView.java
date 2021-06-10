package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CheckpointView {

    public static void drawCheckpoint(SpaceView spaceView, Space space) {
        int d = 0;

        for (FieldAction c : space.getActions()){
            if (c instanceof CheckPoint){
                d =((CheckPoint) c).getNumber();
            }
        }

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

    if (d != 0) {
        switch (d) {
            case 1:
                Image checkpoint1 = new Image("/pictures/checkpoint1.png", 75, 75, false, false);
                gc.drawImage(checkpoint1, 0, 0);
                break;

            case 2:
                Image checkpoint2 = new Image("/pictures/checkpoint2.png", 75, 75, false, false);
                gc.drawImage(checkpoint2, 0, 0);
                break;

            case 3:
                Image checkpoint3 = new Image("/pictures/checkpoint3.png", 75, 75, false, false);
                gc.drawImage(checkpoint3, 0, 0);
                break;

            case 4:
                Image checkpoint4 = new Image("/pictures/checkpoint4.png", 75, 75, false, false);
                gc.drawImage(checkpoint4, 0, 0);
                break;

            case 5:
                Image checkpoint5 = new Image("/pictures/checkpoint5.png", 75, 75, false, false);
                gc.drawImage(checkpoint5, 0, 0);
                break;

                }
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);
        }
    }
}
