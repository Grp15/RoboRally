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

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.dal.IRepository;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import dk.dtu.compute.se.pisd.roborally.dal.RepositoryAccess;
import dk.dtu.compute.se.pisd.roborally.dal.GameInDB;

/**
 * AppController controls the app and is responsible to create a game controller
 *
 *
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");
    private List<GameInDB> Game_IDs = new ArrayList<GameInDB>();
    private List<Integer> GAME_int_IDs = new ArrayList<Integer>();

    //Name of the board according to json file
    private String boardname = "highway";


    final private RoboRally roboRally;

    private GameController gameController;

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }


    /**
     * newGame creates a new instance of a game controller
     */

    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            Board board = LoadBoard.loadBoard(boardname); //
            gameController = new GameController(board);
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    /**
     * Savaegame saves a game to be played later
     */

    public void saveGame() {
        Board board = gameController.getBoard();


        RepositoryAccess rep = new RepositoryAccess();
        rep.getRepository();


        if(board.getGameId() == null) {
            rep.getRepository().createGameInDB(board);
        }

        else if(board.getGameId() != null){
            rep.getRepository().updateGameInDB(board);
        }
        else{
            //Something defensive / error handling
            //This shouldn't be possible
        }


    }

    /**
     * LoadGame loads a saved game
     */

    public void loadGame() {

        //TODO: Skal ændres når man gemmer spilnavne til at vise navne i stedet for id

        RepositoryAccess rep = new RepositoryAccess();

        Game_IDs = rep.getRepository().getGames();


        //This forloop gets the IDs as int

        for (int i = 0; i <= Game_IDs.size()-1; i++) {
            GAME_int_IDs.add(Game_IDs.get(i).getId());
            //System.out.println(GAME_int_IDs.get())
        }

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(Game_IDs.get(0).getId(), GAME_int_IDs);
        dialog.setTitle("Load game");
        dialog.setHeaderText("Select board id to load");
        Optional<Integer> result = dialog.showAndWait();



        if(result.isPresent()) {

            if (gameController == null) {

                gameController = new GameController(rep.getRepository().loadGameFromDB(result.get()));

                //gameController.startProgrammingPhase();

                roboRally.createBoardView(gameController);

            } else {
                rep.getRepository().loadGameFromDB(result.get());
            }
        }

    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    /**
     * Exits the game and gives the player an opportunity to save
     */

    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

    public GameController getGameController(){
        return gameController;
    }

}