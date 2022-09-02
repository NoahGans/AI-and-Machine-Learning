import java.util.Random;
/**This class represents and individual of a population. There are two constructors, one for random gen an individual, and one for when there is an input string.
 * The second is used after the initial generation of a population. An idnvidual also contains its fitness score which is how many of its letters match the 
 * string goal.
 * 
 * @author noahgans
 *
 */
public class Individual {
	String possibleSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+=-[]{}|;’:\",./<>? ";//possible letters to be used for generation
	String content = "";//the content of an individual, the string that represents them
	int fitness;//fitness score max is 50
	String goalString = "I think this is a reasonable medium sized string!!";//goal string   
	
	
	/**This constructor is used when the population class is generating random individuals for the population. 
	 * 
	 */
	public Individual() {
		//Randomly Generate the individual
		randomGenIndividual();
	}
	
	
	/**Constructs individual if given string. Follows the same process as randomGenIndividual without the random generation. 
	 * 
	 * @param mutation
	 * @param inputString
	 */
	public Individual (double mutation, String inputString) {
		char[] listOfChar = inputString.toCharArray();//split input string into char array. This was nessicary  for mutation, so an individual char could be edited
		Random rd = new Random();//new random
		for (int i = 0; i < listOfChar.length; i++) {//go through the char array
			double mutationThreshold = rd.nextDouble();//mutation value. 
			
			if (mutationThreshold < mutation) {//if generated random num is lower than input mutation level than mutate on a letter
				int mutationChar = (int) (this.possibleSymbols.length() * rd.nextDouble());//get random index to chose from possible chars
				listOfChar[i] = this.possibleSymbols.charAt(mutationChar);//replace 
			}
			
			if (listOfChar[i] == this.goalString.charAt(i)) {//count fitness
				this.fitness++;//add to fitness
			}
			this.content += listOfChar[i];//return to string form and add to content
		}
	}
	
	/**Constructs individual if not given string. Chooses a random letter to add to the content string until individual is constructed. Keeps track of
	 * how similar to goal string it is as it does this.
	 */
	private void randomGenIndividual() {
		// TODO Auto-generated method stub
	    Random r = new Random();//random for char selection
	    
		for (int i = 0; i < 50; i++) {//go through the size of an individual
			char charToAdd = possibleSymbols.charAt(r.nextInt(possibleSymbols.length()));//char to add is selected randomly from possible alphabet string
			if (goalString.charAt(i) == charToAdd) {//if char randomly being added matches the goalString
				this.fitness++;//increase fitness score
			}
			content += charToAdd;//add char to content string
		}
	}
	
	/**Returns the fitness of an individual
	 * 
	 * @return
	 */
	public int getFitness() {
		return fitness;
	}
	@Override
	/**toString, returns content
	 * 
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return this.content;
	}
	
	
	
}
