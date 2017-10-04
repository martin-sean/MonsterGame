/* For now we will stick with simple coloured cells to display the board
 * until I learn how to use sprites instead. - Sean */

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UIBoard extends Pane
{
	private int board_width;
	private int board_height;
	private GridPane board;
	
	public UIBoard()
	{
		this.board_width = 11;
		this.board_height = 11;
		initialize_board();
	}
	
	public void initialize_board()
	{
		board = new GridPane();
		
		/* Fills board with Empty/Path Cells */
		for (int i = 0; i < board_width; i++) 
		{
			for (int j = 0; j < board_height; j++)
			{
				/* I know this is disgusting. It's draws the grey path players 
				 * can move on. Bernie did this by using a hard-coded string of
				 * the board, maybe that wasn't so bad after all... */
				
				/* Adds blank cell*/
				if (((i == 1 || i == 5 || i == 9) && j > 0 && j < 10) ||
				((j == 1 || j == 5 || j == 9) && i > 0 && i < 10))
				{
					board.add(new ImageView(new Image("dirt.png", 45, 45, false, false)), i, j);
				}
				
				/* Adds path cell*/
				else
				{
					board.add(new ImageView(new Image("cobble.png", 45, 45, false, false)), i, j);
				}
			}
		}
		
//		/* Test code adding image to index 0,0. */
//		Image image = new Image("monster.png");
//		ImageView imageview = new ImageView(image);
//		board.add(imageview, 0, 0);
		
		Players players;
		players = GameState.get_instance().get_players();
		
		for (int i = 0; i < players.get_player_count(); i++)
		{
			Entity player = players.get_player(i);
			if (player instanceof Monster)
				board.add(new UICell(3), player.get_pos_x(), player.get_pos_y());
			else
				board.add(new UICell(2), player.get_pos_x(), player.get_pos_y());
		}

//		/* Adds Players */
//		board.add(new UICell(2), 1, 1);
//		board.add(new UICell(2), 1, 9);
//		board.add(new UICell(2), 9, 1);
//		board.add(new UICell(2), 9, 9);
//		
//		/* Adds Monster */
//		board.add(new UICell(3), 5, 5);
		
		/* Adds Portals*/
		board.add(new ImageView(new Image("triangle_left.png", 45, 45, false, false)), 0, 5);
		board.add(new ImageView(new Image("triangle_up.png", 45, 45, false, false)), 5, 0);
		board.add(new ImageView(new Image("triangle_right.png", 45, 45, false, false)), 10, 5);
		board.add(new ImageView(new Image("triangle_down.png", 45, 45, false, false)), 5, 10);
	}
	
	public GridPane getBoard()
	{
		return board;
	}
}
