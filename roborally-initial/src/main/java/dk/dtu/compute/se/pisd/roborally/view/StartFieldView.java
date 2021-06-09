package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.StartField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class StartFieldView{
    public static void drawStartField(SpaceView spaceView, Space space){

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image startImage = new Image("/startfield.png", 75, 75, false, false);
        gc.drawImage(startImage, 0, 0);
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);

    }
}
