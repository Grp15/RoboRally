package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.CheckPoint;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Gears;
import dk.dtu.compute.se.pisd.roborally.fileaccess.Adapter;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldActionTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = LoadBoard.loadBoard("test");
        gameController = new GameController(board);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(1,i));
            player.setHeading(Heading.values()[(i + 1) % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    /**
     * Tears down the gameController after each test
     */
    @AfterEach
    void tearDown() {
        gameController = null;
    }


    /**
     * Tests if the right order of players is set
     * @Author Troels
     * @Author Hildibjørg
     *
     */

    @Test
    void PriorityAntenna(){
        Board board = gameController.board;

        //Picks a space and turns it to a Priority Antenna
        Space space = board.getSpace(5, 4);
        board.setPriorityAntenna(space);


        //Pick a player and places him 5 from antenna
        Player player1 = board.getPlayer(1);
        player1.setSpace(board.getSpace(2,2));

        //Pick a player and places him 4 from antenna
        Player player2 = board.getPlayer(0);
        player2.setSpace(board.getSpace(3,2));

        //Pick a player and places him 1 from antenna
        Player player3 = board.getPlayer(2);
        player3.setSpace(board.getSpace(5,3));


        Player[] playerOrder = new Player[board.getPlayersNumber()];

        for(int i = 0; i < board.getPlayersNumber(); i++){
            playerOrder[i] = board.getPlayer(i);
        }

        gameController.findPlayerOrder(playerOrder, board.getPriorityAntenna().x, board.getPriorityAntenna().y);



        Assertions.assertEquals(playerOrder[0], board.getPlayer(2));
        Assertions.assertEquals(playerOrder[1], board.getPlayer(0));
        Assertions.assertEquals(playerOrder[2], board.getPlayer(1));

    }

    /**
     * Tests whether the ConveyorBelt pushes a player towards west
     * @Author Troels
     * @Author Hildibjørg
     */

    @Test
    void ConveyorBeltFromSouth() {

        Board board = gameController.board;

        //Picks a space and turns it to a Conveyorbelt
        Space space = board.getSpace(1, 1);
        ConveyorBelt ConveyorBelt = new ConveyorBelt();
        ConveyorBelt.setHeading(Heading.EAST);
        space.getActions().add(ConveyorBelt);

        //Pick a player and places him below belt
        Player player = board.getPlayer(0);
        player.setHeading(Heading.NORTH);
        player.setSpace(board.getSpace(1,2));

        //Executes Conveyorbelt Action
        for (FieldAction action : space.getActions()) {

            if (action instanceof ConveyorBelt) {
            action.doAction(gameController, space, player);

        }
    }

    Assertions.assertEquals(board.getSpace(2, 1), player.getSpace());
    Assertions.assertEquals(Heading.NORTH, player.getHeading());
}

    /**
     * Tests if a conveyorbelt can push a player through a wall
     * @Author Troels
     * @Author Hildibjørg
     */




    @Test
    void ConveyorBeltFromWest(){
        Board board = gameController.board;

        //Picks a space and turns it to a Conveyorbelt
        //Add an eastern wall to space
        Space space = board.getSpace(1, 1);
        ConveyorBelt ConveyorBelt = new ConveyorBelt();
        ConveyorBelt.setHeading(Heading.EAST);
        space.getActions().add(ConveyorBelt);
        space.getWalls().add(Heading.EAST);

        //Pick a player and places him below belt
        Player player = board.getPlayer(0);
        player.setHeading(Heading.NORTH);
        player.setSpace(board.getSpace(1,2));

        //Executes Conveyorbelt Action
        for (FieldAction action : space.getActions()) {

            if (action instanceof ConveyorBelt) {
                action.doAction(gameController, space, player);

            }
        }

        Assertions.assertEquals(board.getSpace(1, 1), player.getSpace());
    }


    /**
     * Tests if the Gears Fieldaction works
     *
     * @Author Hildibjørg
     * @Author Troels
     *
     */


    @Test
    void GearsLeft(){

        Board board = gameController.board;

        //Picks a space and turns it to a Conveyorbelt
        //Add an eastern wall to space
        Space space = board.getSpace(1, 1);
        Gears Gear = new Gears();
        Gear.setDirection(Direction.Right);
        space.getActions().add(Gear);

        //Pick a player and places him below belt
        Player player = board.getPlayer(0);
        player.setHeading(Heading.NORTH);
        player.setSpace(board.getSpace(1,2));
        Heading next = player.getHeading().next();

        //Executes Conveyorbelt Action
        for (FieldAction action : space.getActions()) {

            if (action instanceof Gears) {
                action.doAction(gameController, space, player);

            }
        }


        Assertions.assertEquals(next, player.getHeading());
    }


    /**
     * Tests if the Gears Fieldaction works
     *
     * @Author Hildibjørg
     * @Author Troels
     *
     */


    @Test
    void GearsRight(){

        Board board = gameController.board;

        //Picks a space and turns it to a Conveyorbelt
        //Add an eastern wall to space
        Space space = board.getSpace(1, 1);
        Gears Gear = new Gears();
        Gear.setDirection(Direction.Left);
        space.getActions().add(Gear);

        //Pick a player and places him below belt
        Player player = board.getPlayer(0);
        player.setHeading(Heading.NORTH);
        player.setSpace(board.getSpace(1,2));
        Heading previous = player.getHeading().prev();

        //Executes Conveyorbelt Action
        for (FieldAction action : space.getActions()) {

            if (action instanceof Gears) {
                action.doAction(gameController, space, player);

            }
        }


        Assertions.assertEquals(previous, player.getHeading());
    }

    /**
     * Checks if the checkpoint FieldAction works
     * @Author Hildibjørg
     * @Author Troels
     */

    @Test
    void Checkpoint(){
        Board board = gameController.board;

        //Picks a space and turns it to a Checkpoint1
        Space space = board.getSpace(1, 1);
        CheckPoint Checkpoint = new CheckPoint();
        Checkpoint.setNumber(1);
        space.getActions().add(Checkpoint);

        //Pick a player and places him below Checkpoint
        Player player = board.getPlayer(0);
        player.setHeading(Heading.NORTH);
        player.setSpace(board.getSpace(1,2));
        Heading previous = player.getHeading().prev();

        Assertions.assertEquals(0,player.getCheckPoints());

        gameController.moveForward(player);

        //Executes Checkpoint Action
        for (FieldAction action : space.getActions()) {

            if (action instanceof CheckPoint) {
                action.doAction(gameController, space, player);

            }
        }

        Assertions.assertEquals(1,player.getCheckPoints());

    }

    /**
     * Checks if Player can land on checkpoint 2
     * @Author Hildibjørg
     * @Author Troels
     */

    /**
     * Checks if the player has reached max checkpoint
     * @Author Hildibjørg
     * @Author Troels
     */


}
