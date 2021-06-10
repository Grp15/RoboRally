package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class CheckPoint extends FieldAction {
    private int number; // Skal have et nr. op til det max antal der kan være hvilket er 5


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController,@NotNull Space space, @NotNull Player player) {
        Board board = gameController.board;

        if(player.getCheckPoints() == number -1){
            player.progressCheckpoint();
            System.out.println("Yo u progressed");
        }

        if(player.getCheckPoints() == board.getNumbOfCheckPoints()){
            System.out.println("You win!!");
        }

        return true; // True hvis det lykkedes, false hvis ikke.
    }
}
