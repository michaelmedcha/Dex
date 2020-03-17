package pokedex;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingUtilities;
import pokedex.MoveBox.MoveNotFoundException;

public class Pokedex extends SpeciesBox{

	public final TypeGraph aTypeGraph;
	private final AbilityBox anAbilityBox;
	private final MoveBox aMoveBox;
	public final String aBaseFolder;
	public final SpeciesFinder aSpeciesFinder ;
	public final AbilityFinder anAbilityFinder ;
	public final MoveFinder aMoveFinder ;
	
	private boolean ready = false;
	
	public Pokedex(String baseFolder, String moveLoc, String abilityLoc, String typeLoc, String mainFile, String megaFile, String formeFile, String bsFile, String ftFile, String eggsEvolsFile, String EVsFile)
	{
		super( mainFile, megaFile, formeFile, bsFile, ftFile, eggsEvolsFile, EVsFile);
		aTypeGraph = new TypeGraph(typeLoc) ;
		aMoveBox = new MoveBox(moveLoc, typeLoc) ;
		anAbilityBox = new AbilityBox(abilityLoc) ;
		aBaseFolder = baseFolder ;
		assignAbilities() ;
		assignMoves() ;
		setEvolutions() ;
		aSpeciesFinder = new SpeciesFinder(this);
		anAbilityFinder = new AbilityFinder(this);
		aMoveFinder = new MoveFinder(this) ;
		ready = true;
	}
	
	public Move getMove(String pMoveName) throws MoveNotFoundException
	{
		return aMoveBox.getMove(pMoveName);
	}
	
	public Ability getAbility(String pAbilityName)
	{
		return anAbilityBox.getAbility(pAbilityName) ;
	}
	
	public Type getType(String pTypeName)
	{
		return aTypeGraph.findType(pTypeName) ;
	}
	
	public MoveBox getMoveBox()
	{
		return aMoveBox ;
	}
	
	public AbilityBox getAbilityBox()
	{
		return anAbilityBox ;
	}
	
	public SpeciesFinder getSpeciesFinder()
	{
		return aSpeciesFinder ;
	}
	
	public AbilityFinder getAbilityFinder()
	{
		return anAbilityFinder ;
	}
	
	public MoveFinder getMoveFinder()
	{
		return aMoveFinder ;
	}
	
	private void assignAbilities()
	{
		ArrayList<Species> aSpeciesList = getSpeciesList() ;
		
		for(Species spec : aSpeciesList)
		{
			spec.setAbilities(anAbilityBox);
		}
	}
	
	private void assignMoves()
	{
		ArrayList<Species> aSpeciesList = getSpeciesList() ;
		
		for(Species spec : aSpeciesList)
		{
			spec.setMoves(aBaseFolder);
		//	System.out.println(spec.getName()+"'s moves were set.");
			
		}
	}
	
	private void setEvolutions()
	{
		sortByName() ;
		for(Species spec : getSpeciesList())
		{
			try
			{
				if(! spec.getPreEvolutionName().equals("--") )
				{
					Species preEv = find(spec.getPreEvolutionName(), false);
					preEv.evolvesIn(spec.getName()) ;
					preEv.setEvolves() ;	
				}
			}
			catch(NullPointerException e)
			{
				System.out.println( spec.getName()+" "+spec.getPreEvolutionName() ) ;
			}
			
		}
	}
	
	public void dumpPokedex(String outputFolder){
		if(! ready){
			System.out.println("The Pokedex is not ready to be dumped.");
			return;
		}
		//Check if folder empty or does not exist (We want that).
		
		sortByPokedexNumber();
		/*
		-all species.
		-all megas.
		-all formes.
		-all battleSwappers.
		-all abilities.
		-all moves.
		*/
		try{
			throw new IOException();
		}
		catch(IOException e){
			System.out.println("Could not dump the Pokemon species.");
		}
	}
	
	public static void main(String[] args) {
		int x = 0;
	}
}
