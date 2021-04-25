package dk.dtu.compute.se.pisd.roborally.model.Spaces;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Direction;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.SpaceType;

/**
 * Denne klasse implementerer gear feltet, som drejer robotten 90 grader med eller imod uret alt efter geartypen.
 */


public class Gears extends Space {
    private SpaceType type = SpaceType.GEARS;
    private final Direction direction;

    public Gears(Board board, int x, int y, Direction direction) {
        super(board, x, y);
        this.direction = direction;
    }


    /**
     * Metode der drejer spiller med eller mod uret når spilleren lander på det
     * @param player
     * @param space
     * @param gameController
     * @return
     */

    //TODO: Gears vender den forkerte spiller om
    public boolean doAction(Player player, Space space, GameController gameController) {


        if(this.direction == Direction.Right){
            gameController.turnRight(player);
        }
        else if(this.direction == Direction.Left){
            gameController.turnLeft(player);
        }

        return true;
    }

    public SpaceType getSpaceType(){
        return type;
    }

    public Direction getGearDirection(){
        return direction;
    }


}

