package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Energy field action controller
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */
public class Energy extends FieldAction {

    private int energy = 1;


    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }


    /**
     * Resets energy value to 0
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
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
     * @author S164539 Hildibjørg
     * @author S154780 Troels
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