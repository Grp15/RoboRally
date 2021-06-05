package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.CheckPoint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CheckpointView {
    public static void drawCheckpoint(SpaceView spaceView, Space space) {
        CheckPoint tempSpace = (CheckPoint) space;

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int d = tempSpace.getNumber();

        switch (d) {
            case 1:
                Image checkpoint1 = new Image("/checkpoint1.png", 75, 75, false, false);
                gc.drawImage(checkpoint1, 0, 0);
                break;

            case 2:
                Image checkpoint2 = new Image("/checkpoint2.png", 75, 75, false, false);
                gc.drawImage(checkpoint2, 0, 0);
                break;

            case 3:
                Image checkpoint3 = new Image("/checkpoint3.png", 75, 75, false, false);
                gc.drawImage(checkpoint3, 0, 0);
                break;

            case 4:
                Image checkpoint4 = new Image("/checkpoint4.png", 75, 75, false, false);
                gc.drawImage(checkpoint4, 0, 0);
                break;

            case 5:
                Image checkpoint5 = new Image("/checkpoint5.png", 75, 75, false, false);
                gc.drawImage(checkpoint5, 0, 0);
                break;

        }
        canvas.setOpacity(0.6);
        spaceView.getChildren().add(canvas);

    }
}
