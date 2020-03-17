package pokedex;

import java.awt.Color;
import java.util.* ;

//import org.junit.* ;

public class Type implements Comparable<Type> {
	
	private final String aTypeName ;
	private ArrayList<TypeEdge> aListOfEdges = new ArrayList<>() ;
	private Color aColor ;
	public static final Color[] colors ={Color.getHSBColor((float) 0.4, (float) 1.0, (float) 0.99), Color.black, Color.getHSBColor((float) 0.55,(float) 0.9,(float) 0.5) , Color.getHSBColor((float) 0.145,(float) 1.0,(float) 1.0), Color.pink, Color.getHSBColor((float) 0.99, (float) 0.99, (float) 0.7), Color.getHSBColor((float) 0.08, (float) 1, (float) 1), Color.getHSBColor((float) 0.5, (float) 0.9, (float) 0.8), Color.getHSBColor((float) 0.8, (float) 0.9, (float) 0.4), Color.green, Color.getHSBColor((float) 0.1, (float) 0.9, (float) 0.8), 
			                            Color.cyan, null, Color.gray, Color.getHSBColor((float) 0.8, (float) 0.8, (float) 0.8), Color.magenta, Color.getHSBColor((float) 0.15, (float) 0.4, (float) 0.54), Color.DARK_GRAY, Color.blue}; 
	//"Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying", "Ghost", "Grass", "Ground", "Ice", "NONE", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println( Arrays.toString( Color.RGBtoHSB(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(), null) ) ) ;
		
	}
	
	
	
	public Type(String pName)
	{
		aTypeName = pName ;
		
		int loc = Arrays.binarySearch(Species.TYPES, pName) ;
		
		aColor = colors[loc] ;
	}
	
	public void addEdge(Type pType, double pWeight)
	{
		String[] TYPES = Species.TYPES ;
		Arrays.sort(TYPES) ;
		
		//assert Arrays.binarySearch(TYPES, pType.getTypeName()) >= 0 ;
		//assert pWeight>=0 && pWeight<=2 ;
		
		aListOfEdges.add(new TypeEdge(pType, pWeight)) ;
	}
	
	public String getTypeName()
	{
		return aTypeName ;
	}
	
	public ArrayList<TypeEdge> getEdges()
	{
		return  aListOfEdges ;
	}
	
	public String toString()
	{
		return aTypeName ;
	}
	
	@Override
	public int compareTo(Type pType)
	{
		return getTypeName().compareTo(pType.getTypeName()) ;
	}
	
	
	public int compare(TypeEdge e1, TypeEdge e2)
	{
		return e1.aType.getTypeName().compareTo(e2.aType.getTypeName())  ;
	}
	
	public Type findType(String pType)
	{
		for(TypeEdge te : aListOfEdges)
		{
			if(te.aType.getTypeName().equalsIgnoreCase(pType)) 
			{
				return te.aType ;
			}
		}
		//assert false ; //We should never reach this point.
		return null ;
	}
	
	//TODO: Debug findWeightToType.
	
	public double findWeightToType(String pType)
	{
	
		for(TypeEdge te : aListOfEdges)
		{
			if(te.getType().getTypeName().equalsIgnoreCase(pType)) 
			{	
				return te.aWeight ;
			}
		}
		//assert false ; //We should never reach this point.
		return -1 ;
	}
	
	public double findWeightToType(Type pType)
	{
		for(TypeEdge te : aListOfEdges)
		{
			if(te.aType.getTypeName().equals(pType.getTypeName())) 
			{
				return te.aWeight ;
			}
		}
		//assert false ;
		return -1 ;
	}
	
	public boolean isResistantTo(String pType)
	{
		if(findType(pType).findWeightToType(this)<1)
		{
			return true ;
		}
		
		
		return false ;
	}
	
	public boolean isResistantTo(Type pType)
	{
		if(pType.findWeightToType(this)<1)
		{
			return true ;
		}
		
		
		return false ;
	}
	
	public boolean isStrongAttacking(Type pType)
	{
		if(this.findWeightToType(pType)>1)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean isStrongAttacking(String pType)
	{
		if(this.findWeightToType(pType)>1)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean isImmuneto(String pType)
	{
		if(findType(pType).findWeightToType(this) == 0)
		{
			return true ;
		}
		return false ;
	}
	
	public boolean isImmuneto(Type pType)
	{
		if(pType.findWeightToType(this) == 0)
		{
			return true ;
		}
		return false ;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return aColor;
	}
	
	

}
