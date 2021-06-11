package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;

/**
 * Model class to handle Priority antenna
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */

public class PriorityAntenna extends Space {
    private int[] PlayerDistances = new int[board.getPlayersNumber()];

    /**
     * The priority antenna is on the board with x and y coordinates
     *
     * @param board
     * @param x
     * @param y
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);

    }



}
