package pokedex;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import pokedex.MoveBox.MoveNotFoundException;





public class Species implements Comparable<Species>, Comparator<Species> {
	
	private String aType1 = "NONE" ;
	private String aType2 = "NONE" ;
	private String aName = "" ;
	private int aPokédexNumber = 0 ;
	private int aRegionalNumber = 0 ;
	private int[] aStatsArray ;
	private boolean canEvolve = false ;
	private boolean isAnEvolution = false ;
	private String[] stringAbilities = new String[3] ;
	private String aPreEvolution = null ;
	private LinkedList<String> aListOfEvolutions = new LinkedList<>() ;
	private int aGeneration = 0 ;
	public static final String[] TYPES = {"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying", "Ghost", "Grass", "Ground", "Ice", "NONE", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"} ;
	
	//New fields from v2
	private Hashtable<String, Integer>  aMoveTableXY ;
	private Hashtable<String, Integer> aMoveTableORAS;
	private Hashtable<String, Integer> anEVTable ;
	private String aFlavorText;
	private String aFirstEggGroup;
	private String aSecondEggGroup;
	private Hashtable<String,String> anEvolutionTable ; //(evolution, method) pairs
	private Ability[] abilities ;
	private String imageFile ;
	
	/* v2's main constructor from v1's data.
	 * 
	 */	
		public Species(int pDexNum, int regDexNum, String pName, int gen, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
		{
			Arrays.sort(TYPES) ;
			assert pDexNum>0 ; 
			assert pDexNum<722 ; 
			assert pStats.length == 7 ;
			assert Arrays.binarySearch(TYPES, pType1)>=0 ; 
			assert Arrays.binarySearch(TYPES, pType2)>=0 ;
			assert pAbilities.length==3 ;
			
			if(!preEv.equals("--"))
			{
				isAnEvolution = true ;
			}
			
			aName = pName ;
			aPokédexNumber = pDexNum ;
			aType1 = pType1 ;
			aType2 = pType2 ;
			//assignGenerationNumber() ;
			aGeneration  = gen ;
			//setRegionalNumber() ;
			aRegionalNumber = regDexNum ;
			aStatsArray = pStats ;
			stringAbilities = pAbilities ;
			aPreEvolution = preEv ;
			abilities = new Ability[3] ;
			
			imageFile = "Battlers/"+aPokédexNumber;
			
			//Initialized:
			anEvolutionTable = new Hashtable<>();
			
		}
		
	public Species()
	{
		
	}
	
	/*
	 * Constructor for Mega
	 */
	//TODO:Complete constructor.
	public Species(String name, int dexNumber, String type1, String type2, int[] stats)
	{
		aName = name;
		aPokédexNumber = dexNumber;
		aType1 = type1;
		aType2 = type2;
		aStatsArray = stats.clone();
	}
	
	
	/*
	 * Constructor for Formes
	 */
	//TODO:Complete constructor.
	public Species(String name, String type1, String type2, int[] stats)
	{
		aName = name ;
		aType1 = type1;
		aType2 = type2;
		aStatsArray = stats.clone();
	}
	
	
	/*
	 * Constructor for future Megas.
	 */
	
	public Species(int pPokédexNumber, String pName, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
	{
		Arrays.sort(TYPES) ;
		assert pPokédexNumber>0 ; 
		assert pPokédexNumber<722 ; 
		assert pStats.length == 7 ;
		assert Arrays.binarySearch(TYPES, pType1)>=0 ; 
		assert Arrays.binarySearch(TYPES, pType2)>=0 ;
		assert pAbilities.length==3 ;
		
		if(!preEv.equals("--"))
		{
			isAnEvolution = true ;
		}
		
		
		aName = pName ;
		aPokédexNumber = pPokédexNumber ;
		aType1 = pType1 ;
		aType2 = pType2 ;
		assignGenerationNumber() ;
		setRegionalNumber() ;
		aStatsArray = pStats ;
		stringAbilities = pAbilities ;
		aPreEvolution = preEv ;
	}

	public void setType(String pType)
	{
		assert aType1.equals("NONE") ;
		aType1 = pType ;
	}
	
	public void setType(String pType1, String pType2)
	{
		assert aType1.equals("NONE") && aType2.equals("NONE") ;
		aType1 = pType1 ;
		aType2 = pType2 ;
	}
	
	public void setEvolves()
	{
		//assert canEvolve == false ;
		canEvolve = true ;
	}
	
	public void setIsAnEvolution()
	{
		assert isAnEvolution == false ;
		isAnEvolution = true ;
	}
	
	public void evolvesIn(String spec)
	{
		aListOfEvolutions.add(spec) ;
	}
	
	public void evolvesFrom(String spec)
	{
		assert aPreEvolution == null ;
		aPreEvolution = spec ;
	}
	
	@Deprecated
	/*
	 * The generation number has been computed and stored from version 1.
	 */
	public void assignGenerationNumber()
	{
		assert aPokédexNumber>0 && aPokédexNumber<722 ;
		if(aPokédexNumber<152)
		{
			aGeneration = 1 ;
		}
		else if(aPokédexNumber<252)
		{
			aGeneration = 2 ;
		}
		else if(aPokédexNumber<387)
		{
			aGeneration = 3 ;
		}
		else if(aPokédexNumber<494)
		{
			aGeneration = 4 ;
		}
		else if(aPokédexNumber<650)
		{
			aGeneration = 5 ;
		}
		else
		{
			aGeneration = 6 ;
		}
	}
	
	public void setRegionalNumber()
	{
		assert aGeneration>0 && aGeneration<7;
		assert aPokédexNumber>0 && aPokédexNumber<722 ;
		if(aGeneration == 1)
		{
			aRegionalNumber = aPokédexNumber ;
		}
		else if(aGeneration == 2)
		{
			aRegionalNumber = aPokédexNumber-151 ;
		}
		else if(aGeneration == 3)
		{
			aRegionalNumber = aPokédexNumber-251 ;
		}
		else if(aGeneration == 4)
		{
			aRegionalNumber = aPokédexNumber-386 ;
		}
		else if(aGeneration == 5)
		{
			aRegionalNumber = aPokédexNumber-493-1 ;
		}
		else
		{
			aRegionalNumber = aPokédexNumber-649 ;
		}
	}
	
	/*public boolean hasFormes()
	{
		if(this instanceof MultiForme)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean hasMega()
	{
		if(this instanceof Mega)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean battleSwaps()
	{
		if(this instanceof BattleSwapper)
		{
			return true ;
		}
		return false ;
	}*/
	
	public boolean resists(Type pType)
	{
		if(aType2.equals("NONE"))
		{
			return ! pType.isStrongAttacking(aType1) ;
		}
		else
		{
			double damageMultiplier = pType.findWeightToType(aType1)*pType.findWeightToType(aType2) ;
			return damageMultiplier < 1 ;
		}	
	}
	
	public boolean takesNeutralDamageFrom(Type pType)
	{
		if(aType2.equals("NONE"))
		{
			return pType.findWeightToType(aType1) == 1 ;
		}
		else
		{
			double damageMultiplier = pType.findWeightToType(aType1)*pType.findWeightToType(aType2) ;
			return damageMultiplier == 1 ;
		}
	}
	
	public boolean isWeakTo(Type pType)
	{
		if(aType2.equals("NONE"))
		{
			return pType.isStrongAttacking(aType1) ;
		}
		else
		{
			double damageMultiplier = pType.findWeightToType(aType1)*pType.findWeightToType(aType2) ;
			return damageMultiplier > 1 ;
		}	
	}
	
	public double damageMultiplierFrom(Type pType)
	{
		if(aType2.equals("NONE"))
		{
			return pType.findWeightToType(aType1) ;
		}
		else
		{
			double damageMultiplier = pType.findWeightToType(aType1)*pType.findWeightToType(aType2) ;
			return damageMultiplier ;
		}
	}
	
	public String typeMatchUps()
	{
		
		String result = "" ;
		TypeGraph tg = new TypeGraph() ;
		
		for(Type currentType : tg.getTypes())
		{
			double damageMultiplier = damageMultiplierFrom(currentType) ;
			String currentName = currentType.getTypeName() ;
			
			result = result+currentName+": "+damageMultiplier+". " ;
		}
		return result ;
	}
	
	public boolean evolves()
	{
		return canEvolve ;
	}
	
	public boolean getIsAnEvolution()
	{
		return isAnEvolution ;
	}
	
	public String getName()
	{
		return aName ;
	}
	
	public int[] getStats()
	{
		return aStatsArray.clone() ;
	}
	
	public int getPokédexNumber()
	{
		return aPokédexNumber ;
	}
	
	public int getRegionalNumber()
	{
		return aRegionalNumber ;
	}
	
	public String getType1()
	{
		return aType1 ;
	}
	
	public String getType2()
	{
		return aType2 ;
	}
	
	public String getPreEvolutionName()
	{
		return aPreEvolution ;
	}
	
	public String[] getStringAbilities()
	{
		return stringAbilities ; 
	}
	
	
	public LinkedList<String> getEvolutions()
	{
		return aListOfEvolutions ;
	}
	
	public Hashtable<String,String> getEvolutionMethods()
	{
		if(anEvolutionTable==null)
		{
			return null;
		}
		return ((Hashtable<String,String>) anEvolutionTable.clone());
	}
	
	public Ability[] getAbilities()
	{
		return abilities;
	}
	
	public String[] getEggGroups()
	{
		String[] egs = {aFirstEggGroup,aSecondEggGroup};
		return egs ;
	}
	
	public Hashtable<String,Integer> getEVs()
	{
		if(anEVTable == null)
		{
			return null ;
		}
		return ((Hashtable<String, Integer>)anEVTable.clone());
	}
	
	public String getFlavorText()
	{
		return aFlavorText ;
	}
	
	public ArrayList<String>  getMovesNamesXY()
	{
		ArrayList<String> result = new ArrayList<>() ;
		for(Enumeration<String>  enumKeys = aMoveTableXY.keys(); enumKeys.hasMoreElements();)
		{
			result.add(enumKeys.nextElement()) ;
		}
		return result ;
	}
	
	public ArrayList<String>  getMovesNamesORAS()
	{
		ArrayList<String> result = new ArrayList<>() ;
		for(Enumeration<String>  enumKeys = aMoveTableORAS.keys(); enumKeys.hasMoreElements();)
		{
			result.add(enumKeys.nextElement()) ;
		}
		return result ;
	}
	
	public Hashtable<String,Integer> getMoveNameLevelPairsXY()
	{
		return aMoveTableXY ;
	}
	
	public Hashtable<String,Integer> getMoveNameLevelPairsORAS()
	{
		return aMoveTableORAS ;
	}
	
	public ArrayList<Move> getMovesXY(MoveBox mb)
	{
		ArrayList<Move> result = new ArrayList<>() ;
		String currentMoveName = "" ;
		try
		{
			for(Enumeration<String>  enumKeys = aMoveTableXY.keys(); enumKeys.hasMoreElements();)
			{
				currentMoveName = enumKeys.nextElement();
				
				/*if(currentMoveName.charAt(0) == '-')
				{
					currentMoveName = cutToFirstUpperCase(currentMoveName) ;
				}*/
				
				result.add(mb.getMove( currentMoveName ) ) ;
			}
		}
		catch(MoveNotFoundException e)
		{
			System.out.println("Where is "+currentMoveName+" in XY? for "+aName+", number "+aPokédexNumber+"." ) ;
			e.printStackTrace();
		}
		return result ;
	}
	
	public static String cutToFirstUpperCase(String s)
	{
		assert s.charAt(0) == '-' : s+" does not start with -. cutToFirstUpperCase() is restricted to such cases." ;
		
		int newFirst = 0 ;
		
		for(int i = 1; i<s.length(); i++)
		{
			if(s.charAt(i)<91)
			{
				newFirst = i ;
				//System.out.println(s.charAt(i)) ;
				break ;
			}
		}
		
		return s.substring(newFirst, s.length()) ;
	}
	
	public ArrayList<Move> getMovesORAS(MoveBox mb)
	{
		ArrayList<Move> result = new ArrayList<>() ;
		String currentMoveName = "" ;
		try
		{
			for(Enumeration<String>  enumKeys = aMoveTableORAS.keys(); enumKeys.hasMoreElements();)
			{
				currentMoveName = enumKeys.nextElement();
				result.add(mb.getMove( currentMoveName ) ) ;
			}
		}
		catch(MoveNotFoundException e)
		{
			System.out.println("Where is "+currentMoveName+" in ORAS? For "+aName+", number "+aPokédexNumber+"." ) ;
			e.printStackTrace();
		}
		return result ;
	}
	
	public boolean hasMoveXY(String moveName, MoveBox mb) throws MoveNotFoundException
	{
		return getMovesXY(mb).contains(mb.getMove(moveName)) ;
	}
	
	public boolean hasMoveORAS(String moveName, MoveBox mb) throws MoveNotFoundException
	{
		return getMovesORAS(mb).contains(mb.getMove(moveName)) ;
	}
	
	public boolean hasMove(String moveName, MoveBox mb) throws MoveNotFoundException
	{
		return hasMoveXY(moveName,mb) || hasMoveORAS(moveName, mb) ;
	}
	
	@Override 
	public int compareTo(Species pokem)
	{
		return getName().compareTo(pokem.getName()) ;
	}
	
	@Override
	public int compare(Species spec1, Species spec2)
	{
		return spec1.compareTo(spec2) ;
	}
	
	@Override
	public boolean equals(Object spec)
	{
		if(spec == null)
		{
			return false ;
		}
		if(spec == this)
		{
			return true ;
		}
		if(this.getClass() != spec.getClass())
		{
			return false ;
		}
		return aName.equals( ((Species)spec).getName() ) ;
	}
	
	@Override
	public int hashCode()
	{
		return aName.hashCode() ;
	}
	
	
	public boolean hasFormes()
	{
		if(this instanceof MultiForme)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean hasMega()
	{
		if(this instanceof Mega)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean battleSwaps()
	{
		if(this instanceof BattleSwapper)
		{
			return true ;
		}
		return false ;
	}
	
	public void setStringAbility(String pAbility)
	{
		assert stringAbilities[2]==null ;
		if(stringAbilities[0]==null)
		{
			stringAbilities[0] = pAbility ;
		}
		else if(stringAbilities[1]==null)
		{
			stringAbilities[1] = pAbility ;
		}
		else if(abilities[2]==null)
		{
			stringAbilities[2] = pAbility ;
		}
	}
	
	//new from v2
	
	//TODO: check for null in setAbilities().
	
	public void setAbilities(AbilityBox ab)
	{
		Ability a1 = ab.getAbility(stringAbilities[0]) ;
		Ability a2 = ab.getAbility(stringAbilities[1]) ;
		Ability ha = ab.getAbility(stringAbilities[2]) ;
		
		abilities[0] = a1 ;
		abilities[1] = a2 ;
		abilities[2] = ha ;
	}
	
	public void setMoves(String moveFolder)
	{
		aMoveTableXY = new Hashtable<>() ;
		aMoveTableORAS = new Hashtable<>() ;
		
		if(aPokédexNumber<10)
		{
			try
			{
				FileReader fr = new FileReader(moveFolder+"moves_by_pokemon/move_c00"+aPokédexNumber+".txt") ;
				BufferedReader br = new BufferedReader(fr); 
				
				String currentLine = br.readLine() ;
				
				while(currentLine!=null)
				{
					String[] split = currentLine.split(";");
					assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					
					if(split[1].charAt(0) == '-')
					{
						split[1] = Species.cutToFirstUpperCase(split[1]) ;
					}
					
					if(isIntegerParseable(split[0]))
					{
						aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
					}
					else
					{
						aMoveTableXY.put(split[1], 1) ;
					}
					currentLine = br.readLine() ;
				}
				
			}
			catch(IOException e)
			{
				System.out.println(getName()+"'s moves cannot be found.") ;
			}
		}
		else if (aPokédexNumber<100)
		{
			//System.out.println(aPokédexNumber) ;
			try
			{
				FileReader fr = new FileReader(moveFolder+"moves_by_pokemon/move_c0"+aPokédexNumber+".txt") ;
				BufferedReader br = new BufferedReader(fr);
				
				String currentLine = br.readLine() ;
				
				while(currentLine!=null)
				{
					String[] split = currentLine.split(";");
					assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					
					if(split[1].charAt(0) == '-')
					{
						split[1] = Species.cutToFirstUpperCase(split[1]) ;
					}
					
					if(isIntegerParseable(split[0]))
					{
						aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
					}
					else
					{
						aMoveTableXY.put(split[1], 1) ;
					}
					currentLine = br.readLine() ;
				}
			}
			catch(IOException e)
			{
				
				try 
				{
					FileReader fr = new FileReader(moveFolder+"moves_by_pokemon/move_xy_oras_c0"+aPokédexNumber+".txt") ;
					BufferedReader br = new BufferedReader(fr) ;
					
					String currentLine = br.readLine() ;
					String[] split = currentLine.split(";") ;
					assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					
					while(split[0].equals("--"))
					{
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						//System.out.println("while0") ;
						if(isIntegerParseable(split[0]))
						{
							aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableXY.put(split[1], 1) ;
						}
						currentLine = br.readLine() ;
						assert currentLine !=null : aName+" has a null problem."+aPokédexNumber ;
						split = currentLine.split(";") ;
						assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					}
					
					while(!split[0].equals("--"))
					{
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						if(isIntegerParseable(split[0]))
						{
							aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableXY.put(split[1], 1) ;
						}
						currentLine = br.readLine() ;
						split = currentLine.split(";") ;
						assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					}
					
					if(split[1].charAt(0) == '-')
					{
						split[1] = Species.cutToFirstUpperCase(split[1]) ;
					}
					
					if(isIntegerParseable(split[0]))
					{
						aMoveTableORAS.put(split[1], Integer.parseInt(split[0])) ;
					}
					else
					{
						aMoveTableORAS.put(split[1], 1) ;
					}
					
					currentLine = br.readLine() ;
					
					while(currentLine!=null)
					{
						split = currentLine.split(";") ;
						assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
						
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						if(isIntegerParseable(split[0]))
						{
							aMoveTableORAS.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableORAS.put(split[1], 1) ;
						}
						currentLine = br.readLine() ;
					}
					
				} 
				catch (IOException e1) 
				{
					System.out.println(aName+"'s moves cannot be found.") ;
					e1.printStackTrace();
				}
			}
		}
		else
		{
			try
			{
				FileReader fr = new FileReader(moveFolder+"moves_by_pokemon/move_c"+aPokédexNumber+".txt") ;
				BufferedReader br = new BufferedReader(fr); 
				
				String currentLine = br.readLine() ;
				
				while(currentLine!=null)
				{
					String[] split = currentLine.split(";");
					
					try
					{
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						if(isIntegerParseable(split[0]))
						{
							aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableXY.put(split[1], 1) ;
						}
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						System.out.println(currentLine+" The Pokémon is "+aName+" "+aPokédexNumber) ;
						e.printStackTrace();
					}
					currentLine = br.readLine() ;
				}
			}
			catch(IOException e)
			{
				try 
				{
					FileReader fr = new FileReader(moveFolder+"moves_by_pokemon/move_xy_oras_c"+aPokédexNumber+".txt") ;
					BufferedReader br = new BufferedReader(fr) ;
					
					String currentLine = br.readLine() ;
					String[] split = currentLine.split(";") ;
					assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					
					while(split[0].equals("--"))
					{
						//System.out.println("while0") ;
						
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						if(isIntegerParseable(split[0]))
						{
							aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableXY.put(split[1], 1) ;
						}
						currentLine = br.readLine() ;
						
						try
						{
							//assert currentLine !=null : aName+" has a null problem."+aPokédexNumber ;
							boolean b = currentLine.equals("") ;
						}
						catch(NullPointerException er)
						{
							assert aName.equals("Mismagius") : aName + " for weird move file should be Mismagius";
							
								aMoveTableORAS = (Hashtable<String,Integer>) aMoveTableXY.clone() ;
								aMoveTableORAS.remove("Power Gem") ;
								aMoveTableXY.remove("Mystical Fire") ;
								aMoveTableORAS.put("Mystical Fire", 1) ;
							
							//cleanDoubleEdge() ;
							return ;
						}
						try
						{split = currentLine.split(";") ;
						
						}
						catch(NullPointerException ex)
						{
							System.out.println(aName+":"+currentLine) ;
						}
						assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					}
					
					while(!split[0].equals("--"))
					{
						
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						if(isIntegerParseable(split[0]))
						{
							aMoveTableXY.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableXY.put(split[1], 1) ;
						}
						currentLine = br.readLine() ;
						split = currentLine.split(";") ;
						assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
					}
					
					if(split[1].charAt(0) == '-')
					{
						split[1] = Species.cutToFirstUpperCase(split[1]) ;
					}
					
					if(isIntegerParseable(split[0]))
					{
						aMoveTableORAS.put(split[1], Integer.parseInt(split[0])) ;
					}
					else
					{
						aMoveTableORAS.put(split[1], 1) ;
					}
					
					currentLine = br.readLine() ;
					
					while(currentLine!=null)
					{
						split = currentLine.split(";") ;
						assert split.length == 2 : aName+"'s "+currentLine+" does not split right." ;
						
						if(split[1].charAt(0) == '-')
						{
							split[1] = Species.cutToFirstUpperCase(split[1]) ;
						}
						
						if(isIntegerParseable(split[0]))
						{
							aMoveTableORAS.put(split[1], Integer.parseInt(split[0])) ;
						}
						else
						{
							aMoveTableORAS.put(split[1], 1) ;
						}
						currentLine = br.readLine() ;
					}
					
				} 
				catch (IOException e1) 
				{
					System.out.println(aName+"'s moves cannot be found.") ;
					e1.printStackTrace();
				}
			}
		}
		
		if(aPokédexNumber == 681)
		{
			int level = aMoveTableXY.get("'sshieldKing's Shield") ;
			aMoveTableXY.remove("'sshieldKing's Shield") ;
			aMoveTableXY.put("King's Shield", level) ;
		}

	}
	
	public void addEvolutionMethod(String evolName, String method)
	{
		anEvolutionTable.put(evolName, method) ;
	}
	
	//TODO:FIX SPECIAL CASES LIKE DEOXYS, WORMADAM!!!!
	
	public void setEVs(String evs)
	{
		
		String[] splits = evs.split(" ") ;
		//splits[splits.length-1] = splits[splits.length-1].trim() ;
		Queue<Integer> vals  = new LinkedList<Integer>() ;
		Queue<String> stats = new LinkedList<String>() ;
		//String current = "" ;
		Hashtable<String, Integer> htOut = new Hashtable() ;
		
		if(aPokédexNumber == 386)
		{
			//System.out.println("DEOXYS!!!!!!!!!");
			htOut.put("Attack", 1);
			htOut.put("Sp. Attack", 1);
			htOut.put("Speed", 1);
			anEVTable=htOut;
			return;
		}
		
		if(aName.equals("Wormadam (Grass)"))
		{
			htOut.put("Sp. Defense", 2);
			anEVTable = htOut;
			return;
		}
		if(aName.equals("Wormadam (Ground)"))
		{
			htOut.put("Defense", 2);
			anEVTable = htOut;
			return;
		}
		if(aName.equals("Wormadam (Steel)"))
		{
			htOut.put("Defense", 1);
			htOut.put("Sp. Defense", 1);
			anEVTable = htOut;
			return;
		}
		for(int i =0 ; i<splits.length; i++)
		{
			if(isIntegerParseable(splits[i]))
			{
				vals.add(Integer.parseInt(splits[i])) ;
			}
			else if(splits[i].equals("Sp."))
			{

			}
			else if(splits[i].trim().equals("Attack") || splits[i].equals("Defense"))
			{
				if(splits[i-1].equals("Sp."))
				{
					stats.add("Sp. "+splits[i]) ; 
				}
				else
				{
					stats.add(splits[i]) ;
				}
			}
			else if(splits[i].equals("Speed") || splits[i].equals("HP"))
			{
				stats.add(splits[i]) ;
			}
			
		}
		
		
		
		assert vals.size() == stats.size() : aPokédexNumber+": the stats must match the effort values! vals has length "+vals.size()+" and stats has"
			+ " length "+stats.size()+"." ;
	
	
		while(!vals.isEmpty())
		{
			htOut.put(stats.poll(), vals.poll()) ;
		}
	
		int blue = 5 ;
		anEVTable = htOut ;
		
	}
	
	
	public void setEvolutionMethods(String toParse)
	{
		String[] split = toParse.split(":");
		assert split.length%2 ==0  || split.length == 1 : Arrays.toString(split) ;
		
		if(split.length==1)
		{
			return;
		}
		
		for(int i = 0; i<split.length; i = i + 2)
		{
			addEvolutionMethod(split[i], split[i+1]);
		}
	}
	
	private static boolean isIntegerParseable(String s)
	{
		try
		{
			int x = Integer.parseInt(s) ;
		}
		catch(NumberFormatException e)
		{
			return false ;
		}
		return true ;
	}
	
	public void setEggGroups(String g1, String g2)
	{
		aFirstEggGroup = g1 ;
		aSecondEggGroup = g2 ;
	}
	
	public void setFlavorText(String ft)
	{
		aFlavorText = ft ;
	}

	public int getGeneration() {
		return aGeneration;
	}
	
	public String toString()
	{
		return aName+":"+aPokédexNumber ;
	}
}
