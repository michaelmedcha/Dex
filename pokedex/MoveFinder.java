package pokedex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

public class MoveFinder implements Comparator<Move>{

	Pokedex aPokedex;
	String currentQuery;
	
	public int compare(Move s1, Move s2)
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
	
	public MoveFinder(Pokedex px)
	{
		aPokedex = px ;
	}
	
	public Move bestGuess(String query)
	{
		currentQuery = query ;
		Hashtable<String,Move> moveTable = aPokedex.getMoveBox().getMoves() ;
		ArrayList<Move> moveList = new ArrayList<>(); 
		for(Move ab : moveTable.values() )
		{
			moveList.add(ab) ;
		}
		
		moveList.sort(this);
		
		return moveList.get(0) ;
	}
	
	public ArrayList<Move> guessesList(String query, int numGuesses)
	{
		currentQuery = query ;
		Hashtable<String,Move> moveTable = aPokedex.getMoveBox().getMoves() ;
		ArrayList<Move> moveList = new ArrayList<>(); 
		for(Move ab : moveTable.values() )
		{
			moveList.add(ab) ;
		}
		
		ArrayList<Move> result = new ArrayList<>() ;
		
		moveList.sort(this);
		
		assert moveList.size() >= numGuesses;
		
		for(int i = 0 ;i < numGuesses; i++)
		{
			result.add(moveList.get(i)) ;
		}
		
		return result ;
	}
	
}
