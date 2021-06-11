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

import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.PriorityAntenna;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

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

        if (player != null) {
            player.setSpace(space);
        }
    }



    public void startInitiliasationPhase(){
        board.setPhase(Phase.INITIALISATION);

        board.setCurrentPlayer(board.getPlayer(0));
    }

    public void startGame(){
        for (int i = 0; i < board.getPlayersNumber(); i++){
            if(board.getPlayer(i).getSpace() == null){
                return;
            }
            board.setCounter(0);
            startProgrammingPhase();

        }
    }

    /**
     * This method initiates the programmingphase
     *
     */

    // XXX: V2

    // TODO: Needs to implement priority antenna here

    //TODO: Når spillerne har deres egen kortbunke skal de trække fra denne

    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        //System.out.println(board.getPlayer(0).getSpace().getSpaceType());

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
            }
        }
        // Checks whether a player picked up a spam cards and spam if he did
        CheckForTypeCards();
    }



    /**
     * Checks if the player has drawn any of following cards
     * 1) SPAM
     * 2) Kommer senere
     */

    public void CheckForTypeCards(){
        for (int i = 0; i < board.getPlayersNumber(); i++){

            for (int j = 0; j < Player.NO_CARDS; j++) {
            Player player = board.getPlayer(i);

                if (player.getCardField(j).getCard().getCommand() == Command.SPAM) {
                //System.out.println("Ur bout to get spammed " + board.getPlayerNumber(player));
                executeCommand(player, Command.SPAM);
                }

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

        //Array with the distances from antenna to player
        //TODO: Priority Antenna needs to not be hardcoded
        //TODO: playerOrder should be put in Board class

        Player[] printOrder = new Player[board.getPlayersNumber()]; //board.getPlayerOrder();

        for(int i = 0; i < board.getPlayersNumber(); i++ ){
            printOrder[i] = board.getPlayer(i);
        }

        Space space = board.getPriorityAntenna();
        findPlayerOrder(printOrder, space.x , space.y);
        board.setPlayerOrder(printOrder);


        board.setPhase(Phase.ACTIVATION);

        //Needs to set the right player turns here
        //board.setCurrentPlayer(board.getPlayer(0)); // Old method
        board.setCurrentPlayer(board.getPlayerfromPlayerOrder(0));
        board.setStep(0);
        //executePrograms(); // V3.5
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

    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                //int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1; // Gammel metode som virker
                int nextPlayerNumber = board.getPlayerNumberfromPlayerOrder(currentPlayer) +1;
                if (card != null) {
                    Command command = card.getCommand();
                    if(command.isInteractive()){
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    else executeCommand(currentPlayer, command);}

                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayerfromPlayerOrder(nextPlayerNumber));
                } else {



                    for(int i = 0; i < board.getPlayersNumber(); i++) {

                        if (currentPlayer == null) return;

                        GameController gameController = this;
                        Player player = board.getPlayer(i);
                        Space space = player.getSpace();

                        for(FieldAction action : space.getActions()) {

                            if (action instanceof ConveyorBelt) {
                                action.doAction(gameController, space, player);
                            }
                            else if (action instanceof Gears){
                                action.doAction(gameController,space,player);
                            }
                            else if (action instanceof CheckPoint){
                                action.doAction(gameController,space,player);
                            }
                            else if (action instanceof Energy){
                                action.doAction(gameController,space,player);
                            }
                            else if (action instanceof StartField){
                                action.doAction(gameController, space,player);
                            }
                            else if (action instanceof PriorityAntenna){
                                action.doAction(gameController, space,player);
                            }


                        }




                        //space.doAction(player,space,gameController);




                    }


                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                       // board.setCurrentPlayer(board.getPlayer(0)); // Gammel metode
                        board.setCurrentPlayer(board.getPlayerfromPlayerOrder(0)); // Ny metode
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

                case TROJAN_HORSE:
                    this.Trojanhorse(player);
                    break;

                case VIRUS:
                    this.Virus(player);
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

                case REPEAT_ROUTINE:
                    this.Again(player);
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

        //int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1; // Gammel metode som virker
        int nextPlayerNumber = board.getPlayerNumberfromPlayerOrder(currentPlayer) +1; // Ny metode
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayerfromPlayerOrder(nextPlayerNumber));
        } else {
            int step = board.getStep() + 1;
            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                //board.setCurrentPlayer(board.getPlayer(0)); // Gammel metode
                board.setCurrentPlayer(board.getPlayerfromPlayerOrder(0)); // Ny metode
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

        if(player.getSpace().getWalls().contains(heading) || space.getWalls().contains(heading.next().next())){
            throw new ImpossibleMoveException(player,space,heading);
        }

        if (other != null){
            Space target = board.getNeighbour(space, heading);
            if(target == player.getSpace()){
                System.out.println("Du falder ud over banen");
            }
            if (target != null) {
                // XXX Note that there might be additional problems
                // with infinite recursion here!
                moveToSpace(other, target, heading);
            }

            else {
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


    public void Again(@NotNull Player player){
        if (board.getStep() == 0){
            return;
        }
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
        Heading heading = currentPlayer.getHeading();
        Space currentSpace = currentPlayer.getSpace();
        Space newSpace = board.getNeighbour(currentSpace, heading);

        try {
            moveToSpace(currentPlayer, newSpace, heading);
        } catch (ImpossibleMoveException e) {

            //e.printStackTrace();
        }
    }

    /**
     * Moves a player forward 2 spaces towards the direction the player is currently facing
     * @param player
     */
    // TODO Assignment V2 - NOTE: is there a better option? is it always two spaces?
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
        player.setHeading(player.getHeading().next());
    }

    /**
     * Set a current players direction to turn left of current heading.
     * @param player current player
     */
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
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
     * Spam a plater by moving top card in program card to register
     * @param player
     */


    /**
     * Implements spam card, which moves 1st card from program field to register field
     * @param player
     */

    //TODO: Implementer i ExecuteNextStep at spillere der har SPAM kort på hånden automatisk får flyttet 1 kort fra hånden
    // over i deres register.
    // Måske det skal implementeres i StartProgrammingPhase();

    public  void Spam(@NotNull Player player) {

        for (int i = 0; i < player.NO_REGISTERS; i++) {

            if (player.getProgramField(i).getCard() == null) {
                moveCards(player.getCardField(i), player.getProgramField(i));
                return;
            }
        }
    }

    //TODO: Need to implement that the player add 2 SPAM card to his bile for now it works like a spam

    public void Trojanhorse(@NotNull Player player){
        executeCommand(player, Command.SPAM);


    }

    //TODO: Implement players within the radius draw cards

    //Every player within 6 fields gets a virus damage card
    //Ide er at se hvor spillere er fra 0 til antal spillere, og se hvem der er indenfor 6 felter

    public void Virus(@NotNull Player player){

        for(int i = 0; i < board.getPlayersNumber(); i++){
            Player otherPlayer = board.getPlayer(i);
/*
            if(otherPlayer != player){

                if (player.CalculateDistanceToPlayer(otherPlayer) < 6){
                    System.out.println(otherPlayer.getName() + " Du har fået virus");
                }
            }
            */
        }

    }

    //TODO: Reboots the robot
    public void Worms(@NotNull Player player){
        notImplemented();
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

    /**
     * Used to determine who starts the round
     * @param space
     * @param player
     * @return
     */

    public int DistanceSpacetoPlayer(Space space, Player player){

        int xDistance = space.x - player.getSpace().x;
        int yDistance = space.y - player.getSpace().y;

        int distance = Math.abs(xDistance) + Math.abs(yDistance);

        return distance;
    }

    /**
     * Create in which order players turn is
     *
     * @return player order
     */

    // TODO: Need to attach reference from distance array to a player
    // TODO: Needs to set which players turn it is
    // TODO: Needs to be called at start of each turn, for now it only does anything if a player stands on the field.
    // TODO: For now this is just an ordinary space, should be executed in a different way than other spaces and not hardcoded
    // TODO: Skal bruge en test
    public Player[] findPlayerOrder(Player[] players, int x, int y) {

        Player[] playerdistance =players;

        for(int i = 0; i < board.getPlayersNumber(); i++){
            playerdistance[i] = board.getPlayer(i);

        }


        for (int i = 0; i < playerdistance.length; i++) {

            playerdistance[i].setDistancetoAntenna(DistanceSpacetoPlayer(board.getSpace(x, y), playerdistance[i]));


            //Sets the players distance to antenna
            //getBoard().getPlayer(i).setDistancetoAntenna(playerdistance[i]);

        }
        //Sorts Playerdistance
        sort(playerdistance);


        return playerdistance;
    }
    /**
     * Sorts a distancefromspacetoplayer array using bubblesort algorithm
     *
     * @param playerDistance
     */

    public void sort(Player[] playerDistance) {

        Player temp;
        for (int i = 0; i < playerDistance.length; i++) {
            for (int j = 1; j < (playerDistance.length - i); j++) {
                if (playerDistance[j - 1].getDistancetoAntenna() > playerDistance[j].getDistancetoAntenna()) {
                    //swap elements
                    temp = playerDistance[j - 1];
                    playerDistance[j - 1] = playerDistance[j];
                    playerDistance[j] = temp;
                }

            }
        }

    }

}

