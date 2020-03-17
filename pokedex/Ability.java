package pokedex;

public class Ability {

	private final String aName ;
	private final String anEffect ;
	private final String aDetailedEffect;
	private final String anOverWorldEffect ;
	
	public Ability(String[] fields)
	{
		assert fields.length == 4 ;
		aName = fields[0].toLowerCase() ;
		anEffect = fields[1] ;
		aDetailedEffect = fields[2] ;
		anOverWorldEffect = fields[3] ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	public String getName()
	{
		return aName ;
	}
	
	public String getEffect()
	{
		return anEffect ;
	}
	
	public String getDetailedEffect()
	{
		return aDetailedEffect ;
	}
	
	public String getOverWorldEffect()
	{
		return anOverWorldEffect ;
	}
	
	public String[] getAll()
	{
		String[] out = {aName, anEffect, aDetailedEffect, anOverWorldEffect} ;
		return out ;
	}
	
	public String toString()
	{
		return aName;
	}
	
}
