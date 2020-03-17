package pokedex;

import java.util.ArrayList;

public interface Finder {

	public String bestStringGuess(String query) ;
	public ArrayList<String> guessesStringList(String query, int numGuesses) ;
	
}
