/**
 * Class MouseCatElephant is the main program for the MouseCatElephant project.
 * The command line arguments specify the host and port for the server, and
 * the name of the player.
 * 
 * @author Adam Warner
 * @version 7/18/2015
 */

import java.net.InetSocketAddress;
import java.net.Socket;

public class MouseCatElephant
{

	/**
	 * Main program.
	 */
	public static void main(String[] args) throws Exception
	{
		if (args.length != 3) usage();
		String host = args[0];
		String name = args[2];
		int port = 0;
		
		try 
		{
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		Socket socket = new Socket();
		socket.connect (new InetSocketAddress(host, port));
		
		MouseCatElephantState model = new MouseCatElephantState();
		MouseCatElephantUI view = MouseCatElephantUI.create(name);

		ModelProxy proxy = new ModelProxy(socket);
		model.setModelListener(view);
		view.setViewListener(proxy);
		proxy.setModelListener(model);
		
		proxy.joinServer(name);
	}

	/**
	 * Display a usage message and exit the program.
	 */
	private static void usage()
	{
		System.err.println("Usage: $ java MouseCatElephant <host> <port> <playername>");
		System.exit(1);
	}
}