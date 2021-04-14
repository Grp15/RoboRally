package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Energy_Space extends Space {

    private int Energy = 0;


    public Energy_Space(Board board, int x, int y) {
        super(board, x, y);
    }

    public boolean doAction(Player player, Space space, GameController gameController){

        player.addEnergy();
        return true;
    }

    public int getEnergy(Player player){
        return Energy;
    }


}
