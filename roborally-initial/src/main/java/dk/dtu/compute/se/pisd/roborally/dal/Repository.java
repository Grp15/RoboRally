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
package dk.dtu.compute.se.pisd.roborally.dal;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.Command;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class implementing the interface IRepository. This class contains the attributes which constitutes the game state at all times
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
class Repository implements IRepository {
	
	private static final String GAME_GAMEID = "gameID";

	private static final String GAME_NAME = "name";
	
	private static final String GAME_CURRENTPLAYER = "currentPlayer";

	private static final String GAME_PHASE = "phase";

	private static final String GAME_STEP = "step";

	private static final String BOARD_NAME = "boardName";
	
	private static final String PLAYER_PLAYERID = "playerID";
	
	private static final String PLAYER_NAME = "name";

	private static final String PLAYER_COLOUR = "colour";
	
	private static final String PLAYER_GAMEID = "gameID";
	
	private static final String PLAYER_POSITION_X = "positionX";

	private static final String PLAYER_POSITION_Y = "positionY";

	private static final String PLAYER_HEADING = "heading";

	private static final String PLAYER_CARDS_REGISTER = "cardRegister";

	private static final String PLAYER_CARDS_PROGRAM = "cardProgram";


	private Connector connector;
	
	Repository(Connector connector){
		this.connector = connector;
	}

	/**
	 * Method which creates an instance of game in the database
	 *
	 * @param game instance of Board class
	 * @return true or false whether action succeeded or not
	 *
	 */
	@Override
	public boolean createGameInDB(Board game) {
		if (game.getGameId() == null) {
			Connection connection = connector.getConnection();
			try {
				connection.setAutoCommit(false);

				/*Note: PreparedStatement is used for setting something
				* ResultSet is used for getting/updating something*/

				PreparedStatement ps = getInsertGameStatementRGK();
				// TODO: the name should eventually set by the user
				//       for the game and should be then used 
				//       game.getName
				//ps.setString(1, "Name: " + game.setGameId(game.getGameId()) + "");
				ps.setString(1, "Date: " +  new Date()); // instead of name
				ps.setNull(2, Types.TINYINT); // game.getPlayerNumber(game.getCurrentPlayer())); is inserted after players!
				ps.setInt(3, game.getPhase().ordinal());
				ps.setInt(4, game.getStep());
				ps.setString(5, game.getBoardName());

				// If you have a foreign key constraint for current players,
				// the check would need to be temporarily disabled, since
				// MySQL does not have a per transaction validation, but
				// validates on a per row basis.
				// Statement statement = connection.createStatement();
				// statement.execute("SET foreign_key_checks = 0");
				
				int affectedRows = ps.executeUpdate();
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (affectedRows == 1 && generatedKeys.next()) {
					game.setGameId(generatedKeys.getInt(1));
				}
				generatedKeys.close();
				
				// Enable foreign key constraint check again:
				// statement.execute("SET foreign_key_checks = 1");
				// statement.close();

				createPlayersInDB(game);
				// TODO this method needs to be implemented first
				//createCardFieldsInDB(game);


				// since current player is a foreign key, it can oly be
				// inserted after the players are created, since MySQL does
				// not have a per transaction validation, but validates on
				// a per row basis.
				ps = getSelectGameStatementU();
				ps.setInt(1, game.getGameId());

				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
					rs.updateRow();
				} else {
					// TODO error handling
					//return false; //Also an idea

				}
				rs.close();

				connection.commit();
				connection.setAutoCommit(true);
				return true;
			} catch (SQLException e) {
				// TODO error handling
				//connection.setAutoCommit(false); //An idea
				e.printStackTrace();
				System.err.println("Some DB error");
				
				try {
					connection.rollback();
					connection.setAutoCommit(true);
				} catch (SQLException e1) {
					// TODO error handling
					e1.printStackTrace();
				}
			}
		} else {
			System.err.println("Game cannot be created in DB, since it has a game id already!");
		}
		return false;
	}


	/**
	 * Method which updates state of game saved in database
	 *
	 * @param game instance of Board class
	 * @return true or false depending on the outcome of the actions success
	 *
	 */
	@Override
	public boolean updateGameInDB(Board game) {
		assert game.getGameId() != null;
		
		Connection connection = connector.getConnection();
		try {
			connection.setAutoCommit(false);

			PreparedStatement ps = getSelectGameStatementU();
			ps.setInt(1, game.getGameId());
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
				rs.updateInt(GAME_PHASE, game.getPhase().ordinal());
				rs.updateInt(GAME_STEP, game.getStep());
				rs.updateString(BOARD_NAME, game.getBoardName());
				rs.updateRow();
			} else {
				// TODO error handling
			}
			rs.close();

			updatePlayersInDB(game);
			/* TOODO this method needs to be implemented first
			updateCardFieldsInDB(game);
			*/

            connection.commit();
            connection.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			// TODO error handling
			e.printStackTrace();
			System.err.println("Some DB error");
			
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO error handling
				e1.printStackTrace();
			}
		}

		return false;
	}


	/**
	 * Method to load a saved game from the database
	 *
	 * @param id
	 * @return game id of game session to be loaded
	 *
	 */
	@Override
	public Board loadGameFromDB(int id) {
		Board game;
		try {
			// TODO here, we could actually use a simpler statement
			//      which is not updatable, but reuse the one from
			//      above for the pupose
			PreparedStatement ps = getSelectGameStatementU();
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			int playerNo = -1;
			if (rs.next()) {
				// TODO the width and height could eventually come from the database
				// int width = AppController.BOARD_WIDTH;
				// int height = AppController.BOARD_HEIGHT;
				// game = new Board(width,height);
				// TODO and we should also store the used game board in the database
				//      for now, we use the default game board
				game = LoadBoard.loadBoard(rs.getString(BOARD_NAME));
				if (game == null) {
					return null;
				}
				playerNo = rs.getInt(GAME_CURRENTPLAYER);
				// TODO currently we do not set the games name (needs to be added)
				game.setPhase(Phase.values()[rs.getInt(GAME_PHASE)]);
				game.setStep(rs.getInt(GAME_STEP));
				game.setBoardName(rs.getString(BOARD_NAME));
			} else {
				// TODO error handling
				return null;
			}
			rs.close();

			game.setGameId(id);			
			loadPlayersFromDB(game);

			if (playerNo >= 0 && playerNo < game.getPlayersNumber()) {
				game.setCurrentPlayer(game.getPlayer(playerNo));
			} else {
				// TODO  error handling
				return null;
			}

			/* TOODO this method needs to be implemented first
			loadCardFieldsFromDB(game);
			*/

			return game;
		} catch (SQLException e) {
			// TODO error handling
			e.printStackTrace();
			System.err.println("Some DB error");
		}
		return null;
	}


	/**
	 * Array list of games saved in the database
	 *
	 * @return list of games saved in database
	 *
	 */
	@Override
	public List<GameInDB> getGames() {
		// TODO when there many games in the DB, fetching all available games
		//      from the DB is a bit extreme; eventually there should a
		//      methods that can filter the returned games in order to
		//      reduce the number of the returned games.
		List<GameInDB> result = new ArrayList<>();
		try {
			PreparedStatement ps = getSelectGameIdsStatement();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(GAME_GAMEID);
				String name = rs.getString(GAME_NAME);
				result.add(new GameInDB(id,name));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO proper error handling
			e.printStackTrace();
		}
		return result;		
	}


	private void createPlayersInDB(Board game) throws SQLException {
		// TODO code should be more defensive
		PreparedStatement ps = getSelectPlayersStatementU();
		ps.setInt(1, game.getGameId());
		
		ResultSet rs = ps.executeQuery();
		for (int i = 0; i < game.getPlayersNumber(); i++) {
			Player player = game.getPlayer(i);
			rs.moveToInsertRow();
			rs.updateInt(PLAYER_GAMEID, game.getGameId());
			rs.updateInt(PLAYER_PLAYERID, i);
			rs.updateString(PLAYER_NAME, player.getName());
			rs.updateString(PLAYER_COLOUR, player.getColor());
			rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
			rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
			rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
			rs.updateString(PLAYER_CARDS_REGISTER, player.getProgram());
			rs.updateString(PLAYER_CARDS_PROGRAM, player.getCards());
			rs.insertRow();
		}

		rs.close();
	}
	
	private void loadPlayersFromDB(Board game) throws SQLException {
		PreparedStatement ps = getSelectPlayersASCStatement();
		ps.setInt(1, game.getGameId());
		
		ResultSet rs = ps.executeQuery();
		int i = 0;
		while (rs.next()) {
			int playerId = rs.getInt(PLAYER_PLAYERID);
			if (i++ == playerId) {
				// TODO this should be more defensive
				String name = rs.getString(PLAYER_NAME);
				String colour = rs.getString(PLAYER_COLOUR);
				Player player = new Player(game, colour ,name);
				game.addPlayer(player);
				
				int x = rs.getInt(PLAYER_POSITION_X);
				int y = rs.getInt(PLAYER_POSITION_Y);
				player.setSpace(game.getSpace(x,y));
				int heading = rs.getInt(PLAYER_HEADING);
				player.setHeading(Heading.values()[heading]);

				//Indlæser register kort fra DB
				String load_register = rs.getString(PLAYER_CARDS_REGISTER);
				CommandCard[] cardsregister = LoadCommandCardsFromDisplayName(player.splitCardsRegisterString(load_register));

				//Indlæser programmeringskort fra DB
				String load_program = rs.getString(PLAYER_CARDS_PROGRAM);
				CommandCard[] cards = LoadCommandCardsFromDisplayName(player.splitCardsProgramString(load_program));


				for(int z = 0; z < cards.length - 1; z++){

					if(z < cardsregister.length - 1){
						player.getProgramField(z).setCard(cardsregister[z]);
					}
					player.getCardField(z).setCard(cards[z]);
				}



				// TODO  should also load players program and hand here
			} else {
				// TODO error handling
				System.err.println("Game in DB does not have a player with id " + i +"!");
			}
		}
		rs.close();
	}
	
	private void updatePlayersInDB(Board game) throws SQLException {
		PreparedStatement ps = getSelectPlayersStatementU();
		ps.setInt(1, game.getGameId());
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int playerId = rs.getInt(PLAYER_PLAYERID);
			// TODO should be more defensive
			Player player = game.getPlayer(playerId);
			// rs.updateString(PLAYER_NAME, player.getName()); // not needed: player's names does not change
			rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
			rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
			rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
			rs.updateString(PLAYER_CARDS_REGISTER, player.getProgram());
			rs.updateString(PLAYER_CARDS_PROGRAM, player.getCards());

			// TODO error handling
			// TODO take care of case when number of players changes, etc
			rs.updateRow();
		}
		rs.close();
		
		// TODO error handling/consistency check: check whether all players were updated
	}

	private static final String SQL_INSERT_GAME =
			"INSERT INTO Game(name, currentPlayer, phase, step, boardName) VALUES (?, ?, ?, ?, ?)";

	private PreparedStatement insert_game_stmt = null;

	private PreparedStatement getInsertGameStatementRGK() {
		if (insert_game_stmt == null) {
			Connection connection = connector.getConnection();
			try {
				insert_game_stmt = connection.prepareStatement(
						SQL_INSERT_GAME,
						Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				// TODO error handling
				e.printStackTrace();
			}
		}
		return insert_game_stmt;
	}

	private static final String SQL_SELECT_GAME =
			"SELECT * FROM Game WHERE gameID = ?";
	
	private PreparedStatement select_game_stmt = null;
	
	private PreparedStatement getSelectGameStatementU() {
		if (select_game_stmt == null) {
			Connection connection = connector.getConnection();
			try {
				select_game_stmt = connection.prepareStatement(
						SQL_SELECT_GAME,
						ResultSet.TYPE_FORWARD_ONLY,
					    ResultSet.CONCUR_UPDATABLE);
			} catch (SQLException e) {
				// TODO error handling
				e.printStackTrace();
			}
		}
		return select_game_stmt;
	}
		
	private static final String SQL_SELECT_PLAYERS =
			"SELECT * FROM Player WHERE gameID = ?";

	private PreparedStatement select_players_stmt = null;

	private PreparedStatement getSelectPlayersStatementU() {
		if (select_players_stmt == null) {
			Connection connection = connector.getConnection();
			try {
				select_players_stmt = connection.prepareStatement(
						SQL_SELECT_PLAYERS,
						ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_UPDATABLE);
			} catch (SQLException e) {
				// TODO error handling
				e.printStackTrace();
			}
		}
		return select_players_stmt;
	}


/*
	private static final String SQL_SELECT_CARDS =
			"SELECT * FROM Player WHERE gameID = ?"; // Todo: Sikkert her det skal ændres til hvad vi har i vores table

	private PreparedStatement select_cards_stmt = null;

	private PreparedStatement select_cards_stmt(){
		if (select_cards_stmt == null){
			Connection connection = connector.getConnection();

			try{
			select_cards_stmt = connection.prepareStatement(
					SQL_SELECT_CARDS,
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		return select_cards_stmt;
	}

 */

	private static final String SQL_SELECT_PLAYERS_ASC =
			"SELECT * FROM Player WHERE gameID = ? ORDER BY playerID ASC";
	
	private PreparedStatement select_players_asc_stmt = null;
	
	private PreparedStatement getSelectPlayersASCStatement() {
		if (select_players_asc_stmt == null) {
			Connection connection = connector.getConnection();
			try {
				// This statement does not need to be updatable
				select_players_asc_stmt = connection.prepareStatement(
						SQL_SELECT_PLAYERS_ASC);
			} catch (SQLException e) {
				// TODO error handling
				e.printStackTrace();
			}
		}
		return select_players_asc_stmt;
	}
	
	private static final String SQL_SELECT_GAMES =
			"SELECT gameID, name FROM Game";
	
	private PreparedStatement select_games_stmt = null;
	
	private PreparedStatement getSelectGameIdsStatement() {
		if (select_games_stmt == null) {
			Connection connection = connector.getConnection();
			try {
				select_games_stmt = connection.prepareStatement(
						SQL_SELECT_GAMES);
			} catch (SQLException e) {
				// TODO error handling
				e.printStackTrace();
			}
		}
		return select_games_stmt;
	}


	// TODO: Needs to find the right card by itself

	private CommandCard[] LoadCommandCardsFromDisplayName(String[] Commands){
		Command[] commands = Command.values();
		CommandCard[] register;
		register = new CommandCard[Commands.length];


		for(int i = 0; i < Commands.length - 1 ; i++) {

			switch (Commands[i]) {

				case "Spam":
					register[i] = new CommandCard(commands[0]);
					break;

				case "Trojansk Hest" :
					register[i] = new CommandCard(commands[1]);
					break;

				case "Virus" :
					register[i] = new CommandCard(commands[2]);
					break;

				case "Again":
					register[i] = new CommandCard(commands[3]);
					break;

				case "Fwd":
					register[i] = new CommandCard(commands[4]);
					break;

				case "2 Fwd":
					register[i] = new CommandCard(commands[5]);
					break;

				case "3 Fwd":
					register[i] = new CommandCard(commands[6]);
					break;

				case "Back Up" :
					register[i] = new CommandCard(commands[7]);
					break;

				case "Turn Right":
					register[i] = new CommandCard(commands[8]);
					break;

				case "Turn Left":
					register[i] = new CommandCard(commands[9]);
					break;

				case "U-turn":
					register[i] = new CommandCard(commands[10]);
					break;

				case "Power Up" :
					register[i] = new CommandCard(commands[11]);
					break;

				case "Energy":
					register[i] = new CommandCard(commands[12]);
					break;

				case "Repeat" :
					register[i] = new CommandCard(commands[13]);
					break;

				case "Sandbox":
					register[i] = new CommandCard(commands[14]);
					break;

				case "Speed":
					register[i] = new CommandCard(commands[15]);
					break;

				case "Left or Right or U-turn":
					register[i] = new CommandCard(commands[16]);
					break;

				default:
					register[i] = null;
					break;

			}
		}
		return register;
	}



}
