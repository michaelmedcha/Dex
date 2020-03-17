package pokedex_ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import pokedex.Pokedex;
import pokedex.Species;

public class Displayer extends JFrame {

	private Pokedex aPokedex ;
	private String pokeballFile ;
	private String iconFile ;
	//public SpeciesSearchWindow anSSW ;
	//public MoveSearchWindow anMSW ;
	//public AbilitySearchWindow anASW ;
	public ArrayList<String> favorites ;
	public ArrayList<String> history ;
	private String[] favHistFiles ;
	public String speciesFolder ;
	
	public Displayer(Pokedex pPokedex, String[] extraFiles ){
		
		aPokedex = pPokedex ;
		pokeballFile = extraFiles[0] ;
		iconFile = extraFiles[1] ;
		initialize() ;
		
		favHistFiles = new String[2] ;
		favHistFiles[0] = extraFiles[2] ;
		favHistFiles[1] = extraFiles[3] ;
		speciesFolder = extraFiles[4] ;
		loadFavorites(aPokedex.aBaseFolder+extraFiles[2]) ;
		loadHistory(aPokedex.aBaseFolder+extraFiles[3]) ;
		
	}
	
	private void initialize()
	{
		JPanel middle = new JPanel() ;
		middle.setLayout(new GridLayout(2,1)) ;
		
		
		BorderLayout bl = new BorderLayout() ;
		bl.setVgap(5);
		bl.setHgap(5);
		setLayout(bl) ;
		
		ImageIcon pokeball = new ImageIcon(aPokedex.aBaseFolder+pokeballFile) ;
		
		
		
		try
		{
			Image test = ImageIO.read(new File(aPokedex.aBaseFolder+iconFile)) ;
			
			setIconImage(test) ;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	
		
		
		JTextPane message = new JTextPane() ;
		message.setText("Welcome to the MMC-Pokedex 2.1.\nThe author would like to thank Pokémon Reborn's spriters : "
				+ "Will, Veenerick, chasemortier, Mektar (Pokemon Amethyst), JoshR691, kidkatt, ShinxLuver, Dr Shellos, Getsuei-H, Amethyst, Noscium, Quanyails,"
				+ " Zermonious, GeoIsEvil, Kyle Dove, dDialgaDiamondb, N-kin, Misterreno, Kevfin, Julian, Gardrow, FlameJow, Minhnerd, The Cynical Poet, Greyenna, Brylark, Lepargon, Princess Phoenix, Gnomowladn, Bryancct, Tinivi.\nThe copyrights on Pokémon-related content belong to Game Freak, The Pokémon Company and Nintendo. This software is fan-made.\nThe author wishes you fun gaming.");
		
		/*
		--Sprites:
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
--Character/Game/World:
Amethyst		
--Misc:
Will		Veenerick		chasemortier	Mektar (Pokemon Amethyst)	
JoshR691	kidkatt			ShinxLuver		Dr Shellos		Getsuei-H
--6th gen sprites:
Amethyst	Noscium			Quanyails		Zermonious		GeoIsEvil		
Kyle Dove	dDialgaDiamondb		N-kin		Misterreno		Kevfin
Xtreme1992	Vale98PM
--Mega Sprites: 
Julian		Amethyst		Gardrow			FlameJow		Minhnerd	
The Cynical Poet			Greyenna		Brylark			Lepargon	
Princess Phoenix			Gnomowladn		Bryancct		Tinivi
		-------------------*/
		
	/*The author would like to thank Pokémon Reborn's spriters : Will, Veenerick, chasemortier, Mektar (Pokemon Amethyst), JoshR691, kidkatt, ShinxLuver, Dr Shellos, Getsuei-H,
	 *Amethyst, Noscium, Quanyails, Zermonious, GeoIsEvil, Kyle Dove, dDialgaDiamondb, N-kin, Misterreno, Kevfin,
	 *Julian, Gardrow, FlameJow, Minhnerd, The Cynical Poet, Greyenna, Brylark, Lepargon, Princess Phoenix, Gnomowladn, Bryancct, Tinivi. 
	 */
		message.setSize(200, 180) ;
		message.setEditable(false);
		middle.add(message, BorderLayout.CENTER) ;
		
		JPanel p1 = new JPanel() ;
		JPanel p2 = new JPanel() ;
		
		p1.setLayout(new BorderLayout());
		p2.setLayout(new BorderLayout());
		
		JLabel l1 = new JLabel() ;
		JLabel l2 = new JLabel() ;
		l1.setIcon(pokeball);
		l2.setIcon(pokeball);
		
		p1.add(l1, BorderLayout.NORTH) ;
		p1.add(l2,BorderLayout.SOUTH) ;
		
		JLabel l12 = new JLabel() ;
		JLabel l22 = new JLabel() ;
		l12.setIcon(pokeball);
		l22.setIcon(pokeball);
		
		p2.add(l12, BorderLayout.NORTH) ;
		p2.add(l22,BorderLayout.SOUTH) ;
		
		
		
		JPanel buttons = new JPanel() ;
		
		GridLayout gl = new GridLayout(3,2) ;
		
		gl.setHgap(5);
		gl.setVgap(5);
		
		buttons.setLayout(gl) ;
		
		JButton sDex = new JButton("Species Dex") ;
		JButton fav = new JButton("Favorites") ;
		JButton mDex = new JButton("Move Dex") ;
		JButton hist = new JButton("History") ;
		JButton aDex = new JButton("Ability Dex") ;
		JButton exit = new JButton("Exit") ;
		
		Displayer inActionListener = this ;
		
		sDex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EventQueue.invokeLater(new Runnable(){
					
					@Override
					public void run(){
						
						SpeciesSearchWindow anSSW = new SpeciesSearchWindow(aPokedex, inActionListener) ;
						inActionListener.setVisible(false);
						anSSW.setVisible(true);
					}
					
				});
			}
			
		});
		
		fav.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run()
					{
						FavoritesWindow fw = new FavoritesWindow(aPokedex, inActionListener) ;
						inActionListener.setVisible(false);
						fw.setVisible(true);
					}
					
				});
				
				
			}
		});
		
		mDex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//inActionListener.setVisible(false);
				EventQueue.invokeLater(new Runnable(){
				
					@Override
					public void run(){
						
						MoveSearchWindow anMSW = new MoveSearchWindow(aPokedex, inActionListener) ;
						inActionListener.setVisible(false);
						anMSW.setVisible(true) ;
					}
					
				});
				
			}
			
		});
		
		hist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run()
					{
						HistoryWindow fw = new HistoryWindow(aPokedex, inActionListener) ;
						inActionListener.setVisible(false);
						fw.setVisible(true);
					}
					
				});
				
				
			}
		});
		
		aDex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//inActionListener.setVisible(false);
				EventQueue.invokeLater(new Runnable(){
					
					@Override
					public void run(){
						AbilitySearchWindow anASW = new AbilitySearchWindow(aPokedex, inActionListener) ;
						inActionListener.setVisible(false);
						anASW.setVisible(true);
					}
					
				});
			}
			
		});
		
		exit.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveFavoritesAndHistory() ;
				
				System.exit(0); 
				
			}
			
		}) ;
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				saveFavoritesAndHistory() ;
				
				System.exit(0); 
			}
			
		}) ;
		
		buttons.add(sDex) ;
		buttons.add(fav) ;
		buttons.add(mDex) ;
		buttons.add(hist) ;
		buttons.add(aDex) ;
		buttons.add(exit) ;
		
		middle.add(buttons, BorderLayout.SOUTH) ;
		
		add(p1,BorderLayout.WEST) ;
		add(p2, BorderLayout.EAST) ;
		add(middle, BorderLayout.CENTER) ;
		
		pack() ;
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
		setResizable(false) ;
		setSize(500,400) ;
		setTitle("MMC Pokédex 2.1") ;
	}
	
	private void loadFavorites(String fn)
	{
		try
		{
			favorites = new ArrayList<>() ;
			FileReader fr = new FileReader(fn) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ;
			
			while(currentLine!=null)
			{
				favorites.add(currentLine) ;
				currentLine = br.readLine() ;
			}
		}
		catch(IOException e)
		{
			System.out.println("The favorites file cannot be found. Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+fn) ;
		}
	}
	
	public String addToFavorites(String name)
	{
		
		Species spec = aPokedex.getSpeciesFinder().bestGuess(name) ;
		
		if(!favorites.contains(spec.getName()))
		{
			favorites.add(spec.getName()) ;
			return spec.getName();
		}
		
		return null ;
	}
	
	private void loadHistory(String fn)
	{
		try
		{
			history = new ArrayList<>() ;
			FileReader fr = new FileReader(fn) ;
			BufferedReader br = new BufferedReader(fr) ;
			
			String currentLine = br.readLine() ;
			
			while(currentLine!=null)
			{
				history.add(currentLine) ;
				currentLine = br.readLine() ;
			}
		}
		catch(IOException e)
		{
			System.out.println("The history file cannot be found. Current folder is "+System.getProperty("user.dir")+".\n"
					+ "The file name used was "+fn) ;
		}
	}
	
	private void saveFavoritesAndHistory()
	{
		String favName = aPokedex.aBaseFolder+favHistFiles[0] ;
		String histName = aPokedex.aBaseFolder+favHistFiles[1] ;
		
		try
		{
			FileWriter fw = new FileWriter(favName) ;
			BufferedWriter bw = new BufferedWriter(fw) ;
			
			for(String s : favorites)
			{
				bw.write(s);
				bw.newLine();
			}
			
			
			bw.close();
		}
		catch(IOException e)
		{
			System.out.println("Favorites don't want to save.") ;
		}
		
		try
		{
			FileWriter fw = new FileWriter(histName) ;
			BufferedWriter bw = new BufferedWriter(fw) ;
			
			for(String s : history)
			{
				bw.write(s);
				bw.newLine();
			}
			
			bw.close();
		}
		catch(IOException e)
		{
			System.out.println("History does not want to save.") ;
		}
	}
	
	public String getIconFile()
	{
		return iconFile.concat("") ;
	}
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis() ;
		String baseFolder = "./data/"  ;
		Pokedex px = new Pokedex(baseFolder,baseFolder+"all_moves.txt",baseFolder+"all_abilities.csv", baseFolder+"type_matchup.csv",baseFolder+"name_types_abilities_stats.txt", baseFolder+"Mega File.csv", baseFolder+"Formes2.csv", baseFolder+"BattleSwappers.csv", baseFolder+"flavor_texts.txt", baseFolder+"evolution_method_and_egg_groups.txt", baseFolder+"egg_group_evol_EVs.txt") ;	
		long mid = System.currentTimeMillis() ;
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				String[] extraFiles = {"pokeball_symbol.png","blue shirt Dragonite.png", "Favorites.csv", "History.csv", "speciesImagesAndMegas/"} ;
				Displayer d = new Displayer(px, extraFiles) ;
				d.setVisible(true) ;
			}
			
		});
		
		long end = System.currentTimeMillis() ;
		System.out.println("Pokédex : "+((double)(mid-start))/1000.0) ;
		System.out.println("Displayer : "+((double)(end-mid))/1000.0) ;
	}

}
