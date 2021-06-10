package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;


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

    @Override
    public boolean doAction(GameController gameController, Space space, Player player) {
        Board board = gameController.board;

        //int[] PlayerDistances = new int[board.getPlayersNumber()];

        return true;
    }
}
