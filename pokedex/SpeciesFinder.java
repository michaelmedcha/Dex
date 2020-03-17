package pokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

public class SpeciesFinder implements Finder, Comparator<Species>{

	Pokedex aPokedex ;
	String currentQuery ;
	
	public int compare(Species s1, Species s2)
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
	
	public SpeciesFinder(Pokedex px)
	{
		aPokedex = px ;
	}
	
	public Species bestGuess(String query)
	{
		currentQuery = query ;
		
		ArrayList<Species> specList = aPokedex.getSpeciesList() ;
		specList.sort(this);
		return specList.get(0) ;
	}
	
	public String bestStringGuess(String query)
	{
		currentQuery = query ;
		
		ArrayList<Species> specList = aPokedex.getSpeciesList() ;
		specList.sort(this);
		return specList.get(0).getName() ;
	}
	
	public ArrayList<Species> guessesList(String query, int numGuesses)
	{
		assert numGuesses <= aPokedex.getSpeciesList().size() ;
		
		currentQuery = query ;
		ArrayList<Species> specList = aPokedex.getSpeciesList() ;
		specList.sort(this);
		
		ArrayList<Species> result = new ArrayList<>() ;
		
		for(int i = 0 ; i < numGuesses; i++)
		{
			result.add( specList.get(i) ) ;
		}
		
		return result ;
	}
	
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
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "integrability" ;
		String r = "integrability" ;
		System.out.println((SearchTools.jaccardIndex(s,r))) ;
		System.out.println((SearchTools.jaccardIndex(s,"lityintegrabi"))) ;
	}

}
