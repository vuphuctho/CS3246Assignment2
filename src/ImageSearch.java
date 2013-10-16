/* Job of class ImageSearch
 * Main class: create instance of Controller, Model and View
 * run the program
 */
public class ImageSearch {
	public static void main(String[] args) {
		Model model = new Model();
		View view = new View();
		Controller controller = new Controller(model, view);
		controller.run();
	}
}
