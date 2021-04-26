package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    /**
     * Sets up the gameController with board, players, space and heading
     */
    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
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
     * Tests if the conveyorbelt pushes the player upwards
     */

    @Test
    void ConveyorBeltFromSouth(){

        // Står på (4,2)

        Board board = gameController.board;

        Player player = board.getCurrentPlayer();
        player.setHeading(Heading.NORTH);

        // ----- If player is below -----
        gameController.moveCurrentPlayerToSpace(board.getSpace(4, 3));
        gameController.moveForward(player);

        Space space = board.getSpace(4,2);

        space.doAction(space.getPlayer(),space,gameController);

        Assertions.assertEquals(board.getSpace(4, 1), player.getSpace());
    }

    @Test
    void ConveyorBeltFromWest(){
        Board board = gameController.board;

        Player player = board.getCurrentPlayer();
        player.setHeading(Heading.NORTH);

        // ----- If player is left -----
        gameController.moveCurrentPlayerToSpace(board.getSpace(3, 2));
        player.setHeading(Heading.EAST);
        gameController.moveForward(player);

        Space conveyor = player.getSpace();
        conveyor.doAction(conveyor.getPlayer(),conveyor,gameController);

        Assertions.assertEquals(board.getSpace(4, 1), player.getSpace());
    }


    /**
     * Test if the gears space works
     */


    @Test
    void Gears(){

        // Står på (5,3)

        Board board = gameController.board;

        Player player = board.getCurrentPlayer();
        player.setHeading(Heading.NORTH);
        gameController.moveCurrentPlayerToSpace(board.getSpace(5,3));
        Heading playerheading = player.getHeading();


        player.getSpace().doAction(player, player.getSpace(), gameController);


        Assertions.assertEquals(playerheading.next(), player.getHeading());
    }


}