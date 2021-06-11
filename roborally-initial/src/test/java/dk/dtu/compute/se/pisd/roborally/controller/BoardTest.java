package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
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
        Board board = LoadBoard.loadBoard("defaultboard");
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



    @Test
    void TestTest(){
        Board board = gameController.board;

        Space space = board.getSpace(3,4);
        Player player = board.getCurrentPlayer();

        System.out.println(player.getCheckPoints());

        System.out.println(space.getActions());

        for (FieldAction c : space.getActions()){
            if (c instanceof CheckPoint){

            }
        }



    }





}
