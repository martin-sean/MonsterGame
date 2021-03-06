import java.io.IOException;
import java.net.Socket;

public class ClientConnHandler extends ConnHandler
{

	public ClientConnHandler(Socket conn, int id) throws IOException 
	{
		super(conn, id);
	}

	@Override
	public void conn_work(Board board, Players players) throws InterruptedException, IOException 
	{
		GameState game_state = GameState.get_instance();
		
		
		// Send game info to client
		board.set_dimensions(Integer.valueOf(get_string())); // Board dimensions
		System.out.println("Set board dimensions to "+board.get_dimensions());
		board.load_layout(get_string()); // Board layouts
		System.out.println("Loaded gameboard");
		String[] id_r = get_string().split(":");
		game_state.set_random_number(Math.abs(Integer.valueOf(id_r[1])));
		this.id = Integer.valueOf(id_r[0]); // The client's ID
		

		
		System.out.println("Got client's ID");
		game_state.players.set_pc_id(this.id);

		
		// TODO: Client interaction and player setup
		
		if (this.id == 0) // If we're the first player to connect
		{
			System.out.println("We're the first user");
			// Wait for the user to set the player target
			while (players.get_player_target() == -1)
			{
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					System.out.println("There was an interupt while waiting for player target.");
					System.out.println(e.getMessage());
				}
			}
			System.out.println("Sending server player target");
			
			send_string(String.valueOf(players.get_player_target()));
		}
		else
		{
			// Otherwise get the player target
			players.set_player_target(Integer.valueOf(get_string()));
		}
		System.out.println("Got a new player target and set the player target to "+players.get_player_target());
		players.create_players(); 
	
		
		// Get this player's object
		Entity player = players.get_player(this.id);
			
		while (player.get_name() == "")
			Thread.sleep(100);
		
		
		send_string(player.get_name());
		System.out.println("Just sent name to server");

		// Wait for list of avaliable spots then set it in gamestate
		String spots = get_string();
		game_state.set_avaliable_spots(spots);
		System.out.println("Got list of avaliable spots from server: "+spots);
		
		
		// Send back the selected spot
		while (players.get_starter_spot() == "")
		{
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				System.out.println("There was an interupt while waiting for starting spot.");
				System.out.println(e.getMessage());
			}
		}
		
		send_string(players.get_starter_spot());
		System.out.println("Picked starter spot and sent off selection");

		if (players.get_player_target() == this.id+1)
		{
			System.out.println("Last player just joined, let's go!");
			game_state.change_run_state(true);
		}
		else
			;
		
		
		
		int alive;
		while (game_state.is_running())
		{
			// Send our direction
			send_string(Integer.toString(players.get_player(players.get_pc_id()).get_ddir()));
			
			
			String rawc = get_string();
			
			players.lock();
			
			System.out.print("Sent direction");
			if (rawc.contains("WINRAR"))
			{
				System.out.println("WE WIN!!!");
				game_state.win(true);
				players.unlock();
				break;
			}
			
			
			String[] coords = rawc.split(":"); // TODO: Get player x and ys from server
			System.out.print(":Got coords string: "+rawc);
			players.set_player_count(coords.length);
			System.out.print(":Player count: "+coords.length);
			
			alive = coords.length-1; // minus one for monster
			for (int i = 0; i < coords.length; i++)
			{
				String[] xy = coords[i].split(",");
				
				if (xy[3].contains("D"))
				{
					System.out.print(":Player is dead");
					
					if (i == this.id)
					{
						System.out.println("This client has died");
						System.exit(0);
					}
					
					((Player)players.get_player(id)).kill();
					alive--;
					
					players.get_player(i).set_id("D");// TODO: TIDY
					continue; // Don't deal with the dead
				}
				
				
				if (Integer.valueOf(xy[3]) == players.get_player_target()) // We're dealing with the monster
				{
					Entity monster = players.get_player(players.get_player_target());
					monster.set_pos_x(Integer.valueOf(xy[0]));
					monster.set_pos_y(Integer.valueOf(xy[1]));
					monster.set_id(xy[3]);
					System.out.print(":Set monster position");
				}
				else
				{
					Player cplayer = (Player) players.get_player(i);
					cplayer.set_pos_x(Integer.valueOf(xy[0]));
					cplayer.set_pos_y(Integer.valueOf(xy[1]));
					cplayer.set_id(xy[3]);
					cplayer.set_dir(Integer.valueOf(xy[2]));
					System.out.print(":Set player pos and dir");
				}
			}
			System.out.print(":Alive players: "+alive);
			players.set_alive_players(alive);
			players.unlock();
			
			game_state.change_run_state(true); // TODO: Find way to run once
		
			Thread.sleep(10);
			System.out.print("\n");
			System.out.flush();

		}
		this.conn.close();
	}

}
