package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.PriorityAntenna;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PriorityAntennaView {
    public static void drawPriorityAntenna(SpaceView spaceView, Space space){
        int[] posXY;

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (FieldAction c : space.getActions()){
            if (c instanceof PriorityAntenna){
                posXY =((PriorityAntenna) c).getPos();
            }
        }

        Image antennaImage = new Image("/pictures/antenna.png", 75, 75, false, false);
        gc.drawImage(antennaImage, 0, 0);
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);
    }

}
