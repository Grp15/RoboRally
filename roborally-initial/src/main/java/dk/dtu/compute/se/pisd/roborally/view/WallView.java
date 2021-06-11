package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Gears;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.net.NoRouteToHostException;

public class WallView {

    public static void drawWall(SpaceView spaceView, Space space){
        Heading h = null;

        for (Heading c : space.getWalls()){
            if (c instanceof Heading){
                h =((Heading) c).getHeading();
            }
        }

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);

        for(Heading h : tempSpace.getHeading){
            switch (h):
            case SOUTH:
                gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2,SpaceView.SPACE_WIDTH-2,SpaceView.SPACE_HEIGHT-2);
                break;

            case NORTH:
                gc.strokeLine(2, SpaceView.SPACE_HEIGHT-72,SpaceView.SPACE_WIDTH-2,SpaceView.SPACE_HEIGHT-72);
                break;

            case EAST:
                gc.strokeLine(72, SpaceView.SPACE_HEIGHT-2,SpaceView.SPACE_WIDTH-2,SpaceView.SPACE_HEIGHT-72);
                break;

            case WEST:
                gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2,SpaceView.SPACE_WIDTH-72,SpaceView.SPACE_HEIGHT-72);
                break;

            default
                break;

        }
    }

}
