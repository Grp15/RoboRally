package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PriorityAntenna extends Space {

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
    public boolean doAction(Player player, Space space, GameController gameController) {

        int[] playerdistance = new int[board.getPlayersNumber()];

        for (int i = 0; i < playerdistance.length; i++) {

            playerdistance[i] = gameController.DistanceSpacetoPlayer(board.getSpace(this.x, this.y), board.getPlayer(i));

            //Need to assign each player to a distance before sorting

            sort(playerdistance);
        }

        return true;
    }

    /**
     * Sorts a distancefromspacetoplayer array using sort algorithm
     *
     * @param playerDistances
     */

    public void sort(int[] playerDistances) {

    }
}
