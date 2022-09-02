import java.io.FileNotFoundException;
import java.util.ArrayList;
/**This class is the driver for the NeralNetwork, and scrapes the data, and then creates and runs the NeralNetwork.
 * 
 * @author noahgans
 *
 */
public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
		ArrayList<double[]> data = new ArrayList<double[]>(); // This arraylist of arrays of doubles will hold all the data used for learning and testing the NN
		
		Parse run = new Parse(data);//this fills the arraylist with data using the parse class
		NeralNetwork main = new NeralNetwork(data);//this initializes the NeralNetwork Class and builds the NN with weights
		
		
		
		//Goes through all learn vs test rations, and runs them on the NN
		for (int i = 1; i < 10; i++) {
			
			double learnVTest = i / 10.0;//gets what percent to use for testing
			System.out.println(100 * learnVTest + "% used for learning \nAverage error =" + main.driver(learnVTest) + "\n");//output print statement
		}
	}
}
