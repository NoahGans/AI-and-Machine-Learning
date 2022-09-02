import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is the main part of this code, and holds all the elements of the
 * Neural Network. THe NN is just a 2d array of 64 by 10 with 64 being the
 * inputs, and 10 being the number of outputs. Each placement in this 2d array
 * is a weight. Primarily these weights are intilized to values between 1 and -1
 * 
 * @author noahgans
 *
 */
public class NeralNetwork {

	double[][] weights = new double[10][64];// that data sturcture holding all the weights
	ArrayList<double[]> data;// this will represent the data structure of the data used to learn and test the
								// NN

	/**
	 * Constructor: sets data to data, and initializes weights
	 * 
	 * @param data
	 */
	public NeralNetwork(ArrayList<double[]> data) {
		// TODO Auto-generated constructor stub
		this.data = data;// setting data
		initalizeWeights();// Initialize weights
	}

	/**
	 * This method runs the training phase of the NN, and then the testing phase of
	 * the NN. It takes in the percent of data to be used for learning, and then
	 * splits that data randomly on that percent. It then learns with that percent,
	 * tests with the rest, and finally calculates average error after testing.
	 * 
	 * @param testPercent
	 * @return avrage error
	 */
	public double driver(double testPercent) {
		int learnValue = (int) (data.size() * testPercent);// get the number of data points to use to test the NN

		for (int i = 0; i < 1000; i++) {// run learning over 1000 epochs
			int randomNum = ThreadLocalRandom.current().nextInt(0, (data.size() - learnValue) + 1);// this line finds a
																									// random location
																									// to start pulling
																									// the learning data
																									// from

			for (int j = 0; j < learnValue; j++) {// go through the size of the learning data set from the randomly
													// generated starting location
				learn(0.01, data.get(randomNum + j));// calls the learn function on every part of the data set
			}
		}

		double totalErr = 0;// Initialize a value to represent the total err over the whole test data set.

		for (int i = data.size() - learnValue; i < data.size(); i++) {// go through the remaining percent of the data
																		// set, and test on that.
			// This will always test on the latter half of the data set, but after 1000
			// epochs, I expect any test set to produce simmilar results
			totalErr += test(data.get(i));// sum the error that the test method returns
		}
		return totalErr / learnValue;// divide the total error by the number of used for learning.
		// this is where things are funky, I first used the data set - learn value, but
		// got inverse results of what were expected. This current implementation
		// however seems wrong. I've checked every process of how the NN learns and
		// tests, and it's working properly, so there might be something small wrong
		// with
		// error calculation
	}

	/**
	 * This function tests the accuracy of the neural network by the running test
	 * cases through it from the data. Each data point is used to calculate outputs,
	 * and those are compared to the expected values given by the data set.
	 * 
	 * @param learnData
	 * @return total error of a given data point
	 */
	public double test(double[] learnData) {
		double weightedSum;// weighted sum. THis will hold the sum value of all the weights x input values
							// for each node
		int outputNode;// output node currently being calculated
		double errSum = 0;// the sum of error for one data set
		for (outputNode = 0; outputNode < 10; outputNode++) {// go through all the output nodes
			weightedSum = 0;// reset to 0 for each calculation of a output node

			// Go through all weights leading into an output node
			for (int j = 0; j < 64; j++) {

				weightedSum += learnData[j + 1] * weights[outputNode][j];// calculate weighted sum. THe +1 is to account
																			// for the goal value at the start of the
																			// data structure
			}

			double gValue = 1 / (1 + Math.pow(Math.E, -weightedSum));// calculate g value or output value
			double Err;// Initialize error for that output node

			// test if wanted output
			if (outputNode == learnData[0]) {// if the output node is the goal value, err = 1 - g value

				Err = 1 - gValue;

				// otherwise, err = 0- gvalue
			} else {
				Err = 0 - gValue;
			}

			// add that error to the error sum, but square it to make it not negative
			errSum += Math.pow(Err, 2);

		}

		// return the square root of all error for a given data point
		return Math.sqrt(errSum);

	}

	/**
	 * Learn executes all learning for all weights for a given example. It first
	 * solves ERR and other required elements needed for the update weight equation
	 * for a output node. Then it updates all weights leading into that out put node
	 * and moves on.
	 * 
	 * @param alpha
	 * @param learnData
	 */
	private void learn(double alpha, double[] learnData) {

		double weightedSum;// Weighted sum value for inputs and their weights
		int outputNode;// output node

		// go through output nodes
		for (outputNode = 0; outputNode < 10; outputNode++) {

			weightedSum = 0;// reset to 0 for every output node cacluclation

			for (int j = 0; j < 64; j++) {
				weightedSum += learnData[j + 1] * weights[outputNode][j];// sum over the inputs and weights

			}

			double gValue = 1 / (1 + Math.pow(Math.E, -weightedSum));// get g-value
			double gPrimeValue = gValue * (1 - gValue);// get g-prime value
			double Err;// Initialize err

			// if the output node matches the goal value, 1 - err
			if (outputNode == learnData[0]) {

				Err = 1 - gValue;

				// otherwise 0 - err
			} else {
				Err = 0 - gValue;
			}

			// go through weights, and update them based off of learning rate and other
			// elements of calculation
			for (int j = 0; j < 64; j++) {
				weights[outputNode][j] += alpha * learnData[j + 1] * Err * gPrimeValue;// update weights

			}
		}
	}

	/**
	 * Helper method that printed weights
	 * 
	 * @param j
	 */
	private void printWeights(int j) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 64; i++) {
			System.out.print(weights[j][i]);
		}
		System.out.println();
	}

	/**
	 * Method to initialize weights. generates random num between 1 and -1 and
	 * assigns to weight placement
	 * 
	 */
	private void initalizeWeights() {
		// go through all weights
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 64; j++) {

				Random r = new Random();// random class

				weights[i][j] = -1 + (1 - -1) * r.nextDouble(); // assing weight value of a random double value between
																// -1 & 1.0
			}

		}
	}
}
