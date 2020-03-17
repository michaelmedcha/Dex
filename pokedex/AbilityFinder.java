package pokedex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

public class AbilityFinder implements Comparator<Ability>{

	Pokedex aPokedex ;
	String currentQuery ;
	
	public int compare(Ability s1, Ability s2)
	{
		double j1 = SearchTools.jaccardIndex(currentQuery, s1.getName()) ;
		double j2 = SearchTools.jaccardIndex(currentQuery, s2.getName()) ;
		
		if(j1>j2)
		{
			return -1 ;
		}
		if(j1==j2)
		{
			return 0 ;
		}
		
		return 1;
	}
	
	public AbilityFinder(Pokedex px)
	{
		aPokedex = px ;
	}
	
	public Ability bestGuess(String query)
	{
		currentQuery = query ;
		Hashtable<String,Ability> abilitiesTable = aPokedex.getAbilityBox().getAbilityTable() ;
		ArrayList<Ability> abilitiesList = new ArrayList<>(); 
		for(Ability ab : abilitiesTable.values() )
		{
			abilitiesList.add(ab) ;
		}
		
		abilitiesList.sort(this);
		
		return abilitiesList.get(0) ;
	}
	
	/*
	public String bestStringGuess(String query)
	{
		currentQuery = query ;
		
		ArrayList<Species> specList = aPokedex.getSpeciesList() ;
		specList.sort(this);
		return specList.get(0).getName() ;
	}
	*/
	
	public ArrayList<Ability> guessesList(String query, int numGuesses)
	{
		/*assert numGuesses <= aPokedex.getSpeciesList().size() ;
		
		currentQuery = query ;
		ArrayList<Species> specList = aPokedex.getSpeciesList() ;
		specList.sort(this);
		
		ArrayList<Species> result = new ArrayList<>() ;
		
		for(int i = 0 ; i < numGuesses; i++)
		{
			result.add( specList.get(i) ) ;
		}
		
		return result ;
		*/
		
		currentQuery = query ;
		Hashtable<String,Ability> abilitiesTable = aPokedex.getAbilityBox().getAbilityTable() ;
		ArrayList<Ability> abilitiesList = new ArrayList<>(); 
		for(Ability ab : abilitiesTable.values() )
		{
			abilitiesList.add(ab) ;
		}
		
		assert numGuesses <= abilitiesList.size() ;
		
		abilitiesList.sort(this);
		
		ArrayList<Ability> result = new ArrayList<>() ;
		
		for(int i = 0 ; i<numGuesses; i++)
		{
			result.add(abilitiesList.get(i)) ;
		}
		
		return result;
	}
	
	/*
	public ArrayList<String> guessesStringList(String query, int numGuesses)
	{
		assert numGuesses < 721 ;
		
		currentQuery = query ;
		ArrayList<Species> specList = aPokedex.getSpeciesList() ;
		specList.sort(this);
		
		ArrayList<String> result = new ArrayList<>() ;
		
		for(int i = 0 ; i < numGuesses; i++)
		{
			result.add( specList.get(i).getName() ) ;
		}
		
		return result ;
	} 
	*/

}
