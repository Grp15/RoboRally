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
import dk.dtu.compute.se.pisd.roborally.model.Spaces.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {

    public final int width;

    public final int height;

    public final String boardName;

    private Integer gameId;

    private final Space[][] spaces;

    private final List<Player> players = new ArrayList<>();

    private Player[] playerOrder = new Player[players.size()];

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;

    private int counter;

    private int numbOfCheckPoints = 0;

    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];

        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;

                if(x == 4 && y == 2){
                    ConveyorBelt belt = new ConveyorBelt(this, x, y, NORTH);
                    spaces[x][y] = belt;
                }

                if(x == 3 && y == 3){
                    PriorityAntenna priorityAntenna = new PriorityAntenna(this, x,y);
                    spaces[x][y] = priorityAntenna;
                }

                if(x== 4 && y == 1){
                    CheckPoint checkPoint = new CheckPoint(this,x,y,1);
                    spaces[x][y] = checkPoint;
                    numbOfCheckPoints++;
                }

                if(x== 5 && y == 1){
                    CheckPoint checkPoint = new CheckPoint(this,x,y,2);
                    spaces[x][y] = checkPoint;
                    numbOfCheckPoints++;
                }

                if(x == 5 && y == 3 ){
                    Gears gear = new Gears(this,x,y, Direction.Right);
                    spaces[x][y] = gear;
                }

                if(x == 2 && y == 6 ){
                    Gears gear = new Gears(this,x,y, Direction.Left);
                    spaces[x][y] = gear;
                }

                if(x == 0 && y == 0){
                    StartField start = new StartField(this,x,y);
                    spaces[x][y] = start;
                }
                if(x == 2 && y == 4){
                    Energy energy = new Energy(this,x,y);
                    spaces[x][y] = energy;
                }

            }
        }
        this.stepMode = false;
    }

    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    public Player getCurrentPlayer() {
        return current;
    }

    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        /* /TODO:
        if (space.getWalls().contains(heading)) {
            return null;
        }

         */
        // TODO needs to be implemented based on the actual spaces
        //      and obstacles and walls placed there. For now it,
        //      just calculates the next space in the respective
        //      direction in a cyclic way.

        // XXX an other option (not for now) would be that null represents a hole
        //     or the edge of the board in which the players can fall

        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }
        Heading reverse = Heading.values()[(heading.ordinal() + 2)% Heading.values().length];
        Space result = getSpace(x, y);
        /*if (result != null) { /TODO
            if (result.getWalls().contains(reverse)) {
                return null;
            }
        }
        return result;

         */
        return result;
    }

    public String getStatusMessage() {
        // This is actually a view aspect, but for making the first task easy for
        // the students, this method gives a string representation of the current
        // status of the game

        // TODO Assignment V1: this string could eventually be refined
        //      The status line should show more information based on
        //      situation; for now, introduce a counter to the Board,
        //      which is counted up every time a player makes a move; the
        //      status line should show the current player and the number
        //      of the current move!
        return "Player = " + getCurrentPlayer().getName() + ", Number of moves = " + getCounter() +" " + ", Number of checkpoints = " + getCurrentPlayer().getCheckPoints() + ", "  + "Number of Energy cubes = " + getCurrentPlayer().getEnergyCubes();
    }

    // TODO Assignment V1: add a counter along with a getter and a setter, so the
    //      state the board (game) contains the number of moves, which then can
    //      be used to extend the status message including the number of
    public int getCounter() {
        return counter;
    }

    // NOTE: if-statement? -> notifyChange() is only needed if counter value changes.
    public void setCounter(int counter) {
        if (this.counter != counter) {
            this.counter = counter;
            notifyChange();
        }
    }

    public Player getPlayerfromPlayerOrder(int i){
        if(i < players.size()){
            return playerOrder[i];
        }
        else {
            return playerOrder[i];
        }
    }

    public int getPlayerNumberfromPlayerOrder(Player player){
        //return players.indexOf(player); // virker ikke

        for(int i = 0; i < players.size(); i++){
            if (player == playerOrder[i]) {
                return i;
            }
        }
        return 0;
    }


    public void setPlayerOrder(Player[] playerOrder){
        this.playerOrder = playerOrder;
    }

    public int getNumbOfCheckPoints() {
        return numbOfCheckPoints;
    }
}
