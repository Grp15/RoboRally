package dk.dtu.compute.se.pisd.roborally.model.Spaces;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class FieldLaser extends Space {

    private Heading heading;

    public FieldLaser(Board board, int x, int y) {
        super(board, x, y);
    }

    /**
     * Fires a laser in direction laserfield is pointing. Laser cant go through players, walls or priority antenna
     * @param player
     * @param space
     * @param gameController
     * @return
     */
    public boolean doAction(Player player, Space space, GameController gameController) {


        return true;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }
}
