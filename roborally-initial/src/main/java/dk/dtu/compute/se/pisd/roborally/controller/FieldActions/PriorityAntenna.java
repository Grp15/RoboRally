package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Class implements the Priority antennae on the board
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */
public class PriorityAntenna extends FieldAction {

int posX,posY;


    public int[] getPos() {
        int[] pos = {posX,posY};
        return pos;
    }

    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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

        //int[] PlayerDistances = new int[board.getPlayersNumber()];

        return true;
    }
}
