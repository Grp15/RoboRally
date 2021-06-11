package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

/**
 * Class implements the gear field which turns the robot 90 degrees either clock or counter clock wise depending on the gear type.
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */


public class Gears extends FieldAction {
    private Direction direction;

    /**
     * Get method for direction
     * @return direction
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    public Direction getDirection(){
        return direction;
    }

    /**
     * Set method for direction
     * @param direction
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    /**
     * Method to perform actions with the players on the spaces in the gamecontroller
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @param player
     * @return return true if action succeeded
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
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

