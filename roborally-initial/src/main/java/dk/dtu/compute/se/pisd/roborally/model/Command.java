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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Command class is an enumiration and is responsible for the different commands for the CommandCards.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author s205436
 * @author s164539
 * @author s152780
 * @author s205472
 * @author s194612
 */
public enum Command {
    // This is a very simplistic way of realizing different commands.
    FORWARD("Fwd"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Fast Fwd"),

    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT);

    final public String displayName;

    final private List<Command> options;

    /**
     * Displays the name of the command along with a list of options
     * @param displayName name of command
     * @param options list of options
     */
    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    /**
     * Method that tells if a command's options is interactive or not.
     *
     * @return true/false depending if interactive
     */
    public boolean isInteractive() {
        return !options.isEmpty();
    }

    /**
     * Returns a list of Command options.
     *
     * @return options for command
     */
    public List<Command> getOptions() {
        return options;
    }

    /**
     * @author s205436
     * Returns display name for given command.
     *
     * @return displayname for command
     */
    public String getDisplayName(){
        return displayName;
    }

}