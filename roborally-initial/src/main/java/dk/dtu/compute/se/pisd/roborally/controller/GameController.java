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
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Spaces.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.*;
//import dk.dtu.compute.se.pisd.roborally.model.ConveyorBelt;
import org.jetbrains.annotations.NotNull;
import static dk.dtu.compute.se.pisd.roborally.model.Spaces.SpaceType.CONVEYORBELT;

/**
 * Gamecontroller conatains methods for all the game logic like initiating phases and moving players
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author s205436
 * @author s164539
 * @author s152780
 * @author s205472
 * @author s194612
 *
 */
public class GameController {

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {

        Player current = board.getCurrentPlayer();
        if(current == null) return;
        int currentInt = board.getPlayerNumber(current);
        Player nextPlayer = board.getPlayer((currentInt+1) % board.getPlayersNumber());

        if (space.getPlayer() == null){
            current.setSpace(space);
            board.setCurrentPlayer(nextPlayer);
            board.setCounter(board.getCounter()+1);
        }
    }

    public void movePlayerToSpace(@NotNull Space space, Player player) {

        if (player == null) {
            player.setSpace(space);
        }
    }

    /**
     * This method initiates the programmingphase
     *
     */

    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }

                // TODO: SPAM skal nok implementeres her

                /*

                for (int j = 0; j < Player.NO_CARDS; j++){
                    if(player.getCardField(i).getCard().getCommand() == Command.SPAM){
                        Spam(player);
                        player.getCardField(i).setVisible(false);
                        System.out.println("Du har et spamkort" + board.getPlayerNumber(player));
                    }
                }
                */
            }
        }
    }

    /**
     * This method generates one random of the games command card for the player
     *
     * @return CommandCard
     */

    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // TODO: Finish LoadCommandCardsFromDisplayName() Maybe rename method



    private void setCards(CommandCard[] cards, Player player){
        for(int i = 0; i < cards.length; i++){
            player.getCardField(i).setCard(cards[i]);
        }
    }







    /**
     * This method ends the programming phase
     */

    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    /**
     * This method makes the programfield visible
     * @param register
     */

    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * This method makes the programfield invisible
     */

    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * This method execute all programming cards in the register
     */

    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * This method executes the first step in the register
     */

    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Executes program as long as activation phase is on and stepmode is off
     */

    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * Executes next step from a players register
     */

    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (card != null) {
                    Command command = card.getCommand();
                    if(command.isInteractive()){
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    //Hvis 1 kort i register er AGAIN Kort springes det over
                    if(card.getCommand() == Command.AGAIN && step == 0){
                        board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));

                    }else executeCommand(currentPlayer, command);


                }
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {

                    for(int i = 0; i < board.getPlayersNumber(); i++) {

                        if (currentPlayer == null) return;

                        GameController gameController = this;
                        Player player = board.getPlayer(i);
                        Space space = player.getSpace();
                        space.doAction(player,space,gameController);




                    }

                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    // XXX: V2

    /**
     * Runs the method connected to the command card
     * @param player
     * @param command
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {

                /**
                 *  --- Damage Cards ---
                 */

                case SPAM:
                    this.Spam(player);
                    break;

                /**
                 *  --- Programming Cards ---
                 */

                case AGAIN:
                    this.Again(player);
                    break;

                case BACK_UP:
                    this.Back_Up(player);
                    break;

                case FORWARD:
                    this.moveForward(player);
                    break;

                case TWOFORWARD:
                    this.movetwoForward(player);
                    break;

                case THREEFOWARD :
                    this.movethreeForward(player);
                    break;

                case POWER_UP:
                    this.Powerup(player);
                    break;

                case RIGHT:
                    this.turnRight(player);
                    break;

                case LEFT:
                    this.turnLeft(player);
                    break;


                case UTURN:
                    this.Uturn(player);
                    break;
                /**
                 *  --- Special Programming Cards ---
                 */

                case ENERGY_ROUTINE:
                    this.Powerup(player);
                    break;

                case SPEED_ROUTINE:
                    this.movethreeForward(player);
                    break;


                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /**
     * .....
     * Changes the phrase to Activation (with options) and executes command card
     * @param command executed option
     */
    public void executeCommandAndContinue(Command command) {
        board.setPhase(Phase.ACTIVATION);
        Player currentPlayer = board.getCurrentPlayer();
        executeCommand(currentPlayer, command);

        int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
        } else {
            int step = board.getStep() + 1;
            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                board.setCurrentPlayer(board.getPlayer(0));
            } else {
                startProgrammingPhase();
            }
        }

        continuePrograms(); // V3.5 (continue)

    }

    /**
     * Exception which is throwed when a player cant perform asked move
     */

    public class ImpossibleMoveException extends Exception {
        private Player player;
        private Space space;
        private Heading heading;
        public ImpossibleMoveException(Player player,
                                       Space space,
                                       Heading heading) {
            super("Move impossible");
            this.player = player;
            this.space = space;
            this.heading = heading;
        }
    }

    /**
     * .....
     * @param player player which is moved
     * @param space space to move to
     * @param heading direction player is facing
     * @throws ImpossibleMoveException
     */
    public void moveToSpace(
            @NotNull Player player,
            @NotNull Space space,
            @NotNull Heading heading) throws ImpossibleMoveException {
        Player other = space.getPlayer();
        if (other != null){
            Space target = board.getNeighbour(space, heading);
            if (target != null) {
                // XXX Note that there might be additional problems
                // with infinite recursion here!
                moveToSpace(other, target, heading);
            } else {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        player.setSpace(space);
}


// ----------------------------------------- Programming Cards ---------------------------------------


    /**
     *
     * Implements the Again card, which repeats the previous executed card from the players register
     * @param player
     */


    //TODO: Skal køre forrige kort igen, må ikke være første kort i Register
    //TODO: Skal opdateres når damage card og special upgrade indføres

    public void Again(@NotNull Player player){
        CommandCardField card = player.getProgramField(board.getStep() -1);
        Command command = card.getCard().getCommand();

        if (board.getStep() == 0){
            return;
        }
        executeCommand(player,command);
    }

    /**
     * Moves a player forward in the direction he is facing. Or if he is standing on a conveyerbelt moves the player
     * in the direction the conveyor belt is facing
     * @param currentPlayer current player
     */
    public void moveForward(@NotNull Player currentPlayer){
        //Player currentPlayer = board.getCurrentPlayer();

        Heading heading;

        if(currentPlayer.getSpace().getSpaceType() == CONVEYORBELT){
            ConveyorBelt belt = (ConveyorBelt) currentPlayer.getSpace();
            heading = belt.getHeading();
        }
        else {
            heading = currentPlayer.getHeading();
        }

        Space currentSpace = currentPlayer.getSpace();
        Space newSpace = board.getNeighbour(currentSpace, heading);

        //currentPlayer.setSpace(newSpace);
        try {
            moveToSpace(currentPlayer, newSpace, heading);
        } catch (ImpossibleMoveException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves a player forward 2 spaces towards the direction the player is currently facing
     * @param player
     */
    public void movetwoForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    /**
     * Moves a player forward 3 spaces towards the direction the player is currently facing
     * @param player
     */

    public void movethreeForward(@NotNull Player player){
        moveForward(player);
        moveForward(player);
        moveForward(player);
    }

    /**
     * Moves a player backwards 1 pace towards the opposite direction the player is currently facing
     * @param player
     */

    public void Back_Up(@NotNull Player player){
        Heading heading = player.getHeading().next().next();
        Space space = player.getSpace().board.getNeighbour(player.getSpace(),heading);


        try {
            moveToSpace(player, space,heading);
        } catch (ImpossibleMoveException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set a current players direction to turn right of current heading.
     * @param player current player
     */
    public void turnRight(@NotNull Player player) {
        Player currentPlayer = board.getCurrentPlayer();
        Heading heading = currentPlayer.getHeading();
        currentPlayer.setHeading(heading.next());
    }

    /**
     * Set a current players direction to turn left of current heading.
     * @param player current player
     */
    public void turnLeft(@NotNull Player player) {
        Player currentPlayer = board.getCurrentPlayer();
        Heading heading = currentPlayer.getHeading();
        currentPlayer.setHeading(heading.prev());
    }

    /**
     * sets the heading of the player to it's opposite direction
     * @param player
     */

    public void Uturn(@NotNull Player player){
        player.setHeading(player.getHeading().next().next());
    }

    /**
     * Adds energy cube to player
     * @param player
     */

    public void Powerup(@NotNull Player player){
        player.addEnergy();
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }


    // ----------------------------------------- DAMAGE CARDS ---------------------------------------


    /**
     * Implements spam card, which moves 1st card from program field to register field
     * @param player
     */

    //TODO: Implementer i ExecuteNextStep at spillere der har SPAM kort på hånden automatisk får flyttet 1 kort fra hånden
    // over i deres register.
    // Måske det skal implementeres i StartProgrammingPhase();

    public  void Spam(@NotNull Player player){

        player.setProgramField(player.getCardField(0), 0);
        player.getProgramField(0).setVisible(false); //Doesnt seem like to work

    }



    // ----------------------------------------- Other ---------------------------------------

    public Board getBoard(){
        return board;
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

    public int DistanceSpacetoPlayer(Space space, Player player){

        int xDistance = space.x - player.getSpace().x;
        int yDistance = space.y - player.getSpace().y;

        int distance = Math.abs(xDistance) + Math.abs(yDistance);

        return distance;
    }

}