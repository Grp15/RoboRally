package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.model.Gears;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class GearView {
    public static void drawGear(SpaceView spaceView, Space space){
    Gears tempSpace = (Gears) space;

    Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.GREY);
    gc.fillOval(25,32,30, 20);
    //gc.setLineWidth(5);
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.setFill(Color.BLACK);
    gc.fillText("MED URET",10,40);
    //gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2,
    //        SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-2);


    // TODO lave switch case for forskellige gear retninger

    spaceView.getChildren().add(canvas);

    }
}
