package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests certain methods in GameController
 */

class GameControllerTest {

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
     * Tests a player moving to a specific space on the board
     */
    @Test
    void someTest() {
        Board board = gameController.board;

        Player player = board.getCurrentPlayer();
        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));

        Assertions.assertEquals(player, board.getSpace(0, 4).getPlayer(), "Player " + player.getName() + " should beSpace (0,4)!");
    }

    /**
     * Tests the moveForward() method, where
     * the player moves exactly one space in the heading SOUTH
     * to another space
     * @author s205436
     * @author s164639
     */
    @Test
    void moveForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setSpace(board.getSpace(0, 4));
        current.setHeading(Heading.SOUTH);

        gameController.moveForward(current);

        Assertions.assertEquals(board.getSpace(0, 5),current.getSpace(),"Player " + current.getName() + " should be at space (0,5) " + "Player is at " + current.getSpace());
    }

    /**
     * Tests the fastForward() method, where
     * the player heads SOUTH and moves two spaces
     * to another space
     * @author s152780
     */
    @Test
    void fastForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setSpace(board.getSpace(0, 4));
        current.setHeading(Heading.SOUTH);

        gameController.movetwoForward(current);

        Assertions.assertEquals(board.getSpace(0, 6),current.getSpace());
    }

    /**
     * Tests the turnRight() method, where
     * the player should change its heading
     * from SOUTH to WEST
     * @author s194612
     */
    @Test
    void turnRight() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setHeading(Heading.SOUTH);

        gameController.turnRight(current);

        Assertions.assertEquals(Heading.WEST,current.getHeading()); //South -> West
    }

    /**
     * Tests the turnLeft() method, where
     * the player should change its heading
     * from SOUTH to EAST
     * @author s205472
     */
    @Test
    void turnLeft() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setHeading(Heading.SOUTH);

        gameController.turnLeft(current);

        Assertions.assertEquals(Heading.EAST,current.getHeading()); //South -> East
    }
}