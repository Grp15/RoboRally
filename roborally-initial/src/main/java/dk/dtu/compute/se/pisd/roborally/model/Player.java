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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * Model class handling states of the players.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class Player extends Subject {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;

    private String name;
    private String color;
    private int EnergyCubes = 5;
    private int CheckPoints = 0;
    private int DistancetoAntenna = 0;
    private Space StartSpace;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program; // Register
    private CommandCardField[] cards;  // Kort på hånd



    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;

        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }


    public int getDistancetoAntenna(){
        return DistancetoAntenna;
    }

    public void setDistancetoAntenna(int i){
        DistancetoAntenna = i;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    public void setProgramField(CommandCardField commandCardField, int i){
        program[i] = commandCardField;
    }

    public void setCardField(CommandCardField commandCardField, int i){
        cards[i] = commandCardField;}


    public String getCards() {
        String ProgrammingCards = "";

        for (int i = 0; i <= (cards.length - 1); i++) {
            try {

                ProgrammingCards += getCardField(i).getCard().getCommand().getDisplayName() + ",";
            }
            catch(NullPointerException e){
            ProgrammingCards += " ,";
            continue;
        }
    }
       // System.out.println("Programming = " + ProgrammingCards);
        return ProgrammingCards;
}


    public String getProgram() {
        String RegisterCards = "";

        for (int i = 0; i <= (program.length - 1); i++) {

            try {


                RegisterCards += getProgramField(i).getCard().getCommand().getDisplayName() + ",";
            }

            catch(NullPointerException e){
                RegisterCards += " ,";
                continue;
            }

        }
        //System.out.println("Register = " + RegisterCards);
        return RegisterCards;
    }

    /**
     * Splits cards in register into string array
     *
     * @param load_register
     * @return register
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */

    public String[] splitCardsRegisterString(String load_register) {

        String[] register = load_register.split(",",-1);

        return register;
    }


    /**
     * Splits cards in programming card section into string array
     *
     * @param load_program
     * @return program
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */

    public String[] splitCardsProgramString(String load_program) {

        String[] program = load_program.split(",",-1);

        return program;
    }

    /**
     * Add 1 energy cube to the energy cubes
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */

    public void addEnergy(){
        EnergyCubes = EnergyCubes + 1;
    }

    public int getEnergyCubes(){
        return EnergyCubes;
    }


    /**
     * Determines the distance to a player from a
     *
     * @param player
     * @return distance
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */
    public int CalculateDistanceToPlayer(Player player){
        int xDistance = Player.this.getSpace().x - player.getSpace().x;
        int yDistance = Player.this.getSpace().y - player.getSpace().y;

        int distance = Math.abs(xDistance) + Math.abs(yDistance);
        return distance;
    }

    public int getCheckPoints(){
        return CheckPoints;
    }


    /**
     * Increment checkpoints one time
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */

    public void progressCheckpoint(){
        CheckPoints++;
    }

    /**
     * Return robot to start space and set checkpoint counter back to 0
     *
     * @author S164539 Hildibjørg
     * @author S154780 Troels
     */

    public void RebootRobot(){
        CheckPoints = 0;
        setSpace(StartSpace);

    }

    public void setStartSpace(Space space){
        StartSpace = space;
    }

    public Space getStartSpace(){
        return StartSpace;
    }


}
