/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * Conveyor belt class which extends FieldAction controller class
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 */
public class ConveyorBelt extends FieldAction {

    private Heading heading;

    /**
     * Get method for heading
     * @return heading
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * Set method for heading
     * @param heading
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    /**
     * Method to perform actions with the players on the spaces in the gamecontroller
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @param player
     * @return return true if action succeeded
     *
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public boolean doAction(GameController gameController, @NotNull Space space, @NotNull Player player) {

        Board board = gameController.board;

        Space target = board.getNeighbour(space, heading);

        try {
            gameController.moveToSpace(player,target, heading);
        } catch (GameController.ImpossibleMoveException e) {
            e.printStackTrace();
        }


        return true; // True hvis det lykkedes, false hvis ikke.
    }






}