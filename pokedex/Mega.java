package pokedex;


import java.util.LinkedList ;

//TODO : Must implement the type change or store the type during Mega evolution.
 
public class Mega extends Species {
	private int aNumberOfMegas ;
	private LinkedList<Species> aListOfMegas = new LinkedList<>() ;
	private Species currentState = this ; 
	
	public Mega(int pPokédexNumber, String pName, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
	{
		super( pPokédexNumber, pName, pType1, pType2, preEv, pStats, pAbilities ) ;
	}
	
	public Mega(int pDexNum, int regDexNum, String pName, int gen, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
	{
		super( pDexNum, regDexNum, pName, gen, pType1, pType2, preEv, pStats, pAbilities) ;
	}
	
	public void addNewMega(Species spec)
	{
		aNumberOfMegas++ ;
		aListOfMegas.add(spec) ;
	}
	
	public int getNumberOfMegas()
	{
		return aNumberOfMegas ;
	}
	
	public Species getFirstMega()
	{
		assert aListOfMegas.size() >= 1 ;
		return aListOfMegas.getFirst() ;
	}
	
	public Species getSecondMega()
	{
		assert aListOfMegas.size() == 2 ;
		return aListOfMegas.get(1) ;
	}
    
}

