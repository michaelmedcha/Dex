package pokedex;

import java.util.LinkedList;

public class MultiForme extends Species{
	private int aNumberOfFormes ;
	private LinkedList<Species> aListOfFormes = new LinkedList<>() ;
	private Species currentForme = this ;
	/**
	 * 
	 * @param pName
	 * @param pPokédexNumber
	 * @param pStats
	 * @param numberOfForms Number of different Formes the Species can take, excluding the standard one.
	 */
	
	public MultiForme(int pPokédexNumber, String pName, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
	{
		super( pPokédexNumber, pName, pType1, pType2, preEv, pStats, pAbilities ) ;
	}
	
	public MultiForme(int pokédexNumber, int regionalNumber, String name,
			int generation, String type1, String type2,
			String preEvolutionName, int[] stats, String[] stringAbilities) {
		
		super(pokédexNumber, regionalNumber, name, generation, type1, type2,
				preEvolutionName, stats, stringAbilities);
	}

	public void addNewForme(Species spec)
	{
		aNumberOfFormes++ ;
		aListOfFormes.add(spec) ;
	}
	
	public int getNumberOfFormes()
	{
		return aNumberOfFormes ;
	}
	
	public void setForme(String pName)
	{
		//Check pName is == aName or a Forme's name and list of Formes not empty.
		assert isFormeAssignable(pName) ;
		assert ! aListOfFormes.isEmpty() ;
		//Make currentForme pName
		
		if(pName.equals(getName()))
		{
			currentForme = this ;
		}
		else
		{
			currentForme = findForme(pName) ;
		}
		
	}
	
	private boolean isFormeAssignable(String pName)
	{
		if(pName.equals(getName()))
		{
			return true ;
		}
		for(Species spec : aListOfFormes)
		{
			if(pName.equals(spec.getName()))
			{
				return true ;
			}
		}
		return false ;
	}
	
	private Species findForme(String pName)
	{
		for(Species spec : aListOfFormes)
		{
			if(spec.getName().equals(pName))
			{
				return spec ;
			}
		}
		return null ;
	}
	
	public LinkedList<Species> getFormes()
	{
		return (LinkedList<Species>) aListOfFormes.clone() ;
	}
}
