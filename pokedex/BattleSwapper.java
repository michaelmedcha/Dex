package pokedex;

public class BattleSwapper extends MultiForme {
	public BattleSwapper(int pPokédexNumber, String pName, String pType1, String pType2, String preEv, int[] pStats, String[] pAbilities)
	{
		super( pPokédexNumber, pName, pType1, pType2, preEv, pStats, pAbilities ) ;
	}
	
	//v2 new constructor
	
	public BattleSwapper(int pokédexNumber, int regionalNumber, String name,
			int generation, String type1, String type2,
			String preEvolutionName, int[] stats, String[] stringAbilities) {
		
		super(pokédexNumber, regionalNumber, name, generation, type1, type2,
				preEvolutionName, stats, stringAbilities);
	}
}
