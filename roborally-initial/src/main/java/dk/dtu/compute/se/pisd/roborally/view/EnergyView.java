package dk.dtu.compute.se.pisd.roborally.view;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Energy;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * EnerguView is a class that loads the energy spaces png's from the picture resource folder and draws it on the spaces using canvas
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */
public class EnergyView {

    /**
     * Draw energy spaces on spaces using x and y coordinates and loading the assets from resource folder
     *
     * @param spaceView
     * @param space
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    public static void drawEnergy(SpaceView spaceView, Space space){
        int energy = 0;

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        for (FieldAction c : space.getActions()){
            if (c instanceof Energy){
                energy =((Energy) c).getEnergy();
            }
        }

        if(energy > 0){
            Image energyImage = new Image("/pictures/EnergySpace_w_cube.png", 75, 75, false, false);
            gc.drawImage(energyImage, 0, 0);
            canvas.setOpacity(0.6);
            spaceView.getChildren().add(canvas);
        }
        else {
            Image energyImage = new Image("/pictures/EnergySpace.png", 75, 75, false, false);
            gc.drawImage(energyImage, 0, 0);
            canvas.setOpacity(0.6);
            spaceView.getChildren().add(canvas);
        }
    }

}


