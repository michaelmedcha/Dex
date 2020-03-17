package pokedex;

public class MoveLevelPair {
	
	private Move aMove ;
	private int aLevel ;
	
	public MoveLevelPair(Move pMove, int pLevel)
	{
		aMove = pMove ;
		aLevel = pLevel ;
	}
	
	public Move getMove()
	{
		return aMove ;
	}
	
	public int getLevel()
	{
		return aLevel ;
	}
	
}
