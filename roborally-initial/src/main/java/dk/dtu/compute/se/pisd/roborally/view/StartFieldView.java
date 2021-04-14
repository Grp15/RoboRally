package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.StartField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StartFieldView{
    public static void drawConveyorBelt(SpaceView spaceView, Space space){
        ConveyorBelt tempSpace = (ConveyorBelt) space;

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillOval(25,32,30, 20);

        spaceView.getChildren().add(canvas);

    }
}
