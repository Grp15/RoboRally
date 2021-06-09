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
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.*;
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

    final public static int SPACE_HEIGHT = 75; // 60; // 75;
    final public static int SPACE_WIDTH = 75;  // 60; // 75;

    public final Space space;

    /**
     * Changes sizes and oclors of spaces view
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
     *
     */

    private void updatePlayer() {
        this.getChildren().clear(); // This clears all elements on the board and if removed the player can be placed on top of the elements

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }


/*
    public void drawSpaces () {

        //For heading set wall

        for (Space fieldAction: space.getActions()) {


            if (fieldAction instanceof PriorityAntenna) {
                PriorityAntennaView.drawPriorityAntenna(this, space);
            }

            if (fieldAction instanceof Gears) {
                GearView.drawGear(this, space);
            }
            if (fieldAction instanceof ConveyorBelt) {
                ConveyorBeltView.drawConveyorBelt(this, fieldAction);
            }
            if (fieldAction instanceof StartField) {
                StartFieldView.drawStartField(this, space);
            }
            if (fieldAction instanceof Energy) {
                EnergySpaceView.drawEnergySpace(this, space);
            }
            if (fieldAction instanceof CheckPoint) {
                CheckpointView.drawCheckpoint(this, space);
            }
        }
    }

 */

    /**
     * If player is not on space, then update player
     * @param subject
     */

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {

            updatePlayer(); // if the player if updated here, the other elements are placed over the player

            for (Space fieldAction: space.getActions()) {


                if (fieldAction instanceof PriorityAntenna) {
                    PriorityAntennaView.drawPriorityAntenna(this, fieldAction);
                }

                if (fieldAction instanceof Gears) {
                    GearView.drawGear(this, fieldAction);
                }
                if (fieldAction instanceof ConveyorBelt) {
                    ConveyorBeltView.drawConveyorBelt(this, fieldAction);
                }
                if (fieldAction instanceof StartField) {
                    StartFieldView.drawStartField(this, fieldAction);
                }
                if (fieldAction instanceof Energy) {
                    EnergySpaceView.drawEnergySpace(this, fieldAction);
                }
                if (fieldAction instanceof CheckPoint) {
                    CheckpointView.drawCheckpoint(this, fieldAction);
                }
            }
        }
    }

}
