package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Energy extends FieldAction {

    private int Energy = 1;

    public int getEnergy() {
        return Energy;
    }

    public void resetEnergy() {
        Energy = 0;
    }

    @Override
    public boolean doAction(GameController gameController, Space space, Player player) {
        Board board = gameController.board;

        int step = board.getStep();

        if(step == player.NO_REGISTERS){
            player.addEnergy();
        }
        if(Energy > 0){
            player.addEnergy();
            resetEnergy();
        }

        return true;
    }
}