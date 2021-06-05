package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;

public class PriorityAntenna extends Space {
    private int[] PlayerDistances = new int[board.getPlayersNumber()];


    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);

    }


    /**
     * Create in which order players turn is
     *
     * @param player
     * @param space
     * @param gameController
     * @return
     */

    // TODO: Need to attach reference from distance array to a player
    // TODO: Needs to set which players turn it is
    // TODO: Needs to be called at start of each turn, for now it only does anything if a player stands on the field.
    // TODO: For now this is just an ordinary space, should be executed in a different way than other spaces and not hardcoded
    // TODO: Skal bruge en test
    public boolean doAction(Player player, Space space, GameController gameController) {

        int[] playerdistance = new int[board.getPlayersNumber()];

        for (int i = 0; i < playerdistance.length; i++) {

            playerdistance[i] = gameController.DistanceSpacetoPlayer(board.getSpace(this.x, this.y), board.getPlayer(i));

            //Sets the players distance to antenna
            gameController.getBoard().getPlayer(i).setDistancetoAntenna(playerdistance[i]);

            //sort(playerdistance);

        }

        for (int i = 0; i < board.getPlayersNumber(); i++) {

            System.out.print(playerdistance[i] + "  ");
        }

        return true;
    }

    /**
     * Sorts a distancefromspacetoplayer array using bubblesort algorithm
     *
     * @param playerDistances
     */

    //TODO: Sort algorithm does not work
    public void sort(int[] playerDistances) {
        int temprorayInt = 0;

        for(int i = 0; i < playerDistances.length; i++){
            for(int j = 1; j < playerDistances.length -1; j++){
                if(playerDistances[j-1] > playerDistances[j]){
                    //byt om på elementer
                    temprorayInt = playerDistances[j-1];
                    playerDistances[j-1] = playerDistances[j];
                    playerDistances[j] = temprorayInt;
                }
            }
        }
    }
}
