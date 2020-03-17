package pokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class AbilityBox2 {
	private Hashtable<String,String[]> anAbilityBox ;
	private ArrayList<String> infos ;
	
	public AbilityBox2(String abilityFilename)
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
				anAbilityBox.put(currentSplit[0], Arrays.copyOfRange(currentSplit,1,4)) ;
				currentLine = br.readLine();
			}
			
		}
		catch(IOException e)
		{
			System.out.println(e) ;
		}
		
		infos = new ArrayList<>() ;
		infos.add("ALL") ;
		infos.add("DESCR") ;
		infos.add("DDESCR") ;
		infos.add("OWE");
		
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
			
			return getAbility(aName) ;
		}
		
		if(what.equals("DESCR")) 
		{
			String[] out = {getDescription(aName)} ;
			return out ;
		}
		
		if(what.equals("DDESCR")) 
		{
			String[] out = {getDetailedDescription(aName)} ;
			return out ;
		}
		
		if(what.equals("OWE"))
		{
			String[] out = {getOverWorldEffect(aName)} ;
			return out ;
		}
		
		return null ;
	}
	
	public String[] getAbility(String aName) 
	{	
		String[] info = anAbilityBox.get(aName) ;
		assert info.length == 3 ;
		String[] result = {aName,info[0],info[1],info[2]} ;
		return result;
	}
	
	public String getDescription(String aName) 
	{
		
		String[] info = anAbilityBox.get(aName) ;
		return info[0] ;
	}
	
	public String getDetailedDescription(String aName) 
	{
		String[] info = anAbilityBox.get(aName) ;
		return info[1] ;
	}
	
	public String getOverWorldEffect(String aName) 
	{
		String[] info = anAbilityBox.get(aName) ;
		return info[2] ;
	}
	
	public boolean isInBox(String aName) 
	{
		return anAbilityBox.containsKey(aName) ;
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
		
		
	}
	
}
