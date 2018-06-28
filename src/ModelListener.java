/**
 * Specifies the interface for an object that is triggered by events from the model object
 * in the MouseCatElephant game. 
 * 
 * @author	Adam Warner
 * @version 7/18/2015
 *
 */

import java.io.IOException;

public interface ModelListener 
{

	/**
	 * Report that an animal was selected by a player.
	 * 
	 * @param id		The ID of the player making the selection
	 * @param animal	The animal selected
	 */
	public void animalSelected(int id, int animal) throws IOException;
	
	/**
	 * Report that a new round was selected.
	 */
	public void newRoundSelected() throws IOException;

	/**
	 * Report the outcome of a round.
	 * 
	 * @param a1	The first animal in the outcome result
	 * @param vs	The first
	 * @param a2	The second animal in the outcome result
	 */
	public void reportOutcome(int a1, int vs, int a2) throws IOException;
	
	/**
	 * Report the score for a player.
	 * 
	 * @param id		The ID of the player
	 * @param score		The score for that player
	 */
	public void reportScore(int id, int score) throws IOException;
	
	/**
	 * Assigns player information
	 * 
	 * @param id		The ID of the player
	 * @param name		The name of the player
	 */
	public void setPlayerInfo(int id, String name) throws IOException;
}
