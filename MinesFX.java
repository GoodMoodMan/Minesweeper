package mines;
// imports
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//MinesFX class representing our graphic interface, extending application
public class MinesFX extends Application{
	// default main method
	public static void main(String args[]) {
		launch(args);
	}

	// overridden start method, building and showing our graphic interface
	@Override
	public void start(Stage stage) throws Exception {
		// controller and root hbox
		Controller controller;
		HBox hbox;
		// try to load fxml file, load controller and hbox
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("mine.fxml"));
			hbox = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// get empty stackpane from controller
		StackPane pane = controller.getG1();
		// create game grid of size 10 by default, 10 mines
		MinesFX.create_grid(pane, 10, 10,10);
		//set the scene and show
		Scene scene=new Scene(hbox);
		stage.setScene(scene);
		
		stage.setTitle("Best Mine Sweeper");
		stage.show();
		
	}
	// create grid static method works with the reset button event in controller
	// fills the empty stackpane with the game grid based on input values in text fields
	public static void create_grid(StackPane pane,int height,int width,int nummines) {
		// new Mines based on input data
		Mines game = new Mines(height,width,nummines);
		// Button2 (created class holding i,j values extending button) matrix based on game size
		Button2 map[][] = new Button2[height][width];
		// remove current grid if existing (new game)
		pane.getChildren().removeAll(pane.getChildren());
		GridPane grid = new GridPane();
		// button2 adding loop to the grid
		for (int i=0;i<height;i++) {
			// adds column and row constraints
			ColumnConstraints col1 = new ColumnConstraints();
			RowConstraints row1 = new RowConstraints();
			// sets size to max possible
		    col1.setPercentWidth(100);
		    row1.setPercentHeight(100);
			for (int j=0;j<width;j++) {	
				// create a new button2 with current i,j
				// sets size, text (based on get method) and mouse event
				Button2 b = new Button2(i, j);
				b.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				b.setText(game.get(i, j));
				b.setOnMouseClicked(event-> {
					// if primary mouse button was clicked (left click)
					if (event.getButton()==MouseButton.PRIMARY) {
						// call the play method with current game,map and button2
						play(game, map,b);  
			        }
	
					// if secondary mouse button was clicked (right click)
			        if (event.getButton()==MouseButton.SECONDARY) {
			        	// calls toggle flag method on current button location
			            game.toggleFlag(b.i, b.j);  
			            b.setText(game.get(b.i, b.j));
			            if (game.isDone()) {
			    			Alert alert = new Alert(AlertType.INFORMATION, "You Won!");
			    			alert.show();
			    		}
			        }
					      
				});
				// add button to game map and grid
				map[i][j]=b;
				grid.add(b, i, j);	
			}
			// add constraints
			grid.getColumnConstraints().add(col1);
			grid.getRowConstraints().add(row1);
		}
		// set padding and add grid to empty stackpane
		grid.setPadding(new Insets(10, 10, 10, 10)); 
		pane.getChildren().add(grid);
	}
	// play method calls the open method on clicked button
	// refreshes the texts of all buttons according to played location
	private static void play (Mines game,Button2 map[][],Button2 b) {
		// if open returned false and location has a mine
		if (!game.open(b.i, b.j)&&game.map[b.i][b.j].mine) {
			// sets show all
			game.setShowAll(true);
		}
		// refresh texts of all buttons on grid based on game change
		for (int i=0;i<map.length;i++) {
			for (int j=0;j<map[0].length;j++) {
				map[i][j].setText(game.get(i, j));				
			}
		}
		// if isDone returns true, game is finished and message alert appears
		if (game.isDone()) {
			Alert alert = new Alert(AlertType.INFORMATION, "You Won!");
			alert.show();
		}
	}
	// static class Button2 extends from button and holds i,j coordiantes
	static class Button2 extends Button {
		private int i,j;
		public Button2(int i,int j) {
			super();
			this.i=i;
			this.j=j;
		}
	}
	

}
