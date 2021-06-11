package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Class which implements the Startfield on the game board
 *
 * @author S164539 Hildibjørg
 * @author S154780 Troels
 */

public class StartField extends FieldAction {
    @Override
    public boolean doAction(GameController gameController, Space space, Player player) {
        return true;
    }
}
