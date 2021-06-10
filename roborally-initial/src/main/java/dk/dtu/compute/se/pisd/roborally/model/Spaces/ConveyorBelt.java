package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

public class ConveyorBelt extends Space {

    private Heading heading;


    public ConveyorBelt(Board board, int x, int y, Heading heading) {
        super(board, x, y);
        super.setType(SpaceType.CONVEYORBELT);
        this.heading = heading;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }




    public boolean doAction(@NotNull Player player, @NotNull Space space, GameController gameController) {


        Space target = board.getNeighbour(space, heading);

        try {
            gameController.moveToSpace(player,target, heading);
        } catch (GameController.ImpossibleMoveException e) {
            e.printStackTrace();
        }


        return true; // True hvis det lykkedes, false hvis ikke.

    }



}
