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
 * The CommandCardField class inheritance from Subject and is responsible for a players command cards.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class CommandCardField extends Subject {

    final public Player player;

    private CommandCard card;

    private boolean visible;

    /**
     * Constructor for CommandCardField
     *
     * @param player the player whose command card fields it is
     */
    public CommandCardField(Player player) {
        this.player = player;
        this.card = null;
        this.visible = true;
    }

    /**
     * Returns a CommandCard
     *
     * @return command card
     */
    public CommandCard getCard() {
        return card;
    }

    /**
     * Set a new command card and notify changes.
     *
     * @param card new command card
     */
    public void setCard(CommandCard card) {
        if (card != this.card) {
            this.card = card;
            notifyChange();
        }
    }

    /**
     * Get visability of command card field
     *
     * @return if command card field is visible return true or else false
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set visablity for command card field
     *
     * @param visible if visible (true)
     *                if not visible (false)
     */
    public void setVisible(boolean visible) {
        if (visible != this.visible) {
            this.visible = visible;
            notifyChange();
        }
    }
}
