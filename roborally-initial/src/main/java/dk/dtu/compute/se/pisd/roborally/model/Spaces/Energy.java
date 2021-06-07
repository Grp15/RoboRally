package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

//TODO: Test om den virker
public class Energy extends Space {

    private int Energy = 1;

    public Energy(Board board, int x, int y) {
        super(board, x, y);
    }

    public boolean doAction(Player player, Space space, GameController gameController){
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

    public int getEnergy() {
        return Energy;
    }

    public void resetEnergy() {
        Energy = 0;
    }
}
