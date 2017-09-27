/* For now we will stick with simple coloured cells to display the board
 * until I learn how to use sprites instead. - Sean */

import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.scene.control.*;

public class UICell extends Pane
{
	/* Player number currently not working as intended
	private int player_number = 1;
	*/
	
	/* TYPES:
	 * BLANK = 0
	 * PLAYER = 1
	 * MONSTER = 2
	 * PORTAL = 3
	 */
	public UICell(int type)
	{
		this.setPrefSize(2000, 2000);
		init_cell(type);
		
		/*if (type == 2)
		{
			player_number++;
			Label number = new Label(Integer.toString(player_number));
			number.setFont(Font.font("Arial", FontWeight.BOLD, 20));
			this.getChildren().add(number);
		}*/
	}
	
	public void init_cell(int type)
	{
		switch (type)
		{
			/* BLANK */
			case 0:
				setStyle("-fx-background-color: white;");
				break;
			/* PATH */
			case 1:
				setStyle("-fx-background-color: lightgrey;");
				break;
			/* PLAYER */
			case 2:
				setStyle("-fx-background-color: yellow;");
				break;
			/* MONSTER */
			case 3:
				setStyle("-fx-background-color: red;");
				break;
			/* PORTAL */
			case 4:
				setStyle("-fx-background-color: purple;");
		}
	}
}
