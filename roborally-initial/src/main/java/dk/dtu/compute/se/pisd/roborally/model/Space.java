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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class for handling general settings about players positions on spaces and their calling of fieldactions
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 */
public class Space extends Subject {

    public final Board board;

    public final int x;
    public final int y;

    private Player player;


    private List<Heading> walls = new ArrayList<>();
    private List<FieldAction> actions = new ArrayList<>();


    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
    }

    public Player getPlayer() {
        return player;
    }



    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }

    /**
     * Get method for wall headings
     *
     * @return walls
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public List<Heading> getWalls() {
        return walls;
    }


    /**
     * Get method for actions
     *
     * @return actions
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     *
     */
    public List<FieldAction> getActions() {
        return actions;
    }

    /**
     * Set method for appointing heading to walls
     *
     * @param heading
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public void setWalls(Heading heading){
        walls.add(heading);
    }



    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

    /**
     * Method for performing an action on spaces
     *
     * @param player
     * @param space
     * @param gameController
     * @return false
     * @author S164539
     * @author S154780
     * @author S205472
     * @author S194612
     */
    public boolean doAction(Player player, Space space, GameController gameController) {
        return false;

    }
}

