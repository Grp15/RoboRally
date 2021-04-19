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

    /**
     * Obtained by getting shot, falling of the board, or into a pit
     */

    //TODO: Skal gøres så de kun kommer i spillerens bunke når spilleren har taget skade

    //   ---DAMAGE CARDS---
    //Spam skal ikke kunne gøre noget hvis den lægges i program bunken
    SPAM("Spam"),

    TROJAN_HORSE("Trojansk Hest"),  // Smid ind igen når SPAM virker
    /*
    VIRUS("Virus"), // Skal implementeres
    WORMS("Worms"), // Skal implementeres efter reboot er implementeret
     */


    //  ---PROGRAMMING CARDS---

    AGAIN("Again"),
    FORWARD("Fwd"),
    TWOFORWARD("2 Fwd"),
    THREEFOWARD("3 Fwd"),
    BACK_UP("Back up"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    UTURN("U-turn"),
    POWER_UP("Power up"),




    //TODO: Skal ændres efter upgrade phase er indført
    //  ---SPECIAL PROGRAMMING CARDS---

    /**
     * Obtained by installing temprorary upgrades
     */

    ENERGY_ROUTINE("Energy"),
    REPEAT_ROUTINE("Repeat"),

    //TODO: Skal ændre i udseende for Sandbox
    OPTION_SANDBOX_ROUTINE("Sandbox",FORWARD,TWOFORWARD,THREEFOWARD,BACK_UP,LEFT,RIGHT,UTURN),
    //SPAM_FOLDER("Spam"),  // Skal implementeres
    SPEED_ROUTINE("Speed"),
    OPTION_WEASELROUTINE("Left, Right or U-turn", LEFT, RIGHT, UTURN);





    final private String displayName;

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