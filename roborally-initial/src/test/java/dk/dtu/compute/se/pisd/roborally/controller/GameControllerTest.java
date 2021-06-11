package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
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

    @Test
    void wallMoveForwardTest() throws GameController.ImpossibleMoveException {
        Board board = gameController.board;
        Space space = board.getSpace(5,1);
        space.setWalls(Heading.SOUTH);
        Player player = board.getPlayer(0);
        player.setHeading(Heading.SOUTH);

        board.setCurrentPlayer(player);


        gameController.moveCurrentPlayerToSpace(space);
        gameController.moveForward(player);


        Assertions.assertEquals(board.getSpace(5,1), player.getSpace() );

    }

    @Test
    void wallMoveBackTest(){
        Board board = gameController.board;

        Space space = board.getSpace(6,1);
        space.setWalls(Heading.NORTH);

        Player player = board.getPlayer(0);
        player.setHeading(Heading.SOUTH);

        board.setCurrentPlayer(player);


        gameController.moveCurrentPlayerToSpace(space);
        gameController.Back_Up(player);

        System.out.println(player.getSpace().x + " " + player.getSpace().y);

        Assertions.assertEquals(board.getSpace(6,1), player.getSpace());

    }

    @Test
    void wallMovePlayerThroughWallTest(){
        Board board = gameController.board;

        Space space = board.getSpace(5,1);
        space.setWalls(Heading.SOUTH);

        Player player1 = board.getPlayer(1);
        player1.setHeading(Heading.SOUTH);

        Player player2 = board.getPlayer(2);
        player2.setHeading(Heading.SOUTH);


        gameController.movePlayerToSpace(space, player1);
        gameController.movePlayerToSpace(board.getSpace(5,0), player2);
        gameController.moveForward(player2);




        Assertions.assertEquals(board.getSpace(5,1), player1.getSpace());
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
     * Tests the twoForward(), where
     * the player heads SOUTH and moves two spaces
     * to another space
     * @author s152780
     */
    @Test
    void twoForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setSpace(board.getSpace(0, 4));
        current.setHeading(Heading.SOUTH);

        gameController.movetwoForward(current);

        Assertions.assertEquals(board.getSpace(0, 6),current.getSpace());
    }

    /**
     * Tests the threeforard() where the player moves three spaces in the direction he is facing
     */

    @Test
    void threeForward(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setSpace(board.getSpace(0, 4));
        current.setHeading(Heading.SOUTH);

        gameController.movethreeForward(current);

        Assertions.assertEquals(board.getSpace(0, 7),current.getSpace());
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

    /**
     * Tests for the Uturn(), where the player should change direction to opposite
     * in this case from south to north
     */

    @Test
    void UTurn(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        current.setHeading(Heading.SOUTH);

        gameController.Uturn(current);

        Assertions.assertEquals(Heading.NORTH,current.getHeading());
    }


    /**
     * Tests the BackUp(), as the player is expected to move one space backward from
     * their current position
     */

    @Test
    void BackUp(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        Space Targetspace = board.getNeighbour(current.getSpace(), current.getHeading().next().next());

        gameController.Back_Up(current);

        Assertions.assertEquals( Targetspace , current.getSpace());
    }

    /**
     * Adds an energy cube to the playermat
     */

    @Test
    void PowerUp(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        int currentAmount = current.getEnergyCubes();

        gameController.Powerup(current);

        Assertions.assertEquals(current.getEnergyCubes(),currentAmount+1);
    }

    /**
     * Tests the Again() which plays previous card from register again, if its the 1st card in register
     * nothing happens
     */

    @Test
    void Again(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        CommandCard forward = new CommandCard(Command.FORWARD);
        CommandCardField card = new CommandCardField(current);
        card.setCard(forward);

        CommandCard again = new CommandCard(Command.AGAIN);
        CommandCardField againcard = new CommandCardField(current);
        againcard.setCard(again);

        Space space = current.getSpace();
        Heading heading =current.getHeading();

        current.setProgramField(card,0);
        current.setProgramField(againcard,1);

        gameController.moveForward(current);
        board.setStep(1);
        gameController.Again(current);

        Space expectedSpace = board.getNeighbour(space ,heading);
        space = expectedSpace;
        expectedSpace = board.getNeighbour(space ,heading);


        Assertions.assertEquals(expectedSpace , current.getSpace());
    }




    // ------------------ Damage Cards ------------------

    /**
     * Tests the spam(), where the player should then place the first card from his programming hand,
     * to his register i.e the command in the register needs to be the same
     */


    @Test
    void Spam(){
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Command card;

        gameController.startProgrammingPhase();


        // If player has drawn spam card do nothing
        boolean hasdrawn = false;
        if(current.getProgramField(0).getCard() != null){
                hasdrawn = true;
        }

        if(hasdrawn == true) {
            card = current.getProgramField(0).getCard().getCommand();

        } else{
            card = current.getCardField(0).getCard().getCommand();
            gameController.Spam(current);
        }

        Assertions.assertEquals(card, current.getProgramField(0).getCard().getCommand());
    }
}





























