package pokedex;

public class Move {
	
	private String aName ;
	private Type aType ;
	private String aCategory ;
	private double aPower ;
	private double anAccuracy ;
	private int aPP ;
	private String aDescription ;
	private double anEffectRate ;
	
	public Move(String[] moveIn, TypeGraph G)
	{
		assert moveIn.length == 9 ;
		
		aName = moveIn[0] ;
		aType = G.findType(moveIn[1]) ;
		aCategory = moveIn[2] ;
		if(!moveIn[3].contains("-"))
		{
			aPower = Double.parseDouble(moveIn[3]) ;
		}
		else
		{
			aPower = 0.0 ;
		}
		
		if(moveIn[4].contains("?"))
		{
			anAccuracy = 1729.0 ;
		}
		else if(!moveIn[4].contains("-") )
		{
			
			anAccuracy = Double.parseDouble(moveIn[4]) ;
		}
		else
		{
			anAccuracy = 0.0 ;
		}
		
		//anAccuracy = Double.parseDouble(moveIn[4]) ;
		if(!moveIn[5].contains("-"))
		{
			aPP = Integer.parseInt(moveIn[5]) ;
		}
		else
		{		
			aPP = -1 ;
		}
		
		//aPP = Integer.parseInt(moveIn[5]) ;
		aDescription = moveIn[7] ;
		
		if(!moveIn[8].contains("-"))
		{
			anEffectRate = Double.parseDouble(moveIn[8]) ;
		}
		else
		{
			anEffectRate = 0 ;
		}
	}
	
	@Override
	public String toString()
	{
		return aName+":"+aDescription;
	}
	
	public String getName()
	{
		return aName ;
	}
	
	public Type getType()
	{
		return aType ;
	}
	
	public String getCategory()
	{
		return aCategory ;
	}
	
	public double getPower()
	{
		return aPower;
	}
	
	public int getPP()
	{
		return aPP ;
	}
	
	public String getDescription()
	{
		return aDescription ;
	}
	
	public double getEffectRate()
	{
		return anEffectRate ;
	}
	
	public double getAccuracy()
	{
		return anAccuracy ;
	}
}
