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

import dk.dtu.compute.se.pisd.roborally.model.Board;

import java.util.List;

/**
 * Interface class to be used with the database. Using this interface it is possible to save and load games in the database
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author S164539
 * @author S154780
 * @author S205472
 * @author S194612
 *
 */
public interface IRepository {
	
 	boolean createGameInDB(Board game);
	
	boolean updateGameInDB(Board game);
	
	Board loadGameFromDB(int id);
	
	List<GameInDB> getGames();

}
