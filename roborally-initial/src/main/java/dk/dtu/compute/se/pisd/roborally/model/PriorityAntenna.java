package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;

public class PriorityAntenna extends Space {
    private int[] PlayerDistances = new int[board.getPlayersNumber()];


    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);

    }



}
