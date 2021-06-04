package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class CheckPoints extends Space {
    private SpaceType CHECKPOINT;
    private int Number; // Skal have et nr. op til det max antal der kan være

    public CheckPoints(Board board, int x, int y, int Number) {
        super(board, x, y);
        this.Number = Number;
    }


}
