package pokedex;

public class BattleSwapper extends MultiForme {
	public BattleSwapper(int pPok�dexNumber, String pName, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
	{
		super( pPok�dexNumber, pName, pType1, pType2, preEv, pStats, pAbilities ) ;
	}
	
	//v2 new constructor
	
	public BattleSwapper(int pok�dexNumber, int regionalNumber, String name,
			int generation, String type1, String type2,
			String preEvolutionName, int[] stats, String[] stringAbilities) {
		
		super(pok�dexNumber, regionalNumber, name, generation, type1, type2,
				preEvolutionName, stats, stringAbilities);
	}
}
