package mines;
// imports
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
//FXML class representing our controller
public class Controller {

	// private FXML node id's
    @FXML
    private Button B1;

    @FXML
    private TextField F1;

    @FXML
    private TextField F2;

    @FXML
    private TextField F3;

    @FXML
    private StackPane G1;

    // FXML reset method using scene builder, called on B1 click
    // calls create_grid method on G1 stackpane with textfield values
    @FXML
    void reset(MouseEvent event) {
    	MinesFX.create_grid(G1, Integer.valueOf(F1.getText()), Integer.valueOf(F2.getText()), Integer.valueOf(F3.getText()));

    }

    // getter for empty stackpane G1
	public StackPane getG1() {
		return G1;
	}

	

}
