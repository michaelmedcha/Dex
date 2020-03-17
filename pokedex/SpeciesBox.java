package pokedex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

public class SpeciesBox implements Comparator<Species>
{
	ArrayList<Species> aSpeciesList ;
	Hashtable<String,Species> aMegasTable;
	Hashtable<String, Species> aFormesTable;
	Hashtable<String, Species> aBattleSwappersTable;
	
	public SpeciesBox(String mainFile, String megaFile, String formeFile, String bsFile, String ftFile, String eggsEvolsFile, String EVsFile) {
		
		aMegasTable = new Hashtable<>();
		aFormesTable = new Hashtable<>();
		aBattleSwappersTable = new Hashtable<>();
		loadSpecies(mainFile) ;
		loadMegas(megaFile) ;
		loadFormes(formeFile);
		loadBattleSwappers(bsFile);
		setMegas();
		setFormes();
		setBattleSwappers();
		//loadMegas() and loadFormes() must be before assigning flavor texts, egg groups, evolutions and EVs.
		attachFlavorTexts(ftFile);
		setEggGroupsAndEvolutionMethods(eggsEvolsFile);
		attachEVs(EVsFile) ;
		
		
	}
	
	@Override
	public int compare(Species s1, Species s2)
	{
		
		return s1.getPokédexNumber()-s2.getPokédexNumber() ;
	}
	
	public void sortByName()
	{
		aSpeciesList.sort(new Species());
	}
	
	public void sortByPokedexNumber()
	{
		aSpeciesList.sort(this);
	}
	
	private void loadSpecies(String mainFile)
	{
		try
		{
			aSpeciesList = new ArrayList<>();
			FileReader fr = new FileReader(mainFile);
			BufferedReader br = new BufferedReader(fr) ;
			String currentLine = br.readLine() ;
			
			while(currentLine!=null)
			{
				String[] currentSplit = currentLine.split(";") ;
//int pDexNum, int regDexNum, String pName, int gen, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities				
				int dexNum = Integer.parseInt(currentSplit[0]) ;
				int rDexNum = Integer.parseInt(currentSplit[1]) ;
				String name = currentSplit[2];
				int gen = Integer.parseInt(currentSplit[3]);
				String t1 = currentSplit[4];
				String t2 = currentSplit[5];
				String preEv = currentSplit[6];
				
				String a1 = currentSplit[7];
				String a2 = currentSplit[8];
				String ha = currentSplit[9];
				
				String[] abilities = {a1,a2,ha};
				
				int totalBS = Integer.parseInt(currentSplit[10]);
				int hp = Integer.parseInt(currentSplit[11]);
				int atk = Integer.parseInt(currentSplit[12]);
				int def = Integer.parseInt(currentSplit[13]);
				int spAtk = Integer.parseInt(currentSplit[14]);
				int spDef = Integer.parseInt(currentSplit[15]);
				int spd = Integer.parseInt(currentSplit[16]);

				int[] stats = {totalBS, hp, atk, def, spAtk, spDef, spd} ;
				
				Species currentSpecies = new Species(dexNum, rDexNum, name, gen, t1, t2, preEv, stats, abilities) ;
				
				aSpeciesList.add(currentSpecies);
				currentLine = br.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("Where are the Species?  Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+mainFile) ;
			System.out.println(e) ;
		}
	}
	
	private void loadMegas(String megaFile)
	{
		try
		{
			FileReader fr = new FileReader(megaFile) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ; currentLine = br.readLine() ; currentLine = br.readLine() ;
			
			while(currentLine != null)
			{
				String[] currentInfo = currentLine.split(";") ;
				int dexNumber = Integer.parseInt( currentInfo[0] ) ;
				String name = currentInfo[1].replaceFirst("Mega ", "") ;
				String type1 = currentInfo[2] ;
				String type2 = currentInfo[3] ;
				String ability = currentInfo[4] ;
				
				int hp = Integer.parseInt( currentInfo[5] ) ;
				int atk = Integer.parseInt( currentInfo[6] ) ;
				int def = Integer.parseInt( currentInfo[7] ) ;
				int spAtk = Integer.parseInt( currentInfo[8] ) ;
				int spDef = Integer.parseInt( currentInfo[9] ) ;
				int spd = Integer.parseInt( currentInfo[10] ) ;
				
				int baseStat = hp+atk+def+spAtk+spDef+spd ;
				
				int[] stats = {baseStat, hp, atk, def, spAtk, spDef, spd} ;
				
				if(type2.equals("-"))
				{
					type2 = "NONE" ; 
				}
				
				//These store the temporary Megas, they will be used to build the actual Mega Objects in aPKMNList
				
				Species currentMega = new Species(name, dexNumber, type1, type2, stats) ;
				//TODO:let the Megas store their ability.
				currentMega.setStringAbility(ability) ;
				//System.out.println(currentMega) ;
				aMegasTable.put(currentMega.getName(), currentMega) ;
				
				currentLine = br.readLine() ;
			}
			
			
		}
		catch(IOException e)
		{
			System.out.println("Where are the Megas? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+megaFile) ;
		}
	}
	
	private void setMegas()
	{
		for(Species spec : aMegasTable.values() )
		{
			String searchName = spec.getName().split(" ")[0] ;
			Species poké = find(searchName, true) ;
			assert poké != null : "Some Mega cannot be found in the Species list";
			if(poké.hasMega())
			{
				Mega megaPoké = (Mega) poké ;
				megaPoké.addNewMega(spec) ;
				aSpeciesList.remove(poké) ;
				aSpeciesList.add(megaPoké) ;
			}
			else
			{
				//TODO: FIX THAT CONSTRUCTOR OR ADD A NEW ONE!!!!!!!!!
				Mega megaPoké = new Mega(poké.getPokédexNumber(), poké.getRegionalNumber(), poké.getName(),poké.getGeneration(), poké.getType1(), poké.getType2(), poké.getPreEvolutionName(), poké.getStats(), poké.getStringAbilities() ) ;
				
				megaPoké.addNewMega(spec) ;
				aSpeciesList.remove(poké) ;
				aSpeciesList.add(megaPoké) ;
			}
			
			
		}
	}
	
	private void loadFormes(String formesFile)
	{
		//TODO : Consider Arceus! Or will be taken care of in battle (using its ability).
		try
		{
			FileReader fr = new FileReader(formesFile) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ;
			
			while(currentLine!=null)
			{
				String[] currentInfo = currentLine.split(" ") ;
				
				if(currentInfo.length==12)
				{
					String name = currentInfo[0]+" "+currentInfo[1] ;
					String type1 = currentInfo[2] ;
					String type2 = currentInfo[3] ;
					String ability = currentInfo[4] ;
					
					int hp = Integer.parseInt( currentInfo[6] ) ;
					int atk = Integer.parseInt( currentInfo[7] ) ;
					int def = Integer.parseInt( currentInfo[8] ) ;
					int spAtk = Integer.parseInt( currentInfo[9] ) ;
					int spDef = Integer.parseInt( currentInfo[10] ) ;
					int spd = Integer.parseInt( currentInfo[11] ) ;
					
					int baseStat = Integer.parseInt( currentInfo[5] ) ;
					
					int[] stats = {baseStat, hp, atk, def, spAtk, spDef, spd} ;
					
					if(type2.equals("-"))
					{
						type2 = "NONE" ; 
					}
					
					Species currentForme = new Species(name, type1, type2, stats) ;
					
					//TODO:Keep abilities stored
					currentForme.setStringAbility(ability);
			
					aFormesTable.put(currentForme.getName(), currentForme) ;
				}
				if(currentInfo.length==13)
				{
					String name = currentInfo[0]+" "+currentInfo[1] ;
					String type1 = currentInfo[2] ;
					String type2 = currentInfo[3] ;
					String ability = currentInfo[4]+" "+currentInfo[5] ;
					
					int hp = Integer.parseInt( currentInfo[7] ) ;
					int atk = Integer.parseInt( currentInfo[8] ) ;
					int def = Integer.parseInt( currentInfo[9] ) ;
					int spAtk = Integer.parseInt( currentInfo[10] ) ;
					int spDef = Integer.parseInt( currentInfo[11] ) ;
					int spd = Integer.parseInt( currentInfo[12] ) ;
					
					int baseStat = Integer.parseInt( currentInfo[6] ) ;
					
					int[] stats = {baseStat, hp, atk, def, spAtk, spDef, spd} ;
					
					if(type2.equals("-"))
					{
						type2 = "NONE" ; 
					}
					
					//Types!
					
					Species currentForme = new Species(name, type1, type2, stats) ;
					//TODO:Keep ability stored.
					currentForme.setStringAbility(ability);
					aFormesTable.put(currentForme.getName(), currentForme) ;
				}
				
				
				
				currentLine = br.readLine() ;
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("Where are the Formes? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+formesFile ) ;
			System.out.println(e) ;
		}
		
	}
	
	private void setFormes()
	{
		
		for(Species spec : aFormesTable.values() )
		{
			String searchName = spec.getName().split(" ")[0] ;
			Species poké = find(searchName,true) ;
			
			assert poké!=null : searchName+" Forme cannot be found.";
			
			if(poké.hasFormes())
			{
				MultiForme formPoké = (MultiForme) poké ;
				formPoké.addNewForme(spec) ;
				aSpeciesList.remove(poké) ;
				aSpeciesList.add(formPoké) ;
			}
			else
			{
				//TODO (2015/2016):ADD NEW FIELDS!!!!!!!!!!!!!!!!! 2020 me: What?
				MultiForme formPoké = new MultiForme(poké.getPokédexNumber(), poké.getRegionalNumber(), poké.getName(),poké.getGeneration(), poké.getType1(), poké.getType2(), poké.getPreEvolutionName(), poké.getStats(), poké.getStringAbilities() ) ;
				formPoké.addNewForme(spec);
				aSpeciesList.remove(poké) ;
				aSpeciesList.add(formPoké) ;
			}
			
		}
	}
	
	private void loadBattleSwappers(String bsFile)
	{
		try
		{
			FileReader fr = new FileReader(bsFile) ;
			BufferedReader br = new BufferedReader(fr) ;
			String currentLine = br.readLine() ;
			
			while(currentLine != null)
			{
				String[] currentInfo = currentLine.split(";") ;
				String name = currentInfo[0] ;
				String type1 = currentInfo[1] ;
				String type2 = currentInfo[2] ;
				
				if(type2.equals("-"))
				{
					type2 = "NONE" ;
				}
				
				int baseStat = Integer.parseInt(currentInfo[3]) ;
				int hp = Integer.parseInt(currentInfo[4]) ;
				int atk = Integer.parseInt(currentInfo[5]) ;
				int def = Integer.parseInt(currentInfo[6]) ;
				int spAtk = Integer.parseInt(currentInfo[7]) ;
				int spDef = Integer.parseInt(currentInfo[8]) ;
				int spd = Integer.parseInt(currentInfo[9]) ;
				
				int[] stats = {baseStat, hp, atk, def, spAtk, spDef, spd} ;
				
				Species spec = new Species(name, type1, type2, stats) ;
				aBattleSwappersTable.put(name, spec) ;
				currentLine = br.readLine() ;
			}
		}
		catch(IOException e)
		{
			System.out.println("Where are the swappers? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+bsFile) ;
			System.out.println(e) ;
		}
		
	}
	
	private void setBattleSwappers()
	{
		for(Species spec : aBattleSwappersTable.values() )
		{
			String searchName = spec.getName().split(" ")[0] ;
			Species poké = find(searchName, true) ;
			
			//I don't think I need it right now.
			
			//spec.setStringAbility( poké.getStringAbilities() ) ;
			
			if(poké.battleSwaps())
			{
				BattleSwapper formPoké = (BattleSwapper) poké ;
				formPoké.addNewForme(spec) ;
				aSpeciesList.remove(poké) ;
				aSpeciesList.add(formPoké) ;
			}
			else
			{
				BattleSwapper formPoké = new BattleSwapper(poké.getPokédexNumber(), poké.getRegionalNumber(), poké.getName(),poké.getGeneration(), poké.getType1(), poké.getType2(), poké.getPreEvolutionName(), poké.getStats(), poké.getStringAbilities() );
				formPoké.addNewForme(spec);
				aSpeciesList.remove(poké) ;
				aSpeciesList.add(formPoké) ;
			}
			
		}
	}
	
	private void attachEVs(String evFile)
	{
		sortByPokedexNumber();
		try
		{
			FileReader fr = new FileReader(evFile) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine();
			String before = "" ;
			while(currentLine != null)
			{
				String[] split = currentLine.split(";");
				
				try
				{	
					findByNumber(Integer.parseInt(split[0].trim()), false).setEVs(split[split.length-1]);
				}
				catch(NumberFormatException f)
				{
					assert before.contains("721") : "The EVs run should be done at 721!" ;
				}
				before = currentLine ;
				currentLine = br.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("Where are the evs? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+evFile) ;
			System.out.println(e) ;
		}
	}
	
	private void attachFlavorTexts(String ftFile)
	{
		try
		{
			FileReader fr = new FileReader(ftFile);
			BufferedReader br = new BufferedReader(fr);
			
			String currentLine = br.readLine().replace("&acute;","e");
			String[] currentSplit = currentLine.split(";");
			
			int dexNum = Integer.parseInt(currentSplit[0]) ;
			String flavtext = currentSplit[1].replaceAll("\t", "") ;
			findByNumber(dexNum,true).setFlavorText(flavtext);
			currentLine = br.readLine().replaceAll("&acute;", "e");
			
			while(currentLine!=null)
			{
				String[] curSplit = currentLine.split(";");
				
				int dNum = Integer.parseInt(curSplit[0]) ;
				
				String ft = curSplit[1].replaceAll("\t", "") ;
				findByNumber(dNum, false).setFlavorText(ft);
				currentLine = br.readLine().replaceAll("&eacute;", "e");
			}
		}
		catch(IOException e)
		{
			System.out.println("Where are the flavor texts? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+ftFile) ;
			System.out.println(e);
		}
		catch(NullPointerException e)
		{
			//System.out.println(e);
		}
		
	}
	
	private void setEggGroupsAndEvolutionMethods(String eggemFile)
	{
		assert aSpeciesList!=null : "The Species list must be initialized!" ;
		
		sortByPokedexNumber();
		try
		{
			FileReader fr = new FileReader(eggemFile) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine();
			
			while(currentLine!=null)
			{
				String[] split = currentLine.split(";");
				
				
				try
				{
					String[] evolsAndMeths = split[1].split(";");
					
					Species curSpec = findByNumber(Integer.parseInt( split[0]), false );
					
					curSpec.setEggGroups(split[split.length-2], split[split.length-1]) ;
					for(int i = split.length-3; i>0; i--)
					{
						String[] evols = split[i].split(":");
						if(evols.length == 2)
						{	
							curSpec.addEvolutionMethod(evols[0], evols[1]);
						}
					}
				}
				catch(NullPointerException f)
				{
					
					System.out.println(split[0]);
				}
				currentLine = br.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("Where are the egg groups and the evolution methods? Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+eggemFile) ;
			System.out.println(e) ;
		}
	}
	
	/*
	 * find() implements binary search on aSpeciesList using the Species' name field.
	 * find() ignores upper and lower case.
	 */
	public Species find(String pName, boolean shouldSort)
	{	
		if(shouldSort)
		{
			sortByName() ;
		}
		int min = 0 ;
		int max = aSpeciesList.size()-1 ;
		
		while(max>=min)
		{
			int mid = (min+max)/2 ;
			
			if(0 == pName.toLowerCase().compareTo(aSpeciesList.get(mid).getName().toLowerCase()) || pName.toLowerCase().equals(aSpeciesList.get(mid).getName().toLowerCase()))
			{
				return aSpeciesList.get(mid) ;
			}
			if(0 < pName.toLowerCase().compareTo(aSpeciesList.get(mid).getName().toLowerCase()))
			{
				min=mid+1 ;
			}
			if(0 > pName.toLowerCase().compareTo(aSpeciesList.get(mid).getName().toLowerCase()))
			{
				max=mid-1 ;
			}
		}
		return null ;
	}
	
	public Species findByNumber(int pNumber, boolean shouldSort) throws IndexOutOfBoundsException
	{
		//TODO: Fix shifting problem: when you go past, for example, Wormadam, the indexes are off.
		//You could build a binary search for the PKMNList.
		//TODO: Once above is fixed, allow for special cases (Wormadam, Meowstic). 
		//Should be earlier in the code.
		if(shouldSort)
		{	
			sortByPokedexNumber() ;
		}
		
		int min = 0 ;
		int max = aSpeciesList.size() ;
		
		while( max >= min )
		{
			int mid = (min+max)/2 ;
			if(aSpeciesList.get(mid).getPokédexNumber() == pNumber)
			{
				return aSpeciesList.get(mid) ;
			}
			if(aSpeciesList.get(mid).getPokédexNumber()>pNumber)
			{
				max = mid-1 ;
			}
			if(aSpeciesList.get(mid).getPokédexNumber()<pNumber)
			{
				min = mid+1 ;
			}
		}
		return null ;
	}
	
	protected ArrayList<Species> getSpeciesList()
	{
		return aSpeciesList ;
	}

	public ArrayList<Species> getEvolutionSpecies(Species spec)
	{
		ArrayList<Species> result = new ArrayList<>() ;
		sortByName();
		for(String evol : spec.getEvolutions())
		{
			result.add(find(evol, false)) ;
		}
		result.sort(this);
		return result ;
	}
	
	public static void main(String[] args)
	{
		int x = 0;
	}
	
}


