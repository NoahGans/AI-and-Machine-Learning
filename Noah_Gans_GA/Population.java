import java.util.ArrayList;
/**This population class is the data structure that holds all generations. Only one is made per solve to save on space complexity. It uses a process to 
 * always keep the population ordered from least fit to most fit from index 0 to final index. Constructor builds a random population
 * 
 * @author noahgans
 *
 */
public class Population {
	ArrayList<Individual> tempPopulation;//This will be used in crossover to hold the next generation before clearing the previous
	ArrayList<Individual>[] initalDataStructure = new ArrayList[51];//2d array for population building. Size 51 to match top fitness of an individual 
	ArrayList<Individual> population;//this is the array that holds a generation
	int size;//size of the population
	int genNum = 0;//genNumber

	/**Constructor for initializing the population. 
	 * 
	 * @param size
	 */
	public Population(int size) {
		// TODO Auto-generated constructor stub
		population = new ArrayList<Individual>();//makes new population array
		tempPopulation = new ArrayList<Individual>();//makes new temp array for future generations
		this.size = size;//sets size
		generatePop(size);//calls generatePop to generate individuals and fill the population array 
	}

	/**This method Clears a previous generation.
	 * 
	 */
	public void clearPreviousGen() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.initalDataStructure.length; i++) {//go through all arrayLists in the array 
			if (this.initalDataStructure[i] != null) {//if they  are null do nothing
				this.initalDataStructure[i].clear();//otherwise clear them
			}
			
		}
		this.population.clear();//clear previous gen, save on space
	}

	/**THis method adds an individual to the temp population. 
	 * 
	 * @param individualToAdd
	 */
	public void addToTemp(Individual individualToAdd) {
		// TODO Auto-generated method stub
		this.tempPopulation.add(individualToAdd);
	}

	
	/**
	 * This method updates a population from the temp population to the actual population. Functions a lot like generatePop.
	 */
	public void updatePop() {
		// TODO Auto-generated method stub
		for (int i = 0; i < size; i++) {//go through the size of the population
			Individual individualToAdd = this.tempPopulation.get(i);//get an individual from the temp population
			
			if (initalDataStructure[individualToAdd.getFitness()] == null) {//if there are no other individual at a given index of that individuals fitness
				initalDataStructure[individualToAdd.getFitness()] = new ArrayList<Individual>();//add a new array list to that index in the array of arraylists
			}

			initalDataStructure[individualToAdd.getFitness()].add(individualToAdd);//add the individual to the data structure
		}
		
		//this is where the 2d array is compressed into the 1d array
		for (int i = 0; i < initalDataStructure.length; i++) {//go through 2d array
			if (initalDataStructure[i] != null) {//if array has something in it
				for (int j = 0; j < initalDataStructure[i].size(); j++) {//go through that array
					population.add(initalDataStructure[i].get(j));//add every element to the final population list
				}
			}
		}
		//now that the temp population has been transfered, clear it. 
		this.tempPopulation.clear();
	
	}

	
	/**Generate population first builds and a individual, and then adds that individual to a 2D array by array list. This data structure allows multiple
	 * individuals to be added to a certain index based on their fitness. After the 2d array is filled, it is gone through and added to the 1d population array.
	 * This makes for a quick sorting of individuals on rank without having to use a sorting method.
	 * @param size
	 */
	private void generatePop(int size) {
		
		//Count through the size of the intended generation
		for (int i = 0; i < size; i++) {
			Individual individualToAdd = new Individual();//make a new random individual

			if (initalDataStructure[individualToAdd.getFitness()] == null) {//if there are no other individual at a given index of that individuals fitness
				initalDataStructure[individualToAdd.getFitness()] = new ArrayList<Individual>();//add a new array list to that index in the array of arraylists
			}

			initalDataStructure[individualToAdd.getFitness()].add(individualToAdd);//add that individual to that arrayList at the end
		}

		
		//this is where the 2d array is compressed into the 1d array
		for (int i = 0; i < initalDataStructure.length; i++) {//go through 2d array
			if (initalDataStructure[i] != null) {//if array has something in it

				for (int j = 0; j < initalDataStructure[i].size(); j++) {//go through that array
					population.add(initalDataStructure[i].get(j));//add every element to the final population list
				}
			}
		}
	}

	/**This function returns an individual in the array given an index
	 * 
	 * @param index
	 * @return
	 */
	public Individual getIndividual(int index) {
		// TODO Auto-generated method stub
		return this.population.get(index);
	}

	@Override
	
	/**To string for debugging, prints all individuals in the population
	 */
	public String toString() {
		// TODO Auto-generated method stub
		String returnString = "";
		for (int i = 0; i < population.size(); i++) {
			returnString += (population.get(i) + " " + population.get(i).getFitness() + "\n");
		}
		return returnString;
	}
}
