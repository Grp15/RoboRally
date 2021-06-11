package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Energy extends FieldAction {

    private int energy = 1;

    public void setEnergy(int energy) {
        this.energy = energy;
    }
    public int getEnergy() {
        return energy;
    }

    public void resetEnergy() {
        energy = 0;
    }

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