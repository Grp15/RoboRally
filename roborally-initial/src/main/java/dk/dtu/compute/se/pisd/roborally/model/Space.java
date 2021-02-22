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

/**
 * The Space class inheritance from Subject and is responsible for the spaces in board.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class Space extends Subject {

    public final Board board;

    public final int x;
    public final int y;

    private Player player;

    /**
     * Constructor for space takes a board and coordinates as parameters
     *
     * @param board affiliated board
     * @param x x-coordinate for placement of space
     * @param y y-coordinate for placement of space
     */
    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
    }

    /**
     * Return player that are on space
     *
     * @return player placed on space
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Place a new player on space if there are any and notify changes
     *
     * @param player player to place on space
     */
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

    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

}
