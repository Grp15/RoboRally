package dk.dtu.compute.se.pisd.roborally.model;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

/**
 * Denne klasse implementerer gear feltet, som drejer robotten 90 grader med eller imod uret alt efter geartypen.
 */


public class Gears extends  Space {
    private SpaceType type = SpaceType.GEARS;
    private String direction;

    public Gears(Board board, int x, int y, String direction) {
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
    public boolean doAction(Player player, Space space, GameController gameController) {


        if(this.direction == "RIGHT"){
            gameController.turnRight(player);
        }
        else if(this.direction == "LEFT"){
            gameController.turnLeft(player);
        }

        System.out.print("Du har ramt et gear");

        return true;
    }

    public SpaceType getSpaceType(){
        return type;
    }


}

