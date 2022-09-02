import java.util.ArrayList;
import java.util.Random;
/**
 * THis class holds all the different methods for running the Genetic algorithm. Each is chosen differently when selecting parents. Otherwise they function the same.
 * Driver iterated over generations, selection gets parents, and performs crossover. Remember mutation is executed in the individual class
 * @author noahgans
 *
 */
public class GeneticSolver {

	Population popToReproduce;//population to reproduce
	double crossOverPecent;//cross over value
	double mutationRate;//mutation rate
	int generations;//number of gen run
	char selectionType;//selection type r = rank t = tournament  b = boltzmann
	int interval;//interval of printing out progress
	
	public GeneticSolver(Population pop, double cross, double mutation, int generations, int interval, char selectionType) {
		// TODO Auto-generated constructor stub
		this.popToReproduce = pop;//set
		this.crossOverPecent = cross;//set
		this.mutationRate = mutation;//set
		this.generations = generations;//set
		this.interval = interval;//set
		this.selectionType = selectionType;//set
		
		driver();//Run GA
		
	}
	/**This method iterated over generations until the goal string is met, or the generations are reached. It contains all the printing statements too. It has
	 * the broad structure of going through the number of generations, and in each generation going through the size of the population and filling the temp
	 * Population with newly generated individuals.
	 * 
	 */
	
	private void driver() {
		// TODO Auto-generated method stub
		
		System.out.println("Generating Solutions..... \n");//starting print message
		
		for (int i = 0; i < this.generations; i++) {//Go through number of generations
			//System.out.println("Gen Number " + this.popToReproduce.size);
			//System.out.println(this.popToReproduce);
			for (int j = 0; j < this.popToReproduce.size; j++) {//go through the population size 
				popToReproduce.addToTemp(selection());//breed a new individual and add to the temporary population
			}
			//after a new generation has been bread 
			popToReproduce.clearPreviousGen();//call the clear previous generation function to clear previous generatio2n
			popToReproduce.updatePop();//update the population with temp population
			
			//target is reached after the generation has been updated
			if (this.popToReproduce.getIndividual(this.popToReproduce.size -1).getFitness() == 50) {//if fitness of best individual = 50
				//print the goal state message
				System.out.println();
				System.out.println("Hit the target!!");
				System.out.println("Target: 		I think this is a reasonable medium sized string!!");
				System.out.println("Best individual:        " + this.popToReproduce.getIndividual(this.popToReproduce.size -1));
				System.out.println("Generation found:" + i);
				System.out.println("Score = " + this.popToReproduce.getIndividual(this.popToReproduce.size -1).getFitness() + "/50 = 100.0%");
				return;
			}
			
			//if the current generation is on the interval number, print out progress message
			if(i % this.interval == 0 & i != 0)
				System.out.println(i + "( " + this.popToReproduce.getIndividual(this.popToReproduce.size -1).getFitness() + "/50):   " + this.popToReproduce.getIndividual(this.popToReproduce.size - 1));
			
		}
		//If all generations have been iterated through, than print out the failed to reach message
		System.out.println("Missed the target...");
		System.out.println("Target: 		I think this is a reasonable medium sized string!!");
		System.out.println("Best individual:        " + this.popToReproduce.getIndividual(this.popToReproduce.size -1));
		System.out.println("Generation found:	" + this.generations);
		System.out.println("Score = " + this.popToReproduce.getIndividual(this.popToReproduce.size -1).getFitness() + "/50 = " + this.popToReproduce.getIndividual(this.popToReproduce.size -1).getFitness() * 2 + ".0%" );

		
		

	}
	
	/**Selection uses the designated selection method to get two parents, and then performs crossover taking into the pC. Only one child is produced in a
	 * crossover. This is done because it makes the driver function better. It can iterate through the size of the population instead of half the population which 
	 * leads to problems with odd number of generations. Also it functionally is the same thing.
	 * 
	 * @return Individual to be added to list
	 */
	public Individual selection() {
		// TODO Auto-generated method stub
		Random rd = new Random(); // creating Random object
		
		Individual parent1 = null;//parents are initlized 
		Individual parent2 = null;
		
		//use Rank selection
		if (this.selectionType == 'r') {
			parent1 = getParentRS();
			parent2 = getParentRS();
		}
		
		//Use tournament selection
		if (this.selectionType == 't') {
			parent1 = getParentTS();
			parent2 = getParentTS();
		}
		//Use Boltzmann selection
		if(this.selectionType == 'b') {
			parent1 = getParentBS();
			parent2 = getParentBS();
		}
		
		
		Individual child;//Initialize child
		
		double crossoverValue = rd.nextDouble();//crossover threshold, 
		int crossPoint = (int) (rd.nextDouble() * parent1.content.length());//crossover point is random
		//System.out.println("Cross over Point == " + crossPoint);
		
		
		//if random crossover is lower than designate pC, crossover occurs
		//System.out.println(crossoverValue + " < " + this.crossOverPecent);
		if (crossoverValue < this.crossOverPecent) {
			child = new Individual(this.mutationRate, parent1.content.substring(0, crossPoint) + parent2.content.substring(crossPoint, parent1.content.length())); //crossover at random chance
			//make a new child with the first half from parent 1, and the second half from parent two
		}
		//if no crossover, child will equal parent 1
		else {
			child = parent1;
		}
		return child;//return child for adding to next generation
		
		
	}
	
	/**
	 * this Method uses the probability of the fitness rank over total ranks of the population to select parents for next generation breeding. This method first
	 * gets the total ranking of which is always the (popSize + 1) (popSize/2). A random double is generated, multiplied by this total rank to get a point to
	 * pull from. This random rank point needs to be turned into a index which is put back into the equation to get the rank of a given size, but instead the 
	 * total rank is known, and we need the population/index, so in the case of size 6 population:
	 * 
	 * total rank = 21 = (6+1)(6/2) = 7*3
	 * Index   		1  		   2   		3   		4 		  	5		  	6 
	 * Rank Range  0-1/21	1/21-3/21	3/21-6/21 	6/21-10/21	10/21-15/21	15/21-1
	 * Selection = 21(randomDouble)
	 * IndexTo select ===>  Selection = (x+1)(x/2)
	 * 
	 * If we solve this, it becomes a quadratic equation which is what is coded below. This allows for quick and easy selection based on rank, where higher rank
	 * permits a larger range. 
	 * 	 * @return
	 */
	private Individual getParentRS() {
		// TODO Auto-generated method stub
		Random rd = new Random(); // creating Random object
	    double selection;  // displaying a random double value between 0.0 & 1.0
	    int totalRank = (popToReproduce.size +1)* (popToReproduce.size/2);//get toatal rank
		selection = rd.nextDouble() * totalRank;//get a value in that toatal ranking
		
		//Execute quadratic equation for selection index
		int parentIndex = (int) (.5 + Math.sqrt(Math.pow(.5, 2)- (4 * (-0.5) * (selection))));//big equation to get index given total rank and num between 0 and 1
			
		return this.popToReproduce.getIndividual(parentIndex - 1);//return that parent (value - 1) so no index error at upper end
	}
	
	
	/**This tournament selection runs a tournament on 1/10 the size of the population. I didn't see that this value was outlined in the assignment, but seemed 
	 * like a good percentage. An array is filled with .1(popSize) number of individuals randomly selected from the population. Then the one with the best
	 * fitness is returned for breeding
	 * 
	 * @return
	 */
	private Individual getParentTS() {
		Individual winner;//winner to be returned
		ArrayList <Individual> tounamte = new ArrayList<Individual>();//Array that will hold members of the tournament
		Random rd = new Random();//new random
		int tournamateSize = (int) (this.popToReproduce.size * .1);//tournaments will be of 1/10 the size of the pop
		int count = 0;//will hold number of members in tournament
		while(count < tournamateSize) {//while with count is used instead of for loop only increase count if new individual is added
			int chosenIndex = (int) (rd.nextDouble() * this.popToReproduce.size);//get next index to add to tournament
			if (!tounamte.contains(this.popToReproduce.getIndividual(chosenIndex))) {//if the selected member is not already in the tournament pool
				tounamte.add(this.popToReproduce.getIndividual(chosenIndex));//add to tournament
				count++;//increase count
			}
		}
		winner = tounamte.get(0);//set winner to first member of the tournament pool
		for (int i = 1; i < tounamte.size(); i++) {//go through the tournament
			if (winner.getFitness() < tounamte.get(i).getFitness()) {//if the individual is better than the current winner
				winner = tounamte.get(i);//reset winner
			}
		}
		return winner;//return winner
	}
	
	
	/**
	 * The Boltzmann selection picks individuals relative to their fitness. This is done by finding the sum of all e^fitness values. After this, a running sum
	 * is kept of the e^fitness value / the first sum for of each individual. When the addition of a individuals (e^fitness)/sum value exceeds the double calculated,
	 * than that individual is selected. Individuals with higher fitness will add more to this running sum, and therfore have a higher chance of being selected.
	 * @return
	 */
	private Individual getParentBS() {
		// TODO Auto-generated method stub
		double sum = 0;//first sum, divisor
		double sum2 = 0;//running sum
		double temp;//temp for comparison
		
		Random rd = new Random(); // creating Random object, because it's probabilities, all adds to one, so no need to normalize it
		double selection = rd.nextDouble();
		
		
		for (int i = 0; i < this.popToReproduce.size; i++) {
			sum += Math.pow(Math.E, popToReproduce.getIndividual(i).getFitness());//Calculate the sum of all e^fitness values
		}
		
		for (int i = 0; i < this.popToReproduce.size; i++) {
			temp = sum2 + Math.pow(Math.E, popToReproduce.getIndividual(i).getFitness()) / sum;//temp equals the running sum plus addition of this iteration
			if (sum2 < selection && selection < temp) {//if in between these two values
				return this.popToReproduce.getIndividual(i);//choose this individual for breeding
			}
			sum2 = temp;//set the sum to the temp, as it is the addition of this iterations sum
		}
		return this.popToReproduce.getIndividual(this.popToReproduce.size - 1);//in none are selected which should not happen, chose the last individual 
		//which by structure has he highest chance of getting selected.
	}

}
