import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class parses the data file given for the assignment, and fills an array
 * of double arrays with the data. The most notable part of this class is that
 * the first element of every array is the goal value for the following bit
 * values. This makes the storage data structure simpler.
 * 
 * 
 * 
 * @author noahgans
 *
 */
public class Parse {

	ArrayList<double[]> listOfExamples;// data structure to be filled

	public Parse(ArrayList<double[]> data) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		this.listOfExamples = data; // set input 2d array to list of examples
		readFile();// call read file

	}

	/*
	 * THis method reads the file, and fills the data structure with the appropriate
	 * data. Again, the value of the first element is equal to the index the 1
	 * appears in the following line after the bit map. 0,0,1,0,0,0,0,0,0 goals
	 * result in the first value of the array to be 3, and the rest to be the bitmap
	 * info.
	 * 
	 */
	public void readFile() throws FileNotFoundException {

		File file = new File("digit-examples-all.txt");// new file of the data set
		Scanner sc = new Scanner(file);// scanner to read file
		String[] values;// string array that will hold the string values of each line

		while (sc.hasNextLine()) {// while there is more of the file to be gone through
			double[] inputArray = new double[65];// create an array of length 65 for the goal value, and the remaining 8
													// x 8 bit map indexes
			values = sc.nextLine().split(" "); // split the line on the spaces. gets separate values

			// iterate through the values of the split line
			for (int j = 0; j < values.length; j++) {

				// if the count is in the index of the data storage structure. does not include
				// index 0, because that must be saved for the goal value.
				// this conditional accounts for the ')' and '(' at the start and end of each
				// line
				if (j > 0 && j < 64) {

					double valueToAdd = Double.parseDouble(values[j]);// convert the value to a double
					inputArray[j] = valueToAdd;// add that double to the array list
				}
			}

			values = sc.nextLine().split(" ");// split the next line which will have the
			for (int i = 0; i < values.length; i++) {// go through that line
				if (i > 0 && i < 11) {// account for the ( and ) at the start and end

					if (values[i].equals("1.0")) {// if the value at that index is equal to one
						inputArray[0] = i - 1;// add the value of that index to the start of the data structure
					}

				}

			}

			listOfExamples.add(inputArray);// add final array to the larger arrayList, and continue to the next example
		}

	}

}
