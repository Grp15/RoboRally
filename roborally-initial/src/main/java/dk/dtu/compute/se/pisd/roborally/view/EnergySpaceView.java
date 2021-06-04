package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.Energy;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.StartField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnergySpaceView {
    public static void drawEnergySpace(SpaceView spaceView, Space space){
        Energy tempSpace = (Energy) space;

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image energyImage = new Image("/EnergySpace.png", 75, 75, false, false);
        gc.drawImage(energyImage, 0, 0);
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);
    }

}


