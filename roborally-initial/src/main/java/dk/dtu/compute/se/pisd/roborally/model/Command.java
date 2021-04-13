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
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public enum Command {

    // This is a very simplistic way of realizing different commands.


    // PROGRAMMING CARDS

    //AGAIN("Again"),
    BACK_UP("Back up");

    /*
    FORWARD("Fwd"),
    TWOFORWARD("2 Fwd"),
    THREEFOWARD("3 Fwd"),
    POWER_UP("Power up"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    UTURN("U-turn"),

    // SPECIAL PROGRAMMING CARDS

    ENERGY_ROUTINE("Energy"),
    REPEAT_ROUTINE("Repeat"),
    SANDBOX_ROUTINE("Sandbox"),
    SPAM_FOLDER("Spam"),
    SPEED_ROUTINE("Speed"),
    OPTION_WEASELROUTINE("Left, Right or U-turn", LEFT, RIGHT, UTURN),

    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT);

     */




    final private String displayName;

    // XXX Assignment V3
    // Command(String displayName) {
    //     this.displayName = displayName;
    // }
    //
    // replaced by the code below:

    final private List<Command> options;


    /**
     * Displays the name of the command along with a list of options
     * @param displayName
     * @param options
     */
    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    public boolean isInteractive() {
        return !options.isEmpty();
    }

    public List<Command> getOptions() {
        return options;
    }

    public String getDisplayName(){
        return displayName;
    }

}