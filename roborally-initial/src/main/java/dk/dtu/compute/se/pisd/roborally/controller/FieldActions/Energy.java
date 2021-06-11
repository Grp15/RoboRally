package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Energy field action controller
 *
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 */
public class Energy extends FieldAction {

    private int energy = 1;

    /**
     * Set method for energy int
     * @param energy
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Get method for energy int
     * @return energy
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public int getEnergy() {
        return energy;
    }


    /**
     * Resets energy value to 0
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public void resetEnergy() {
        energy = 0;
    }


    /**
     * Method to perform actions with the players on the spaces in the gamecontroller
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @param player
     * @return return true if action succeeded
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    @Override
    public boolean doAction(GameController gameController, Space space, Player player) {
        Board board = gameController.board;

        int step = board.getStep();

        if(step == player.NO_REGISTERS){
            player.addEnergy();
        }
        if(energy > 0){
            player.addEnergy();
            resetEnergy();
        }

        return true;
    }
}