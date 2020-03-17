package pokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class AbilityBox {
	private Hashtable<String,Ability> anAbilityBox ;
	private ArrayList<String> infos ;
	
	public AbilityBox(String abilityFilename)
	{
		try
		{
			anAbilityBox = new Hashtable<>();
			FileReader fr = new FileReader(abilityFilename) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			br.readLine();
			String currentLine = br.readLine() ;
			while(currentLine != null)
			{
				String[] currentSplit = currentLine.split(";") ;
				assert currentSplit.length == 4 : Arrays.toString(currentSplit);
				anAbilityBox.put(currentSplit[0].toLowerCase(), new Ability(currentSplit)) ;
				currentLine = br.readLine();
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Where are the abilities? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+abilityFilename) ;
			System.out.println(e) ;
		}
		
		infos = new ArrayList<>() ;
		infos.add("ALL") ;
		infos.add("EFF") ;
		infos.add("DEFF") ;
		infos.add("OWE");
		
	}
	
	public Hashtable<String, Ability> getAbilityTable()
	{
		return (Hashtable<String, Ability>) anAbilityBox.clone() ;
	}
	
	//TODO:asserts and error checking for getters!
	
	//TODO:Temporarily use isInBox for error checking.
	
	public String[] getInfo(String what, String aName) throws AbilityNotFoundException
	{
		assert infos.contains(what) : what+" is not an option." ;
		
		if(!isInBox(aName))
		{
			throw new AbilityNotFoundException("\""+aName + "\" cannot be found in the ability box.") ;
		}
		
		if(what.equals("ALL"))
		{
			
			return getAbility(aName).getAll() ;
		}
		
		if(what.equals("EFF")) 
		{
			String[] out = {getEffect(aName)} ;
			return out ;
		}
		
		if(what.equals("DEFF")) 
		{
			String[] out = {getDetailedEffect(aName)} ;
			return out ;
		}
		
		if(what.equals("OWE"))
		{
			String[] out = {getOverWorldEffect(aName)} ;
			return out ;
		}
		
		return null ;
	}
	
	public Ability getAbility(String aName) 
	{	
		return anAbilityBox.get(aName.toLowerCase()) ;
	}
	
	public String getEffect(String aName) 
	{
		return anAbilityBox.get(aName.toLowerCase()).getEffect() ;
	}
	
	public String getDetailedEffect(String aName) 
	{
		return anAbilityBox.get(aName.toLowerCase()).getDetailedEffect() ;
	}
	
	public String getOverWorldEffect(String aName) 
	{
		return anAbilityBox.get(aName.toLowerCase()).getOverWorldEffect() ;
	}
	
	public boolean isInBox(String aName) 
	{
		return anAbilityBox.containsKey(aName.toLowerCase()) ;
	}
	
	class AbilityNotFoundException extends Exception
	{
		public AbilityNotFoundException(String errorMessage, Throwable cause)
		{
			super(errorMessage, cause) ;
			
		}
		public AbilityNotFoundException(String errorMessage)
		{
			super(errorMessage) ;
			
		}
		
	}
	
	public static void main(String[] args) throws AbilityNotFoundException 
	{
		AbilityBox ab = new AbilityBox("C:/Users/marie/Desktop/Data for Pokédex/Data_for_v2/all_abilities.csv") ;
		System.out.println(Arrays.toString(ab.getInfo("ALL","Adaptability") ) ) ;
		
	}
	
}
