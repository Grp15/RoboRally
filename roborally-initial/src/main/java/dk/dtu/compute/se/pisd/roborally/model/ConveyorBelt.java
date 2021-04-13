package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class ConveyorBelt extends Space {

    private Heading heading;
    private SpaceType type = SpaceType.CONVEYORBELT;

    public ConveyorBelt(Board board, int x, int y, Heading heading) {
        super(board, x, y);
        this.heading = heading;

    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public SpaceType getSpaceType(){
        return type;
    }

    public boolean doAction(@NotNull Player player, @NotNull Space space, GameController gameController) {
        // TODO Skub en spiller i den retning ConveyorBelt peger. (Husk at spillet eksekvere 1 register pr. spiller ad gangen)

        //player.setSpace(space.board.getNeighbour(space,heading));

        gameController.moveForward(player);

        return true; // True hvis det lykkedes, false hvis ikke.

    }


}
