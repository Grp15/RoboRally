package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * Checkpoint controller class which extends FieldAction class.
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */
public class CheckPoint extends FieldAction {
    private int number; // Skal have et nr. op til det max antal der kan være hvilket er 5


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     *  Method to perform actions with the players on the spaces in the gamecontroller
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @param player
     * @return returns true if actions succeeded
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    @Override
    public boolean doAction(@NotNull GameController gameController,@NotNull Space space, @NotNull Player player) {
        Board board = gameController.board;

        if(player.getCheckPoints() == number -1){
            player.progressCheckpoint();
          //  System.out.println("Yo u progressed");
        }

        if(player.getCheckPoints() == board.getNumberOfCheckpoints()){
            System.out.println("You win!!");
        }

        return true; // True hvis det lykkedes, false hvis ikke.
    }
}
