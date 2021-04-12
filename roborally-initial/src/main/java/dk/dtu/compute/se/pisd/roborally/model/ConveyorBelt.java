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
        // TODO Step by step
        /**
         *  1) Find ud af hvordan spilleren skal rykkes (Husk at tænke på exceptions
         *  2) Vi skulle fikse doAction i gameController og opsætte
         *  det ordenligt i view.
         *  3) Vi skal implementere hvor hen spilleren rykkes til
         *  4) Vi skal fange exceptions og så returnerer metoden False
         *  5) ellers skal den returnere True
         **/

        //player.setSpace(space.board.getNeighbour(space,heading));

        gameController.movePlayerToSpace(space.board.getNeighbour(space,heading), player);

        //Ser ud til at virke, kaster bare ikke nogle exceptions såsom impossible move

        //gameController.moveCurrentPlayerToSpace(space.board.getNeighbour(space,heading)); // TODO : Der skal findes det rigtige space at smide spilleren hen, kaster også en exception


        return true; // True hvis det lykkedes, false hvis ikke.

    }


}
