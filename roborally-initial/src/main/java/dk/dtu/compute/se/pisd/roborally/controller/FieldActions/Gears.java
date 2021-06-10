package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.SpaceType;
import org.jetbrains.annotations.NotNull;

/**
 * Denne klasse implementerer gear feltet, som drejer robotten 90 grader med eller imod uret alt efter geartypen.
 */


public class Gears extends FieldAction {
    private Direction direction;


    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean doAction(GameController gameController, @NotNull Space space, @NotNull Player player) {

        if(this.direction == Direction.Right){
            gameController.turnRight(player);
        }
        else if(this.direction == Direction.Left){
            gameController.turnLeft(player);
        }

        return true;
    }
}

