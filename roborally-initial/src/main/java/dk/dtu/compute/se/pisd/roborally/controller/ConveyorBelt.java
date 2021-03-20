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
package dk.dtu.compute.se.pisd.roborally.controller;

import com.sun.xml.internal.bind.v2.TODO;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class ConveyorBelt extends FieldAction {

    private Heading heading;

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // TODO Skub en spiller i den retning ConveyorBelt peger. (Husk at spillet eksekvere 1 register pr. spiller ad gangen)
        // TODO Step by step
        /**
         *  1) Find ud af hvordan spilleren skal rykkes (Husk at tænke på exceptions
         *  2) Vi skulle fikse doAction i gameController og opsætte
         *  det ordenligt i view.
         *  3) Vi skal implementere hvor hen spilleren rykkes til
         *  4) Vi skal fange exceptions og så returnerer metoden False
         *  5) ellers skal den returnere True
         */

        Player current = space.getPlayer(); // Tror ikke det her skal bruges

        gameController.moveCurrentPlayerToSpace(space.board.getNeighbour(space,heading)); // TODO : Der skal findes det rigtige space at smide spilleren hen, kaster også en exception


        return false; // True hvis det lykkedes, false hvis ikke.
    }

}
