/**
 * This class manages the MouseCatElephant game state.
 * 
 * @author	Adam Warner
 * @version 7/18/2015
 *
 */

import java.io.IOException;

public class MouseCatElephantState implements ModelListener 
{
	private ModelListener modelListener;
	private int myID, theirChoice;

	/**
	 * Constructor for the MouseCatElephant game state object.
	 */
	public MouseCatElephantState(){}

	/**
	 * Sets the model listener for this object.
	 * 
	 * @param ml		The ModelListener object
	 */
	public void setModelListener(ModelListener ml) 
	{
		modelListener = ml;
	}
	
	/**
	 * Sets player information.
	 * 
	 * @param  id				The ID of the player
	 * @param  name				The name of the player
	 * @throws IOException 		Thrown if an I/O error occurred
	 */
	public void setPlayerInfo(int id, String name) throws IOException
	{
		if (name == null)
		{
			myID = id;
		} else
		{
			if (id == myID)
			{
				modelListener.setPlayerInfo(0, name);
			} else
			{
				modelListener.setPlayerInfo(1, name);
			}
		}
	}
	
	/**
	 * Handle scoring information.
	 * 
	 * @param id				The player ID
	 * @param score				The score for that player
	 * @throws IOException 		Thrown if an I/O error occurred
	 */
	public void reportScore(int id, int score) throws IOException
	{
		if (id == myID)
		{
			modelListener.reportScore(0, score);
		} else
		{
			modelListener.reportScore(1, score);
		}
	}
		
	/**
	 * Reports the outcome of the round.
	 * 
	 * @param a1				The first animal
	 * @param vs				The action taken by the first animal
	 * @param a2				The second animal
	 * @throws IOException 		Thrown if an I/O error occurred
	 */
	public void reportOutcome(int a1, int vs, int a2) throws IOException
	{
		modelListener.reportOutcome(a1, vs, a2);
		modelListener.animalSelected(1, theirChoice);
		
	}

	/**
	 * Handle the opposing players animal choice.
	 * 
	 * @param id				The player ID
	 * @param animal			The code for the animal selected (0 mouse, 1 cat, 2 elephant)
	 * @throws IOException 		Thrown if an I/O error occurred
	 */
	public void animalSelected(int id, int animal) throws IOException
	{
		if (id != myID)
		{
			modelListener.animalSelected(1, 3);
			theirChoice = animal;
		} else
			modelListener.animalSelected(0, animal);
	}

	/**
	 * Initializes a new round.
	 * @throws IOException 		Thrown if an I/O error occurred
	 */
	public void newRoundSelected() throws IOException
	{	
		modelListener.newRoundSelected();
	}

	
}