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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the view of different spaces on the board and colors and sizes hereof
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 75;
    final public static int SPACE_WIDTH = 75;

    public final Space space;

    /**
     * Changes sizes and colours of spaces view
     *
     * @param space
     */

    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    /**
     * Updates the positions of the player on the board
     */

    private void updatePlayer() {
        this.getChildren().clear(); // This clears all elements on the board and if removed the player can be placed on top of the elements

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0);
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
            this.getChildren().add(arrow);
        }
    }


    /**
     * Updates the view of the board by inserting the given spaces and players
     *
     * @param subject
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     * @author S205472
     */

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {

            updatePlayer(); // if the player if updated here, the other elements are placed over the player

                for (FieldAction fieldAction : space.getActions()) {

                    if (fieldAction instanceof ConveyorBelt) {
                        ConveyorBeltView.drawConveyorBelt(this, space);
                    }
                    if (fieldAction instanceof Gears) {
                        GearView.drawGear(this, space);
                    }

                    if (fieldAction instanceof CheckPoint) {
                        CheckpointView.drawCheckpoint(this, space);
                    }

                    if (fieldAction instanceof Energy) {
                        EnergyView.drawEnergy(this, space);
                    }

                    if (fieldAction instanceof StartField) {
                        StartFieldView.drawStartField(this, space);

                    }
                    if (fieldAction instanceof PriorityAntenna) {
                        PriorityAntennaView.drawPriorityAntenna(this, space);
                    }

                }
            for (Heading heading : space.getWalls()) {
                if (heading instanceof Heading) {
                    WallView.drawWall(this, space);
                }
            }
        }

    }
}
