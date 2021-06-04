package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class CheckPoints extends Space {
    private SpaceType CHECKPOINT;
    private int Number; // Skal have et nr. op til det max antal der kan være hvilket er 5

    public CheckPoints(Board board, int x, int y, int Number) {
        super(board, x, y);
        this.Number = Number;
    }

    /**
     * Denne metode håndterer at hvis spilleren lander på et checkpoint og har været på det forrige, kan denne komme til næste checkpoint
     * @param player
     * @param space
     * @param gameController
     * @return
     */

    //TODO: Test this method

    public boolean doAction(@NotNull Player player, @NotNull Space space, GameController gameController) {
        // TODO Skal indeholde logik for at en spiller har checkpoints i den rigtige rækkefølge

        if(player.getCheckPoints() == Number-1){
            player.setCheckPoints(Number);
        }

        return true; // True hvis det lykkedes, false hvis ikke.

    }

}
