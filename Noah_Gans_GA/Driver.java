/**
 * This class is the driver for the whole program, it takes in the arguments and
 * builds necessary data structures, and runs the geneticSolver
 * 
 * @author noahgans
 *
 */
public class Driver {

	public static void main(String[] args) {

		int populationSize = Integer.parseInt(args[0]);// get population size from 
		String method = args[1];// gets selection method from input
		double pC = Double.parseDouble(args[2]);// gets cross over prob
		double pM = Double.parseDouble(args[3]);// mutation prob
		int generations = Integer.parseInt(args[4]);// num generations
		int interval = Integer.parseInt(args[5]);// interval for printing out genetic process
		char selectionType = 0;// char to be used to signal what type of selection process to use

		// sets to char's for easier comparisons later
		if (method.equals("rs")) {// if rank
			selectionType = 'r';// set
		}
		if (method.equals("ts")) {
			selectionType = 't';
		}
		if (method.equals("bs")) {
			selectionType = 'b';
		}

		Population geneticPopulation = new Population(populationSize);// build population of input size
		GeneticSolver geneticSolve = new GeneticSolver(geneticPopulation, pC, pM, generations, interval, selectionType);// run
																														// geneticSolve
																														// with
																														// inputs
																														// given

	}

}
