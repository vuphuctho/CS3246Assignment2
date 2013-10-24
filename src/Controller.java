import java.awt.image.BufferedImage;

/* Job of class Controller
 * communicate with View and Model
 * receive requests from View and pass to Model;
 * receive results from Model and pass to View after that
 * All EventListener() will be implemented here 
 */

public class Controller {
	private Model model;
	private View view;
	
	public Controller() {
		this.model = new Model();
		this.view = new View();
	}
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	public void run(){
		view.setVisible(true);
	}
	
	// only for testing
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.run();
	} 
}
