package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class GearView {

    public static void drawGear(SpaceView spaceView, Space space){

        Direction d = null;

        for (FieldAction c : space.getActions()){
            if (c instanceof Gears){
                d =((Gears) c).getDirection();
            }
        }

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        if (d != null) {
            switch (d) {
                case Right:
                    Image gearImageRight = new Image("/pictures/GearImageRight.png", 75, 75, false, false);
                    gc.drawImage(gearImageRight, 0, 0);
                    break;

                case Left:
                    Image gearImageLeft = new Image("/pictures/GearImageLeft.png", 75, 75, false, false);
                    gc.drawImage(gearImageLeft, 0, 0);
                    break;
            }
            canvas.setOpacity(0.6);
            spaceView.getChildren().add(canvas);
        }
    }
}
