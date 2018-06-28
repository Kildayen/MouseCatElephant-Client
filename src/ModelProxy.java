/**
 * Defines the network proxy for the model object in the MouseCatElephant project.
 * The proxy resides in the client program and communicates with the server.
 * 
 * @author	Adam Warner
 * @version 7/18/2015
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class ModelProxy implements ViewListener
{
	private Socket socket;
	private OutputStreamWriter out;
	private BufferedReader in;
	private ModelListener model;

	/**
	 * Construct a new model proxy.
	 *
	 * @param  		socket  		The socket
	 * @exception 	IOException		Thrown if an I/O error occurred
	 */
	 public ModelProxy(Socket socket) throws IOException
	 {
		 this.socket = socket;
		 out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
		 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	 }

	/**
	* Set the model listener object for this model proxy
	*
	* @param  modelListener  Model listener.
	*/
	public void setModelListener(MouseCatElephantState model)
	{
		this.model = model;
		new ReaderThread().start();
	}

	/**
	 * Sends a join message to the server.
	 * 
	 * @param	name			The name of the player joining the server
	 * @throws	IOException 	Thrown if an I/O error occurred
	 */
	public void joinServer(String name) throws IOException
	{
		out.write("join " + name + "\n");
		out.flush();
	}
	
	/**
	 * Sends an animal selected message to the server.
	 * 
	 * @param		a				The code for the animal selected (0 mouse, 1 cat, 2 elephant)
	 * @exception	IOException 	Thrown if an I/O error occurred
	 */
	public void selectAnimal(int a) throws IOException
	{
		out.write("choose " + a + "\n");
		out.flush();
	}

	/**
	 * Sends a new round selected message to the server.
	 * 
	 * @exception	IOException 	Thrown if an I/O error occurred
	 */
	public void newRound() throws IOException
	{
		out.write("new\n");
		out.flush();
	}
	
	/**
	 * Sends a quit message to the server and closes the program.
	 * 
	 * @param	i					The exit code.
	 * @throws 	IOException			Thrown if an I/O error occurred
	 */
	public void quit(int i) throws IOException
	{
		if (i != 0)
			System.err.println ("Invalid Message");
		
		out.write("quit\n");
		out.flush();
		socket.close();
		System.exit(i);
	}
	
	/**
	* ReaderThread is a helper class that receives and processes messages from the network.
	*/
	private class ReaderThread extends Thread
	{
		public void run()
		{
			try
			{
				for (;;)
				{
					int [] msg = new int[3];
					String name;
					
					String str = in.readLine();
					StringTokenizer tokens = new StringTokenizer(str);
					
					if (tokens.countTokens() < 1 || tokens.countTokens() > 4)
					{
						quit(1);
					}
					
					switch (tokens.nextToken())
					{
						case "id":
							msg[0] = Integer.parseInt(tokens.nextToken());	
							model.setPlayerInfo(msg[0], null);
							break;
						case "name":
							msg[0] = Integer.parseInt(tokens.nextToken());
							name = tokens.nextToken();
							model.setPlayerInfo(msg[0], name);
							break;
						case "score":
							msg[0] = Integer.parseInt(tokens.nextToken());
							msg[1] = Integer.parseInt(tokens.nextToken());
							model.reportScore(msg[0], msg[1]);
							break;
						case "choice":
							msg[0] = Integer.parseInt(tokens.nextToken());
							msg[1] = Integer.parseInt(tokens.nextToken());
							model.animalSelected(msg[0], msg[1]);
							break;
						case "outcome": 
							msg[0] = Integer.parseInt(tokens.nextToken());
							msg[1] = Integer.parseInt(tokens.nextToken());
							msg[2] = Integer.parseInt(tokens.nextToken());
							model.reportOutcome(msg[0], msg[1], msg[2]);
							break;
						case "new":
							model.newRoundSelected();
							break;
						case "quit":
							quit(0);
							break;
						default:
							quit(1);
							break;
					}
				}
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
			catch (IOException e){}
			finally
			{
				try
				{
					socket.close();
				}
				catch (IOException e){}
			}
		}
	}
}