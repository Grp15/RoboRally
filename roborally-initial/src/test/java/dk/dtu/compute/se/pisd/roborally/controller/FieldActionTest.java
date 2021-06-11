package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class FieldActionTest {
    private GameController gameController;

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

}
