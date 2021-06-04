package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class CheckPoints extends Space {
    private SpaceType CHECKPOINT;
    private String name;

    public CheckPoints(Board board, int x, int y) {
        super(board, x, y);
    }
}
