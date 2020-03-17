package pokedex_ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import pokedex.*;

public class SpeciesWindow extends JFrame implements Comparator<MoveLevelPair> {

	Species aSpecies ;
	Pokedex aPokedex ;
	
	private JFrame typeMatchUps ;
	private JFrame moves ;
	private JFrame movesORAS ;
	
	private ArrayList<JFrame> moveDescriptions;
	private ArrayList<JFrame> moveDescriptionsORAS;
	
	SpeciesSearchWindow anSSW ;
	FavoritesWindow fw ;
	HistoryWindow aHW ;
	Displayer aDisplayer ;
	
	private JFrame mega1 ;
	private JFrame megaTMU1 ;
	private JFrame megaTMU2 ;
	
	private JFrame swap ;
	private JFrame[] swapTMUs ;
	
	private JFrame forme ;
	private JFrame[] formes ;
	
	private JFrame ability1 ;
	private JFrame ability2 ;
	private JFrame hAbility ;
	
	public SpeciesWindow(Species spec, Pokedex px)
	{
		aSpecies = spec ;
		aPokedex = px ;
		anSSW = null ;
		initialize();
		//displayEvolutionChain(aSpecies);	
		
		
	}
	
	public SpeciesWindow(Pokedex px, SpeciesSearchWindow ssw)
	{
		//aSpecies = spec ;
		aPokedex = px ;
		anSSW = ssw ;
		//initialize();
		//displayEvolutionChain(aSpecies);		
	}
	
	public SpeciesWindow(Pokedex px, SpeciesSearchWindow ssw, Species spec)
	{
		aSpecies = spec ;
		aPokedex = px ;
		anSSW = ssw ;
		
		if(anSSW != null)
		{	
			aDisplayer = anSSW.aDisplayer ;
		}
		
		initialize();
		//displayEvolutionChain(aSpecies);	
		
		
	}
	
	public SpeciesWindow(Pokedex px, FavoritesWindow ssw, Species spec)
	{
		aSpecies = spec ;
		aPokedex = px ;
		fw = ssw ;
		
		if(fw!=null)
		{	
			aDisplayer = fw.aDisplayer ;
		}
		initialize();
		//displayEvolutionChain(aSpecies);	
		
		
	}
	
	public SpeciesWindow(Pokedex px, HistoryWindow ssw, Species spec)
	{
		aSpecies = spec ;
		aPokedex = px ;
		aHW = ssw ;
		
		if(aHW!=null)
		{	
			aDisplayer = aHW.aDisplayer ;
		}
		
		initialize();
		//displayEvolutionChain(aSpecies);	
		
		
	}
	
	private final void initialize()
	{
		//this.removeAll();
		Image t2 = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB) ;
		
		typeMatchUps = new JFrame() ;
		typeMatchUps.add(typeMatchUps(aSpecies)) ;
		typeMatchUps.setLocation(400, 100);
		typeMatchUps.setSize(new Dimension(900,100));
		typeMatchUps.setTitle("Type Matchups") ;
		//typeMatchUps.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
		typeMatchUps.setResizable(false);
		
		moveDescriptions = new ArrayList<>() ;
		
		moves = new JFrame() ;
		moves.add(moves()) ;
		moves.setLocation(400,100) ;
		moves.setSize(new Dimension(1000,1000));
		moves.setTitle("Moves");
		//moves.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
		
		if(aSpecies.getMovesNamesORAS().size()>0)
		{	
			moveDescriptionsORAS = new ArrayList<>() ;
		
			movesORAS = new JFrame() ;
			movesORAS.add(movesORAS()) ;
			movesORAS.setLocation(400,100) ;
			movesORAS.setSize(new Dimension(1000,1000));
			movesORAS.setTitle("ORAS Moves");
			//movesORAS.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
			
		}	
		
String imageFile = aPokedex.aBaseFolder+"speciesImagesAndMegas/" ;
		
		if(aSpecies.getPokédexNumber()<10)
		{
			imageFile = imageFile+"00"+aSpecies.getPokédexNumber()+".png" ;
		}
		else if(aSpecies.getPokédexNumber()<100)
		{
			imageFile = imageFile+"0"+aSpecies.getPokédexNumber()+".png" ;
		}
		else
		{
			imageFile = imageFile+aSpecies.getPokédexNumber()+".png" ;
		}
		
		try
		{
			Image test = ImageIO.read(new File(imageFile)) ;
			setIconImage(test) ;
		}
		catch(IOException e)
		{
			if(aDisplayer!=null)
			{
				try
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
					setIconImage(test) ;
				}
				catch(IOException f)
				{
					f.printStackTrace();
				}
			}
			
		}
		
		typeMatchUps.setIconImage(getIconImage()) ;
		moves.setIconImage(getIconImage());
		
		for(JFrame x : moveDescriptions)
		{
			x.setIconImage(getIconImage());
		}
		
		if(movesORAS!=null)
		{
			movesORAS.setIconImage(getIconImage()) ;
			for(JFrame x : moveDescriptionsORAS)
			{
				x.setIconImage(getIconImage());
			}
		}
		
//		moves.setResizable(false);
		
		if(aSpecies.hasMega())
		{
			mega1 = oneMegaDisplay() ;
			//mega1.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
			mega1.setTitle("Mega Evolution Information for "+aSpecies.getName());
			mega1.setResizable(false) ;
		}
		
		setLayout(new GridLayout(2,2)) ;
		
		JPanel topLeft = topLeftCorner() ;
		JPanel topRight = topRightCorner() ;
		JPanel bottomLeft = bottomLeftCorner() ;
		JPanel bottomRight = bottomRightCorner() ;
		
		//Fill topLeft
		
 
		
		add(topLeft) ;
		//Fill topRight
		
		add(topRight) ;
		//Fill bottomLeft
		
		add(bottomLeft) ;
		//Fill bottomRight
		
		add(bottomRight) ;
		
		pack() ;
		
		if(anSSW==null && fw == null && aHW == null)
		{
			setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		}
		else
		{
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
		}
		
		
		
		
		
		
		
		
		setLocationRelativeTo(null) ;
		setTitle("Pokédex Entry for "+aSpecies.getName()) ;
		setSize(700,700) ;
		setLocation(0,0) ;
		setResizable(false) ;
		
	}
	
	private JPanel topLeftCorner()
	{
		JPanel result =  new JPanel() ;
		result.setLayout(null);
		
		JLabel nameAndNum = new JLabel("Name: "+aSpecies.getName()+"                Dex Number: "+aSpecies.getPokédexNumber()) ; ;
		
		nameAndNum.setLocation(20,20) ;
		nameAndNum.setSize(300,20) ;
		result.add(nameAndNum) ;
		
		String imageFile = aPokedex.aBaseFolder+aDisplayer.speciesFolder ;
		
		if(aSpecies.getPokédexNumber()<10)
		{
			imageFile = imageFile+"00"+aSpecies.getPokédexNumber()+".png" ;
		}
		else if(aSpecies.getPokédexNumber()<100)
		{
			imageFile = imageFile+"0"+aSpecies.getPokédexNumber()+".png" ;
		}
		else
		{
			imageFile = imageFile+aSpecies.getPokédexNumber()+".png" ;
		}
		
		ImageIcon image = new ImageIcon(imageFile) ;
		
		
		JLabel imageLabel = new JLabel(image) ;
		imageLabel.setSize(image.getIconWidth(),image.getIconHeight());
		imageLabel.setBorder(BorderFactory.createEtchedBorder(ICONIFIED));
		imageLabel.setBackground(Color.red);
		
		imageLabel.setLocation(20, 40) ;
		imageLabel.setSize(240,240) ;
		result.add(imageLabel) ;
		
		JLabel types = new JLabel("Type 1/Type 2: "+aSpecies.getType1()+"/"+aSpecies.getType2()) ;
		
		types.setSize(200, 20);
		types.setLocation(20,280);
		result.add(types) ;
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return result ;
	}
	
	private JPanel topRightCorner()
	{
		JPanel result = new JPanel() ;
		result.setLayout(new BoxLayout(result,BoxLayout.Y_AXIS)) ;
		//result.setLayout(new FlowLayout());
		JPanel ftLine = new JPanel() ;
		ftLine.setLayout(new GridLayout(1,2));
		
		ftLine.add(new JLabel("Description")) ;
		
		JTextPane ft = new JTextPane() ;
		ft.setText(aSpecies.getFlavorText());
		ft.setEditable(false);
		ftLine.add(ft) ;
		result.add(ftLine) ;
		
		result.add(Box.createRigidArea(new Dimension(5,5))) ;
		
		JPanel abilityLine = new JPanel() ;
		abilityLine.setLayout(new GridLayout(1,4));
		
		abilityLine.add(new JLabel("Abilities")) ;
		
		JTextPane abilities = new JTextPane() ;
		if(aSpecies.getAbilities()[1] != null)
		{
			abilities.setText(AbilityWindow.upperCaseEachWord( aSpecies.getAbilities()[0].toString() )+"/\n"+AbilityWindow.upperCaseEachWord(  aSpecies.getAbilities()[1].toString() ) );
			
			EventQueue.invokeLater(new Runnable(){
				
				@Override
				public void run()
				{
					ability1 = new AbilityWindow(aSpecies.getAbilities()[0], getIconImage()) ;
					ability2 = new AbilityWindow(aSpecies.getAbilities()[1], getIconImage()) ;
				}
				
			});
			
			
		}
		else
		{
			abilities.setText(AbilityWindow.upperCaseEachWord( aSpecies.getAbilities()[0].toString() ) ) ;
			EventQueue.invokeLater(new Runnable(){
				
				@Override
				public void run()
				{
					ability1 = new AbilityWindow(aSpecies.getAbilities()[0], getIconImage()) ;
				}
				
			});
		}
		abilities.setEditable(false);
		
		abilities.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				ability1.setVisible(true);
				if(ability2 != null)
				{
					ability2.setVisible(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
			}
			
		});
		
		abilityLine.add(abilities) ;
		
		abilityLine.add(new JLabel("Hidden Ability")) ;
		JTextPane hiddenAbility = new JTextPane() ;
		if(aSpecies.getAbilities()[2] == null)
		{
			hiddenAbility.setText("NONE") ;
		}
		else
		{
			hiddenAbility.setText(AbilityWindow.upperCaseEachWord(aSpecies.getAbilities()[2].toString() ) );
			EventQueue.invokeLater(new Runnable(){
				
				@Override
				public void run()
				{
					hAbility = new AbilityWindow(aSpecies.getAbilities()[2], getIconImage()) ;
				}
				
			});
			
			hiddenAbility.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e)
				{
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e)
				{
					hAbility.setVisible(true);
					
				}
				
				@Override
				public void mouseExited(MouseEvent e)
				{
					
					
				}
				
				@Override
				public void mouseReleased(MouseEvent e)
				{
					
				}
				
				@Override
				public void mousePressed(MouseEvent e)
				{
					
				}
			});
			
		}
		
		hiddenAbility.setEditable(false) ;
		
		abilityLine.add(hiddenAbility) ;
		
		result.add(abilityLine) ;
		
		result.add(Box.createRigidArea(new Dimension(5,5))) ;
		
		JPanel statRect = new JPanel() ;
		//statRect.setLayout(new GridLayout(1,2));
		
		statRect.add(new JLabel("Stats")) ;
		
		int[] stats = aSpecies.getStats() ;
		
		JPanel statGrid = new JPanel() ;
		
		statGrid.setLayout(new GridLayout(2,7));
		
		statGrid.add(new JLabel("Total")) ;
		statGrid.add(new JLabel("HP")) ;
		statGrid.add(new JLabel("Atk")) ;
		statGrid.add(new JLabel("Def")) ;
		statGrid.add(new JLabel("SpA")) ;
		statGrid.add(new JLabel("SpD")) ;
		statGrid.add(new JLabel("Spe")) ;
		
		statGrid.add(new JLabel(""+stats[0])) ;
		statGrid.add(new JLabel(""+stats[1])) ;
		statGrid.add(new JLabel(""+stats[2])) ;
		statGrid.add(new JLabel(""+stats[3])) ;
		statGrid.add(new JLabel(""+stats[4])) ;
		statGrid.add(new JLabel(""+stats[5])) ;
		statGrid.add(new JLabel(""+stats[6])) ;
		
		statRect.add(statGrid) ;
		result.add(statRect) ;
		
		JPanel eggLine = new JPanel() ;
		eggLine.setLayout(new GridLayout(1,2));
		
		eggLine.add(new JLabel("Egg Groups")) ;
		
		JTextPane egg = new JTextPane() ;
		egg.setText(aSpecies.getEggGroups()[0]+"/"+aSpecies.getEggGroups()[1]);
		egg.setEditable(false);
		eggLine.add(egg) ;
		result.add(eggLine) ;
		
		result.add(Box.createRigidArea(new Dimension(5,5))) ;
		
		JPanel EVsLine = new JPanel() ;
		EVsLine.setLayout(new GridLayout(1,2));
		
		EVsLine.add(new JLabel("EVs")) ;
		
		JTextPane evs = new JTextPane() ;
		String evString = "" ;
		for(String stat : aSpecies.getEVs().keySet())
		{
			evString = evString+", "+aSpecies.getEVs().get(stat)+" "+stat ;
		}
		evs.setText(evString.replaceFirst(",", ""));
		evs.setEditable(false);
		EVsLine.add(evs) ;
		result.add(EVsLine) ;
		
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		return result ;
	}
	
	private JPanel bottomLeftCorner()
	{
		JPanel result = new JPanel() ;
		
		result.setLayout(new GridLayout(2,1));
		result.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton tmu = new JButton("Type Match Ups (Show)") ;
		tmu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tmu.getText().contains("Show"))
				{
					typeMatchUps.setVisible(true);
					tmu.setText("Type Match Ups (Hide)");
				}
				else
				{
					typeMatchUps.setVisible(false);
					tmu.setText("Type Match Ups (Show)");
				}
				
			}
		});
		
		typeMatchUps.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				tmu.setText("Type Match Ups (Show)");
				
			}
			
		});
		
		//TODO: Could give access to number of resistances and weaknesses.
		
		//tmu.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		//tmu.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		tmu.setSize(100,100);
		
		JPanel tmuAndMoves = new JPanel() ;
		tmuAndMoves.setLayout(new FlowLayout());
		
		tmuAndMoves.add(tmu) ;
		
		JButton toMoves = new JButton("Moves (Show)") ;
	
		toMoves.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(toMoves.getText().contains("Show"))
				{
					moves.setVisible(true);
					toMoves.setText("Moves (Hide)");
				}
				else
				{
					moves.setVisible(false);
					for(JFrame x : moveDescriptions)
					{
						x.setVisible(false) ;
					}
					toMoves.setText("Moves (Show)");
				}
				
			}
		});
		
		tmuAndMoves.add(toMoves) ;
		
		moves.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				toMoves.setText("Moves (Show)");
				for(JFrame x : moveDescriptions)
				{
					x.setVisible(false) ;
				}
			}
			
		});
		
		if(aSpecies.getMovesNamesORAS().size()>0)
		{	
		
			JButton toMovesORAS = new JButton("ORAS Moves (Show)") ;
			
			toMovesORAS.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(toMovesORAS.getText().contains("Show"))
					{
						movesORAS.setVisible(true);
						toMovesORAS.setText("ORAS Moves (Hide)");
					}
					else
					{
						movesORAS.setVisible(false);
						for(JFrame x : moveDescriptionsORAS)
						{
							x.setVisible(false) ;
						}
						toMovesORAS.setText("ORAS Moves (Show)");
					}
					
				}
			});
			
			tmuAndMoves.add(toMovesORAS) ;
		
			movesORAS.addWindowListener(new WindowAdapter(){
				
				@Override
				public void windowClosing(WindowEvent e)
				{
					toMovesORAS.setText("ORAS Moves (Show)");
					for(JFrame x : moveDescriptionsORAS)
					{
						x.setVisible(false) ;
					}
				}
				
			});
			
		}	
			
		if(aSpecies.hasMega())
		{
			JButton megaAccess = new JButton("Mega Evolution (Show)") ;
			
			if(((Mega) aSpecies).getNumberOfMegas() == 2)
			{
				megaAccess.setText("Mega Evolutions (Show)");
				//megaTMU2.setIconImage(mega1.getIconImage());
				//mega1.setIconImage(this.getIconImage());
			}
			
			//megaTMU1.setIconImage(mega1.getIconImage());
			
			megaAccess.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					
					if(megaAccess.getText().contains("Show"))
					{
						mega1.setVisible(true);
						
						if(((Mega) aSpecies).getNumberOfMegas() == 2)
						{
							megaAccess.setText("Mega Evolutions (Hide)");
						}
						else
						{
							megaAccess.setText("Mega Evolution (Hide)");
						}
					}
					else
					{
						mega1.setVisible(false);
						megaTMU1.setVisible(false) ;
						if(((Mega) aSpecies).getNumberOfMegas() == 2)
						{
							megaTMU2.setVisible(false);
							megaAccess.setText("Mega Evolutions (Show)");
						}
						else
						{	
							megaAccess.setText("Mega Evolution (Show)");
						}
					}
				}
				
			}) ;
			
			tmuAndMoves.add(megaAccess) ;
			
			mega1.addWindowListener(new WindowAdapter(){
				
				@Override
				public void windowClosing(WindowEvent e)
				{	
					megaAccess.setText("Mega Evolution (Show)");
					megaTMU1.setVisible(false);
					if(((Mega) aSpecies).getNumberOfMegas() == 2)
					{
						megaTMU2.setVisible(false);	
					}
				}
				
			}) ;
			
			
			
		}

		
		
		if(aSpecies.battleSwaps())
		{
			
			JButton swapperAccess = new JButton("Battle Forme (Show)") ;
			
			swap = swapperDisplay(swapperAccess) ;
			
			swapperAccess.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(swapperAccess.getText().contains("Show"))
					{
						swapperAccess.setText("Battle Forme (Hide)");
						swap.setVisible(true);
					}
					else
					{
						swapperAccess.setText("Battle Forme (Show)");
						swap.setVisible(false);
						for(JFrame aFrame : swapTMUs)
						{
							aFrame.setVisible(false);
						}
					}
				}
				
			});
			
			tmuAndMoves.add(swapperAccess) ;
		}
		
		if(aSpecies.hasFormes() && !aSpecies.battleSwaps())
		{
			JButton formeAccess = new JButton("Forme (Show)") ;
			
			forme = formeDisplay(formeAccess) ;
			
			formeAccess.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					
					if(formeAccess.getText().contains("Show")) 
					{
						formeAccess.setText("Forme (Hide)");
						forme.setVisible(true);
					}
					else
					{	
						formeAccess.setText("Forme (Show)");
						forme.setVisible(false);
					}
					
				}
				
			} ) ;
			
			tmuAndMoves.add(formeAccess) ;
		}
		
		result.add(tmuAndMoves) ;
		
		//result.add(typeMatchUps()) ;
		
		result.add(evolutionChain()) ;
		return result ;
	}
	
	private JPanel bottomRightCorner()
	{
		JPanel result = new JPanel() ;
		result.setLayout(new GridLayout(3,1));
		
		JPanel addToFavoritesP = new JPanel() ;
		JPanel returnToPokedexP = new JPanel() ;
		JPanel searchAgainP = new JPanel() ;
		
		addToFavoritesP.setLayout(new FlowLayout());
		returnToPokedexP.setLayout(new FlowLayout());
		searchAgainP.setLayout(new FlowLayout());
		
		JButton addToFavorites = new JButton("Add to Favorites") ;
		JButton returnToPokedex = new JButton("Return to Pokédex") ;
		JButton searchAgain = new JButton("Search for Another Pokémon") ;
		
		addToFavorites.setSize(150, 30);
		returnToPokedex.setSize(150, 30);
		searchAgain.setSize(150,30);
		
		if(fw != null)
		{
			searchAgain.setText("Return to Favorites");
		}
		if(aHW != null)
		{
			searchAgain.setText("Return to History");
		}
		
		/*
		addToFavorites.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		returnToPokedex.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		searchAgain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		*/
		
		addToFavoritesP.add(addToFavorites) ;
		returnToPokedexP.add(returnToPokedex) ;
		searchAgainP.add(searchAgain) ;
		
		searchAgain.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(aSpecies.hasMega())
				{
					mega1.setVisible(false);
					megaTMU1.setVisible(false);
					
					if(((Mega) aSpecies).getNumberOfMegas() == 2)
					{
						megaTMU2.setVisible(false);
					}
				}
				
				if(movesORAS != null)
				{
					movesORAS.setVisible(false) ;
					for(JFrame x : moveDescriptionsORAS)
					{
						x.setVisible(false);
					}
				}
				
				if(swap!=null)
				{
					swap.setVisible(false);
					
					for(JFrame aFrame : swapTMUs)
					{
						aFrame.setVisible(false);
					}
					
				}
				
				if(forme!=null)
				{
					forme.setVisible(false) ;
				}
				
				ability1.setVisible(false);
				
				if(ability2 != null)
				{
					ability2.setVisible(false);
				}
				
				if(hAbility != null)
				{
					hAbility.setVisible(false) ;
				}
				
				if(anSSW != null)
				{
					setVisible(false) ;
					typeMatchUps.setVisible(false);
					moves.setVisible(false);
					for(JFrame x : moveDescriptions)
					{
						x.setVisible(false);
					}
					anSSW.setVisible(true);
				}
				
				
				if(aHW != null)
				{
					setVisible(false) ;
					typeMatchUps.setVisible(false);
					moves.setVisible(false);
					for(JFrame x : moveDescriptions)
					{
						x.setVisible(false);
					}
					aHW.setVisible(true);
				}
				
				if(fw != null)
				{
					setVisible(false) ;
					typeMatchUps.setVisible(false);
					moves.setVisible(false);
					for(JFrame x : moveDescriptions)
					{
						x.setVisible(false);
					}
					fw.setVisible(true);
				}
				
				
				
			}
		});
		
		returnToPokedex.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(aSpecies.hasMega())
				{
					mega1.setVisible(false);
					megaTMU1.setVisible(false);
					
					if(((Mega) aSpecies).getNumberOfMegas() == 2)
					{
						megaTMU2.setVisible(false);
					}
					
				}
				
				if(movesORAS != null)
				{
					movesORAS.setVisible(false) ;
					for(JFrame x : moveDescriptionsORAS)
					{
						x.setVisible(false);
					}
				}
				
				if(swap!=null)
				{
					swap.setVisible(false);
					
					for(JFrame aFrame : swapTMUs)
					{
						aFrame.setVisible(false);
					}
					
				}
				
				if(forme!=null)
				{
					forme.setVisible(false) ;
				}
				
				ability1.setVisible(false);
				
				if(ability2 != null)
				{
					ability2.setVisible(false);
				}
				
				if(hAbility != null)
				{
					hAbility.setVisible(false) ;
				}
				
				if(aDisplayer != null)
				{
					setVisible(false) ;
					typeMatchUps.setVisible(false);
					moves.setVisible(false);
					for(JFrame x : moveDescriptions)
					{
						x.setVisible(false);
					}
					aDisplayer.setVisible(true);
				}
				
				
				
			}
		});
		
		addToFavorites.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(aDisplayer!=null)
				{
					if(!aDisplayer.favorites.contains( aSpecies.getName() ) )
					{
						aDisplayer.favorites.add(aSpecies.getName() ) ;
					}
				}
			}
			
		});
		
		
		
		if(fw == null && aHW == null)
		{	
			result.add(addToFavoritesP) ;
			result.add(searchAgainP) ;
		}
		else if(aHW !=null)
		{
			result.add(addToFavoritesP) ;
			result.add(searchAgainP) ;
		}
		else if( fw !=null)
		{
			//result.add(addToFavoritesP) ;
			result.add(searchAgainP) ;
		}
		
		
		result.add(returnToPokedexP) ;
		
		return result ;
	}

	private JFrame oneMegaDisplay()
	{
		JFrame result = new JFrame() ;
		
		
		if(aSpecies.getName().equals("Charizard") || aSpecies.getName().equals("Mewtwo"))
		{
			result.setLayout(new GridLayout(2,1)) ;
			
			JPanel m1 = megaDisplay(1) ;
			JPanel m2 = megaDisplay(2) ;
			
			result.add(m1) ;
			result.add(m2) ;
			
			result.setSize(600, 640);
			result.setIconImage(getIconImage()) ;
			
			
			return result ;
		}
		
		result.setLayout(null) ;
		
		Species toDisplay = ((Mega) aSpecies).getFirstMega() ;
		
		String imageFile = aPokedex.aBaseFolder+aDisplayer.speciesFolder ;
		
		if(aSpecies.getPokédexNumber()<10)
		{
			imageFile = imageFile+"00"+aSpecies.getPokédexNumber()+"_1.png" ;
		}
		else if(aSpecies.getPokédexNumber()<100)
		{
			imageFile = imageFile+"0"+aSpecies.getPokédexNumber()+"_1.png" ;
		}
		else
		{
			imageFile = imageFile+aSpecies.getPokédexNumber()+"_1.png" ;
		}
		
		try
		{
			Image test = ImageIO.read(new File(imageFile)) ;
			result.setIconImage(test) ;
		}
		catch(IOException e)
		{
			if(aDisplayer!=null)
			{
				try
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
					result.setIconImage(test) ;
				}
				catch(IOException f)
				{
					f.printStackTrace();
				}
			}
			
		}
		
		ImageIcon image = new ImageIcon(imageFile) ;
		
		JLabel megaIm = new JLabel(image) ;
		
		megaIm.setLocation(20, 40);
		megaIm.setSize(200,200);
		megaIm.setBackground(Color.RED);
		megaIm.setBorder(BorderFactory.createEtchedBorder(ICONIFIED));
		result.add(megaIm) ;
		
		JLabel name = new JLabel("Name: Mega "+aSpecies.getName()) ;
		
		if(aSpecies.getName().equals("Charizard") || aSpecies.getName().equals("Mewtwo")) 
		{
			name.setText(name.getText()+" X");
		}
		
		name.setLocation(20, 10);
		name.setSize(200, 20);
		result.add(name) ;
		
		JLabel types = new JLabel("Type 1/Type 2: "+toDisplay.getType1()+"/"+toDisplay.getType2()) ;
		
		types.setLocation(20,250);
		types.setSize(200,20);
		result.add(types) ;
		
		JLabel ability = new JLabel("Ability: "+toDisplay.getStringAbilities()[0]) ;
		
		ability.setSize(150,20);
		ability.setLocation(240, 40);
		result.add(ability) ;
		
		AbilityWindow megaAbility = new AbilityWindow(aPokedex.getAbilityFinder().bestGuess(toDisplay.getStringAbilities()[0]), result.getIconImage()) ;
		
		ability.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				
				EventQueue.invokeLater(new Runnable(){
					
					@Override
					public void run()
					{
						
						megaAbility.setVisible(true);
					}
					
				});
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
			}
			
		});
		
		JLabel statLabel = new JLabel("Stats: ") ;
		
		statLabel.setSize(100,20) ;
		statLabel.setLocation(240,80);
		result.add(statLabel) ;
		
		int[] stats = toDisplay.getStats() ;
		
		JPanel statGrid = new JPanel() ;
		
		statGrid.setLayout(new GridLayout(2,7));
		
		statGrid.add(new JLabel("Total")) ;
		statGrid.add(new JLabel("HP")) ;
		statGrid.add(new JLabel("Atk")) ;
		statGrid.add(new JLabel("Def")) ;
		statGrid.add(new JLabel("SpA")) ;
		statGrid.add(new JLabel("SpD")) ;
		statGrid.add(new JLabel("Spe")) ;
		
		statGrid.add(new JLabel(""+stats[0])) ;
		statGrid.add(new JLabel(""+stats[1])) ;
		statGrid.add(new JLabel(""+stats[2])) ;
		statGrid.add(new JLabel(""+stats[3])) ;
		statGrid.add(new JLabel(""+stats[4])) ;
		statGrid.add(new JLabel(""+stats[5])) ;
		statGrid.add(new JLabel(""+stats[6])) ;
		
		statGrid.setLocation(280, 70);
		statGrid.setSize(250, 50);
		result.add(statGrid) ;
		
		megaTMU1 = new JFrame() ;
		megaTMU1.add( typeMatchUps(toDisplay) ) ;
		megaTMU1.setSize(900, 100);
		//megaTMU1.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		megaTMU1.setIconImage(result.getIconImage());
		megaTMU1.setResizable(false);
		megaTMU1.setTitle("Type Matchups for Mega "+aSpecies.getName());
		
		JButton tmu = new JButton("Type Matchups (Show)") ;
		
		tmu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tmu.getText().contains("Show"))
				{
					tmu.setText("Type Matchups (Hide)") ;
					megaTMU1.setVisible(true);
				}
				else
				{
					tmu.setText("Type Matchups (Show)") ;
					megaTMU1.setVisible(false) ;
				}
			}
			
		});
		
		tmu.addAncestorListener(new AncestorListener(){
			
			@Override
			public void ancestorAdded(AncestorEvent e)
			{
				
			}
			
			@Override
			public void ancestorRemoved(AncestorEvent e)
			{
				tmu.setText("Type Matchups (Show)");
				megaAbility.setVisible(false);
			}
			
			@Override
			public void ancestorMoved(AncestorEvent e)
			{
				
			}
			
		});
		
		megaTMU1.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				tmu.setText("Type Matchups (Show)");
				
			}
				
		});
		
		result.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				tmu.setText("Type Matchups (Show)");
				megaAbility.setVisible(false);
			}
			
		});
		
		tmu.setSize(200,40);
		tmu.setLocation(300, 180) ;
		result.add(tmu) ;
		result.setSize(600, 320);
		
		return result ;
	}
	
	private JPanel megaDisplay(int num)
	{
		JPanel result = new JPanel() ;
		result.setLayout(null) ;
		
		Species toDisplay = null ;
		
		if(num == 2)
		{	
			toDisplay = ((Mega) aSpecies).getFirstMega() ;
		}
		else if(num == 1)
		{
			toDisplay = ((Mega) aSpecies).getSecondMega() ;
		}
		
		String imageFile = aPokedex.aBaseFolder+aDisplayer.speciesFolder ;
		
		if(aSpecies.getPokédexNumber()<10)
		{
			imageFile = imageFile+"00"+aSpecies.getPokédexNumber()+"_"+num+".png" ;
		}
		else if(aSpecies.getPokédexNumber()<100)
		{
			imageFile = imageFile+"0"+aSpecies.getPokédexNumber()+"_"+num+".png" ;
		}
		else
		{
			imageFile = imageFile+aSpecies.getPokédexNumber()+"_"+num+".png" ;
		}
		
		
		ImageIcon image = new ImageIcon(imageFile) ;
		
		JLabel megaIm = new JLabel(image) ;
		
		megaIm.setLocation(20, 40);
		megaIm.setSize(200,200);
		megaIm.setBackground(Color.RED);
		megaIm.setBorder(BorderFactory.createEtchedBorder(ICONIFIED));
		result.add(megaIm) ;
		
		
		
		JLabel name = new JLabel("Name: Mega "+aSpecies.getName()) ;
		
		if( aSpecies.getName().equals("Charizard")|| aSpecies.getName().equals("Mewtwo")) 
		{
			if(num ==1)
			{	
				name.setText(name.getText()+" X");
			}
			else if( num == 2 )
			{
				name.setText(name.getText()+" Y");
			}
		}
		
		name.setLocation(20, 10);
		name.setSize(200, 20);
		result.add(name) ;
		
		JLabel types = new JLabel("Type 1/Type 2: "+toDisplay.getType1()+"/"+toDisplay.getType2()) ;
		
		types.setLocation(20,250);
		types.setSize(200,20);
		result.add(types) ;
		
		JLabel ability = new JLabel("Ability: "+toDisplay.getStringAbilities()[0]) ;
		
		ability.setSize(150,20);
		ability.setLocation(240, 40);
		result.add(ability) ;
		
		AbilityWindow megaAbility = new AbilityWindow(aPokedex.getAbilityFinder().bestGuess(toDisplay.getStringAbilities()[0]), null) ;
		
		ability.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				
				EventQueue.invokeLater(new Runnable(){
					
					@Override
					public void run()
					{
						
						megaAbility.setVisible(true);
					}
					
				});
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
			}
			
		});
		
		JLabel statLabel = new JLabel("Stats: ") ;
		
		statLabel.setSize(100,20) ;
		statLabel.setLocation(240,80);
		result.add(statLabel) ;
		
		int[] stats = toDisplay.getStats() ;
		
		JPanel statGrid = new JPanel() ;
		
		statGrid.setLayout(new GridLayout(2,7));
		
		statGrid.add(new JLabel("Total")) ;
		statGrid.add(new JLabel("HP")) ;
		statGrid.add(new JLabel("Atk")) ;
		statGrid.add(new JLabel("Def")) ;
		statGrid.add(new JLabel("SpA")) ;
		statGrid.add(new JLabel("SpD")) ;
		statGrid.add(new JLabel("Spe")) ;
		
		statGrid.add(new JLabel(""+stats[0])) ;
		statGrid.add(new JLabel(""+stats[1])) ;
		statGrid.add(new JLabel(""+stats[2])) ;
		statGrid.add(new JLabel(""+stats[3])) ;
		statGrid.add(new JLabel(""+stats[4])) ;
		statGrid.add(new JLabel(""+stats[5])) ;
		statGrid.add(new JLabel(""+stats[6])) ;
		
		statGrid.setLocation(280, 70);
		statGrid.setSize(250, 50);
		result.add(statGrid) ;
		
		JFrame megaTMU = new JFrame() ;
		
		String tmuTitle = "Type Matchups for Mega "+aSpecies.getName() ;
		
		if(num == 1)
		{
			megaTMU1 = megaTMU ;
			tmuTitle = tmuTitle+" X" ;
		}
		else if (num == 2)
		{
			megaTMU2 = megaTMU ;
			tmuTitle = tmuTitle+" Y" ;
		}
		
		megaTMU.setTitle(tmuTitle);
		
		
		
		try
		{
			Image test = ImageIO.read(new File(imageFile)) ;
			megaTMU.setIconImage(test);
		}
		catch(IOException e)
		{
			if(aDisplayer!=null)
			{
				try
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
					megaTMU.setIconImage(test) ;
				}
				catch(IOException f)
				{
					f.printStackTrace();
				}
			}
			
		}
		
		try
		{
			Image test = ImageIO.read(new File(imageFile)) ;
			megaAbility.setIconImage(test);
		}
		catch(IOException e)
		{
			if(aDisplayer!=null)
			{
				try
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
					megaAbility.setIconImage(test) ;
				}
				catch(IOException f)
				{
					f.printStackTrace();
				}
			}
			
		}
		
		//megaTMU = new JFrame() ;
		megaTMU.add( typeMatchUps(toDisplay) ) ;
		megaTMU.setSize(900, 100);
		//megaTMU.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		megaTMU.setResizable(false);
		
		JButton tmu = new JButton("Type Matchups (Show)") ;
		
		tmu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tmu.getText().contains("Show"))
				{
					tmu.setText("Type Matchups (Hide)") ;
					megaTMU.setVisible(true);
				}
				else
				{
					tmu.setText("Type Matchups (Show)") ;
					megaTMU.setVisible(false) ;
				}
			}
			
		});
		
		tmu.addAncestorListener(new AncestorListener(){
			
			@Override
			public void ancestorAdded(AncestorEvent e)
			{
				
			}
			
			@Override
			public void ancestorRemoved(AncestorEvent e)
			{
				tmu.setText("Type Matchups (Show)");
				megaAbility.setVisible(false);
			}
			
			@Override
			public void ancestorMoved(AncestorEvent e)
			{
				
			}
			
		});
		
		megaTMU.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				tmu.setText("Type Matchups (Show)");
			}
			
		});
		
		
		try
		{
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		tmu.setSize(200,40);
		tmu.setLocation(300, 180) ;
		result.add(tmu) ;
		result.setSize(600, 320);
		
		
		return result ;
	}
	
	private JFrame swapperDisplay(JButton b)
	{
		JFrame result = new JFrame() ;
		
		BattleSwapper spec = (BattleSwapper) aSpecies  ;
		
		
		Species[] swaps = spec.getFormes().toArray(new Species[spec.getFormes().size()] ) ;
		
		result.setLayout(new GridLayout(swaps.length,1));
		
		swapTMUs = new JFrame[swaps.length] ;
		
		int height = 0 ;
		
		for(int i = 0 ; i<swaps.length; i++)
		{
			JPanel currentPanel = aSwapper(swaps[i],i+1) ;
			
			
			
			result.add( currentPanel );
			height+=currentPanel.getHeight() ;
		}
		
		result.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				b.setText("Battle Forme (Show)") ;
				for(JFrame aFrame : swapTMUs)
				{
					aFrame.setVisible(false);
				}
			}
			
		}); 
		
		result.setTitle("Battle Forme Information for "+aSpecies.getName());
		result.setSize(600,height+(swaps.length-1)*60);
		result.setIconImage(getIconImage());
		
		return result ;
	}
	
	private JPanel aSwapper(Species toDisplay, int i)
	{
		JPanel result = new JPanel() ;
		
		result.setLayout(null) ;
		
		
		String imageFile = aPokedex.aBaseFolder+aDisplayer.speciesFolder ;
		
		if(aSpecies.getPokédexNumber()<10)
		{
			imageFile = imageFile+"00"+aSpecies.getPokédexNumber()+"_"+i+".png" ;
		}
		else if(aSpecies.getPokédexNumber()<100)
		{
			imageFile = imageFile+"0"+aSpecies.getPokédexNumber()+"_"+i+".png" ;
		}
		else
		{
			imageFile = imageFile+aSpecies.getPokédexNumber()+"_"+i+".png" ;
		}
		
		
		ImageIcon image = new ImageIcon(imageFile) ;
		
		JLabel megaIm = new JLabel(image) ;
		
		megaIm.setLocation(20, 40);
		megaIm.setSize(200,200);
		megaIm.setBackground(Color.RED);
		megaIm.setBorder(BorderFactory.createEtchedBorder(ICONIFIED));
		result.add(megaIm) ;
		
		JLabel name = new JLabel("Name: "+toDisplay.getName()) ;
		
		name.setLocation(20, 10);
		name.setSize(200, 20);
		result.add(name) ;
		
		JLabel types = new JLabel("Type 1/Type 2: "+toDisplay.getType1()+"/"+toDisplay.getType2()) ;
		
		types.setLocation(240,40);
		types.setSize(200,20);
		result.add(types) ;
		
		/*
		
		JLabel ability = new JLabel("Ability: "+toDisplay.getStringAbilities()[0]) ;
		
		ability.setSize(150,20);
		ability.setLocation(240, 40);
		result.add(ability) ;
		
		*/
		
		JLabel statLabel = new JLabel("Stats: ") ;
		
		statLabel.setSize(100,20) ;
		statLabel.setLocation(240,80);
		result.add(statLabel) ;
		
		int[] stats = toDisplay.getStats() ;
		
		JPanel statGrid = new JPanel() ;
		
		statGrid.setLayout(new GridLayout(2,7));
		
		statGrid.add(new JLabel("Total")) ;
		statGrid.add(new JLabel("HP")) ;
		statGrid.add(new JLabel("Atk")) ;
		statGrid.add(new JLabel("Def")) ;
		statGrid.add(new JLabel("SpA")) ;
		statGrid.add(new JLabel("SpD")) ;
		statGrid.add(new JLabel("Spe")) ;
		
		statGrid.add(new JLabel(""+stats[0])) ;
		statGrid.add(new JLabel(""+stats[1])) ;
		statGrid.add(new JLabel(""+stats[2])) ;
		statGrid.add(new JLabel(""+stats[3])) ;
		statGrid.add(new JLabel(""+stats[4])) ;
		statGrid.add(new JLabel(""+stats[5])) ;
		statGrid.add(new JLabel(""+stats[6])) ;
		
		statGrid.setLocation(280, 70);
		statGrid.setSize(250, 50);
		result.add(statGrid) ;
		
		JFrame megaTMU = new JFrame() ;
		
		String tmuTitle = "Type Matchups for "+toDisplay.getName() ;
		
		megaTMU.setTitle(tmuTitle);
		
		swapTMUs[i-1] = megaTMU ;
		
		try
		{
			Image test = ImageIO.read(new File(imageFile)) ;
			megaTMU.setIconImage(test);
		}
		catch(IOException e)
		{
			if(aDisplayer!=null)
			{
				try
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
					megaTMU.setIconImage(test) ;
				}
				catch(IOException f)
				{
					f.printStackTrace();
				}
			}
			
		}
		
		//megaTMU = new JFrame() ;
		megaTMU.add( typeMatchUps(toDisplay) ) ;
		megaTMU.setSize(900, 100);
		//megaTMU.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		megaTMU.setResizable(false);
		
		
		JButton tmu = new JButton("Type Matchups (Show)") ;
		
		tmu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tmu.getText().contains("Show"))
				{
					tmu.setText("Type Matchups (Hide)") ;
					megaTMU.setVisible(true);
				}
				else
				{
					tmu.setText("Type Matchups (Show)") ;
					megaTMU.setVisible(false) ;
				}
			}
			
		});
		
		megaTMU.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				tmu.setText("Type Matchups (Show)");
			}
			
		});
		
		
		tmu.setSize(200,40);
		tmu.setLocation(300, 180) ;
		result.add(tmu) ;
		result.setSize(600, 320);
		
		result.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent e)
			{
				tmu.setText("Type Matchups (Show)") ;
			}
			
			@Override
			public void ancestorAdded(AncestorEvent e)
			{
				
			}
			
			@Override
			public void ancestorMoved(AncestorEvent e)
			{
				
			}
			
		});
		
		
		
		return result ;
	}
	
	private JFrame formeDisplay(JButton b)
	{
		JFrame result = new JFrame();
		
		MultiForme spec = (MultiForme) aSpecies;
		
		Species[] myFormes = spec.getFormes().toArray(new Species[spec.getFormes().size()]) ;
		
		if(aSpecies.getName().equals("Rotom"))
		{
			result.setLayout(new GridLayout(2,3));
		}
		else
		{
			result.setLayout(new GridLayout(myFormes.length,1));
		}
		formes = new JFrame[myFormes.length] ;
		
		int height = 0 ;
		
		for(int i = 0 ; i<myFormes.length; i++)
		{
			JPanel currentPanel = formeDisplay(myFormes[i],i+1) ;
			
			
			result.add( currentPanel );
			height+=currentPanel.getHeight() ;
		}
		
		result.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				b.setText("Forme (Show)");
			}
			
		});
		
		result.setSize(600, height+(myFormes.length-1)*60 );
		result.setTitle("Forme Information for "+aSpecies.getName()) ;
		result.setIconImage(getIconImage());
		
		return result ;
	}
	
	private JPanel formeDisplay(Species toDisplay,int num)
	{
		JPanel result = new JPanel() ;
		result.setLayout(null) ;
		
		String imageFile = aPokedex.aBaseFolder+aDisplayer.speciesFolder ;
		
		if(aSpecies.getPokédexNumber()<10)
		{
			imageFile = imageFile+"00"+aSpecies.getPokédexNumber()+"_"+num+".png" ;
		}
		else if(aSpecies.getPokédexNumber()<100)
		{
			imageFile = imageFile+"0"+aSpecies.getPokédexNumber()+"_"+num+".png" ;
		}
		else
		{
			imageFile = imageFile+aSpecies.getPokédexNumber()+"_"+num+".png" ;
		}
		
		
		ImageIcon image = new ImageIcon(imageFile) ;
		
		JLabel megaIm = new JLabel(image) ;
		
		megaIm.setLocation(20, 40);
		megaIm.setSize(200,200);
		megaIm.setBackground(Color.RED);
		megaIm.setBorder(BorderFactory.createEtchedBorder(ICONIFIED));
		result.add(megaIm) ;
		
		JLabel name = new JLabel("Name: "+toDisplay.getName()) ;
		
		name.setLocation(20, 10);
		name.setSize(200, 20);
		result.add(name) ;
		
		JLabel types = new JLabel("Type 1/Type 2: "+toDisplay.getType1()+"/"+toDisplay.getType2()) ;
		
		types.setLocation(20,250);
		types.setSize(200,20);
		result.add(types) ;
		
		JLabel ability = new JLabel("Ability: "+toDisplay.getStringAbilities()[0]) ;
		
		ability.setSize(150,20);
		ability.setLocation(240, 40);
		result.add(ability) ;
		
AbilityWindow megaAbility = new AbilityWindow(aPokedex.getAbilityFinder().bestGuess(toDisplay.getStringAbilities()[0]), null) ;
		
		ability.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				
				EventQueue.invokeLater(new Runnable(){
					
					@Override
					public void run()
					{
						
						megaAbility.setVisible(true);
					}
					
				});
				
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				
			}
			
		});
		
		JLabel statLabel = new JLabel("Stats: ") ;
		
		statLabel.setSize(100,20) ;
		statLabel.setLocation(240,80);
		result.add(statLabel) ;
		
		int[] stats = toDisplay.getStats() ;
		
		JPanel statGrid = new JPanel() ;
		
		statGrid.setLayout(new GridLayout(2,7));
		
		statGrid.add(new JLabel("Total")) ;
		statGrid.add(new JLabel("HP")) ;
		statGrid.add(new JLabel("Atk")) ;
		statGrid.add(new JLabel("Def")) ;
		statGrid.add(new JLabel("SpA")) ;
		statGrid.add(new JLabel("SpD")) ;
		statGrid.add(new JLabel("Spe")) ;
		
		statGrid.add(new JLabel(""+stats[0])) ;
		statGrid.add(new JLabel(""+stats[1])) ;
		statGrid.add(new JLabel(""+stats[2])) ;
		statGrid.add(new JLabel(""+stats[3])) ;
		statGrid.add(new JLabel(""+stats[4])) ;
		statGrid.add(new JLabel(""+stats[5])) ;
		statGrid.add(new JLabel(""+stats[6])) ;
		
		statGrid.setLocation(280, 70);
		statGrid.setSize(250, 50);
		result.add(statGrid) ;
		
		JFrame megaTMU = new JFrame() ;
		
		String tmuTitle = "Type Matchups for "+aSpecies.getName() ;
		
		megaTMU.setTitle(tmuTitle);
		
		try
		{
			Image test = ImageIO.read(new File(imageFile)) ;
			megaTMU.setIconImage(test);
			megaAbility.setIconImage(test);
		}
		catch(IOException e)
		{
			if(aDisplayer!=null)
			{
				try
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
					megaTMU.setIconImage(test) ;
					megaAbility.setIconImage(test);
				}
				catch(IOException f)
				{
					f.printStackTrace();
				}
			}
			
		}
		
		//megaTMU = new JFrame() ;
		megaTMU.add( typeMatchUps(toDisplay) ) ;
		megaTMU.setSize(900, 100);
		//megaTMU.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		megaTMU.setResizable(false);
		
		JButton tmu = new JButton("Type Matchups (Show)") ;
		
		tmu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(tmu.getText().contains("Show"))
				{
					tmu.setText("Type Matchups (Hide)") ;
					megaTMU.setVisible(true);
				}
				else
				{
					tmu.setText("Type Matchups (Show)") ;
					megaTMU.setVisible(false) ;
				}
			}
			
		});
		
		tmu.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorAdded(AncestorEvent e)
			{
				
			}
			
			@Override
			public void ancestorRemoved(AncestorEvent e)
			{
				megaTMU.setVisible(false) ;
				tmu.setText("Type Matchups (Show)");
				megaAbility.setVisible(false);
			}
			
			@Override
			public void ancestorMoved(AncestorEvent e)
			{
				
			}
			
		});
		
		tmu.setSize(200,40);
		tmu.setLocation(300, 180) ;
		result.add(tmu) ;
		
		megaTMU.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				tmu.setText("Type Matchups (Show)") ;
			}
			
		});
		
		result.setSize(550, 320);
		
		return result ;
	
	}	
		
	public void displayEvolutionChain(Species spec)
	{
		System.out.println("~Evolution Chain~") ;
		Species s = spec ;
		//System.out.println("disevolchiain") ;
		//System.out.println(s) ;
		aPokedex.sortByName();
		while(!s.getPreEvolutionName().equals("--"))
		{
			s = aPokedex.find(s.getPreEvolutionName(),false ) ;
		}
		
		if(!s.evolves())
		{
			System.out.println(spec.getName()+" belongs to no evolution chain.") ;
			return ;
		}
		
		Queue<Species> aQueue = new LinkedList<Species>() ;
		
		aQueue.add(s) ;
		System.out.println(s.getName()+". Pokédex Number: "+s.getPokédexNumber()+".") ;
		
		while(! aQueue.isEmpty())
		{
			s = aQueue.poll() ;
			
			for(Species pkmn: aPokedex.getEvolutionSpecies(s))
			{
				System.out.println(s.getName()+" evolves in "+pkmn.getName()+". Pokédex Number: "+pkmn.getPokédexNumber()+". Method: "+s.getEvolutionMethods().get(pkmn.getName())+".") ;
				aQueue.add(pkmn) ;
			}
		}
		
		//System.out.println(spec.getName()) ;
	}
	
	private JTextPane evolutionChain()
	{
		JTextPane result = new JTextPane() ;
		String text = "" ;
		
		
		//System.out.println("~Evolution Chain~") ;
		Species s = aSpecies ;
		//System.out.println("disevolchiain") ;
		//System.out.println(s) ;
		aPokedex.sortByName();
		while(!s.getPreEvolutionName().equals("--"))
		{
			s = aPokedex.find(s.getPreEvolutionName(),false ) ;
		}
		
		if(!s.evolves())
		{
			text = aSpecies.getName()+" belongs to no evolution chain. Pokédex Number: "+aSpecies.getPokédexNumber()+"." ;
			result.setText(text);
			result.setSelectionColor(Color.orange);
			result.setEditable(false);
			return result;
		}
		
		Queue<Species> aQueue = new LinkedList<Species>() ;
		
		aQueue.add(s) ;
		text = (s.getName()+". Pokédex Number: "+s.getPokédexNumber()+".") ;
		
		while(! aQueue.isEmpty())
		{
			s = aQueue.poll() ;
			
			/*for(String species : s.getEvolutions() )
			{
				
				Species now = aPokédex.find(species) ;
				System.out.println(s.getName()+" evolves in "+now.getName()+". Pokédex Number: "+now.getPokédexNumber()+".") ;
				aQueue.add(now) ;
			}*/
			
			for(Species pkmn: aPokedex.getEvolutionSpecies(s))
			{
				text = text+"\n"+(s.getName()+" evolves in "+pkmn.getName()+". Pokédex Number: "+pkmn.getPokédexNumber()+". Method: "+s.getEvolutionMethods().get(pkmn.getName())+".") ;
				aQueue.add(pkmn) ;
			}
		}
		
		result.setText(text);
		result.setSelectionColor(Color.orange);
		result.setEditable(false);
		return result;
	}
	
	private JPanel evolutionChainLabels()
	{
		JPanel result = new JPanel() ;
		result.setLayout(new GridLayout(9,1) );
		String text = "" ;
		
		
		//System.out.println("~Evolution Chain~") ;
		Species s = aSpecies ;
		//System.out.println("disevolchiain") ;
		//System.out.println(s) ;
		aPokedex.sortByName();
		while(!s.getPreEvolutionName().equals("--"))
		{
			s = aPokedex.find(s.getPreEvolutionName(),false ) ;
		}
		
		if(!s.evolves())
		{
			result.add(new JLabel(aSpecies.getName()+" belongs to no evolution chain. Pokédex Number: "+aSpecies.getPokédexNumber()+"." ) );
			
			//result.setSelectionColor(Color.orange);
			return result;
		}
		
		Queue<Species> aQueue = new LinkedList<Species>() ;
		
		aQueue.add(s) ;
		text = (s.getName()+". Pokédex Number: "+s.getPokédexNumber()+".") ;
		
		while(! aQueue.isEmpty())
		{
			s = aQueue.poll() ;
			
			/*for(String species : s.getEvolutions() )
			{
				
				Species now = aPokédex.find(species) ;
				System.out.println(s.getName()+" evolves in "+now.getName()+". Pokédex Number: "+now.getPokédexNumber()+".") ;
				aQueue.add(now) ;
			}*/
			
			for(Species pkmn: aPokedex.getEvolutionSpecies(s))
			{
				result.add(new JLabel(s.getName()+" evolves in "+pkmn.getName()+". Pokédex Number: "+pkmn.getPokédexNumber()+". Method: "+s.getEvolutionMethods().get(pkmn.getName())+".") ) ;
				aQueue.add(pkmn) ;
			}
		}
		
		//result.setText(text);
		//result.setSelectionColor(Color.orange);
		//result.setBackground(Color.blue);
		return result;
	}
	
	private JPanel typeMatchUps(Species s)
	{
		
		String result = "" ;
		JPanel out = new JPanel() ;
		out.setLayout(new GridLayout(1,18));
		
		
		TypeGraph tg = aPokedex.aTypeGraph ;
		
		for(pokedex.Type currentType : tg.getTypes())
		{
			JPanel now = new JPanel() ;
			now.setLayout(new GridLayout(2,1)) ;
			double damageMultiplier = s.damageMultiplierFrom(currentType) ;
			String currentName = currentType.getTypeName() ;
			
			result = result+currentName+": "+damageMultiplier+". " ;
			
			JLabel name = new JLabel(currentName) ;
			
			name.setForeground(currentType.getColor());
			//name.setBorder(BorderFactory.createLineBorder(currentType.getColor(), 3));
			
			now.add(name) ;
			now.add(new JLabel(""+damageMultiplier)) ;
			out.add(now) ;
		}
		
		//out.setBorder(BorderFactory.createEtchedBorder());
		
		return out ;
	}
	
	private JPanel moves()
	{
		
		
		JPanel result = new JPanel() ;
		ArrayList<Move> xyMoves = aSpecies.getMovesXY(aPokedex.getMoveBox()) ;
		Hashtable<String, Integer> xyStringPairs = aSpecies.getMoveNameLevelPairsXY() ;
		ArrayList<MoveLevelPair> xyPairs = new ArrayList<>() ;
		
		for(Move aMove : xyMoves)
		{
			MoveLevelPair mlp = new MoveLevelPair(aMove, xyStringPairs.get( aMove.getName() ) ) ;
			xyPairs.add(mlp) ;
		}
		
		xyPairs.sort(this);
		
		result.setLayout(new GridLayout(xyPairs.size()+1,1));
		
		JPanel headers = new JPanel() ;
		headers.setLayout(new GridLayout(1,9));
		
		headers.add(new JLabel("Level")) ;
		headers.add(new JLabel("Name")) ;
		headers.add(new JLabel("Type")) ;
		headers.add(new JLabel("Power")) ;
		headers.add(new JLabel("Category")) ;
		headers.add(new JLabel("Accuracy")) ;
		headers.add(new JLabel("PP")) ;
		headers.add(new JLabel("Description")) ;
		headers.add(new JLabel("Effect Rate")) ;
		
		result.add(headers);
		
		for(int i = 0; i < xyPairs.size(); i++)
		{
			Move currentMove = xyPairs.get(i).getMove() ;
			JPanel currentMoveLine = new JPanel() ;
			currentMoveLine.setLayout( new GridLayout(1,9) ) ;
			//Level, move name, type, power, category, accuracy, pp, description, effectRate
			currentMoveLine.add(new JLabel( xyPairs.get(i).getLevel()+"" ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getName() ) ) ;
			
			JLabel name = new JLabel( currentMove.getType().getTypeName() ) ;
			
			name.setForeground(currentMove.getType().getColor());
			
			currentMoveLine.add(name) ;
			
			currentMoveLine.add(new JLabel( currentMove.getPower()+"" ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getCategory() ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getAccuracy()+"" ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getPP()+"" ) ) ;
			
			JTextPane descriptionSquare = new JTextPane( ) ;
			//descriptionSquare.setSize(200,200);
			descriptionSquare.setText(currentMove.getDescription()) ;
			descriptionSquare.setEditable(false) ;
			
			
			JButton descButton = new JButton("Click to See") ;
			
		//	currentMoveLine.add(descriptionSquare ) ;
			currentMoveLine.add(descButton) ;
			
			JFrame descFrame = new JFrame() ;
			descFrame.setLayout(new GridLayout(1,1)) ;
			descFrame.setSize(230, 150);
			descFrame.setLocation(400, 300);
			descFrame.add(descriptionSquare) ;
			//descFrame.setIconImage(getIconImage());
			
descButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(descButton.getText().contains("See"))
					{
						descFrame.setVisible(true);
						descButton.setText("Click to Hide");
					}
					else
					{
						descFrame.setVisible(false);
						descButton.setText("Click to See");
					}
					
					
				}
			});

			descButton.addAncestorListener(new AncestorListener(){
				
				@Override
				public void ancestorAdded(AncestorEvent e)
				{
					
				}
				
				@Override
				public void ancestorRemoved(AncestorEvent e)
				{
					descButton.setText("Click to See");
				}
				
				@Override
				public void ancestorMoved(AncestorEvent e)
				{
					
				}
				
			});
			
			descFrame.addWindowListener(new WindowAdapter(){
				
				@Override
				public void windowClosing(WindowEvent e)
				{
					descButton.setText("Click to See");
				}
				
			});
			
			moveDescriptions.add(descFrame);
			
			currentMoveLine.add(new JLabel( currentMove.getEffectRate()+"" ) ) ;
			
			result.add(currentMoveLine) ;
			
			
		}
		
		return result ;
	}
	
	private JPanel movesORAS()
	{
		
		
		
		JPanel result = new JPanel() ;
		ArrayList<Move> xyMoves = aSpecies.getMovesORAS(aPokedex.getMoveBox()) ;
		
		
		Hashtable<String, Integer> xyStringPairs = aSpecies.getMoveNameLevelPairsORAS() ;
		ArrayList<MoveLevelPair> xyPairs = new ArrayList<>() ;
		
		for(Move aMove : xyMoves)
		{
			MoveLevelPair mlp = new MoveLevelPair(aMove, xyStringPairs.get( aMove.getName() ) ) ;
			xyPairs.add(mlp) ;
		}
		
		xyPairs.sort(this);
		
		result.setLayout(new GridLayout(xyPairs.size()+1,1));
		
		JPanel headers = new JPanel() ;
		headers.setLayout(new GridLayout(1,9));
		
		headers.add(new JLabel("Level")) ;
		headers.add(new JLabel("Name")) ;
		headers.add(new JLabel("Type")) ;
		headers.add(new JLabel("Power")) ;
		headers.add(new JLabel("Category")) ;
		headers.add(new JLabel("Accuracy")) ;
		headers.add(new JLabel("PP")) ;
		headers.add(new JLabel("Description")) ;
		headers.add(new JLabel("Effect Rate")) ;
		
		result.add(headers);
		
		for(int i = 0; i < xyPairs.size(); i++)
		{
			Move currentMove = xyPairs.get(i).getMove() ;
			JPanel currentMoveLine = new JPanel() ;
			currentMoveLine.setLayout( new GridLayout(1,9) ) ;
			//Level, move name, type, power, category, accuracy, pp, description, effectRate
			currentMoveLine.add(new JLabel( xyPairs.get(i).getLevel()+"" ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getName() ) ) ;
			
			JLabel name = new JLabel( currentMove.getType().getTypeName() ) ;
			
			name.setForeground(currentMove.getType().getColor());
			
			currentMoveLine.add(name) ;
			
			currentMoveLine.add(new JLabel( currentMove.getPower()+"" ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getCategory() ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getAccuracy()+"" ) ) ;
			currentMoveLine.add(new JLabel( currentMove.getPP()+"" ) ) ;
			JTextPane descriptionSquare = new JTextPane( ) ;
			//descriptionSquare.setSize(200,200);
			descriptionSquare.setText(currentMove.getDescription()) ;
			descriptionSquare.setEditable(false) ;
			
			
			JButton descButton = new JButton("Click to See") ;
			
		//	currentMoveLine.add(descriptionSquare ) ;
			currentMoveLine.add(descButton) ;
			
			JFrame descFrame = new JFrame() ;
			descFrame.setLayout(new GridLayout(1,1)) ;
			descFrame.setSize(230, 150);
			descFrame.setLocation(400, 300);
			descFrame.add(descriptionSquare) ;
			//descFrame.setIconImage(getIconImage());
			
			descButton.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(descButton.getText().contains("See"))
					{
						descFrame.setVisible(true);
						descButton.setText("Click to Hide");
					}
					else
					{
						descFrame.setVisible(false);
						descButton.setText("Click to See");
					}
					
					
				}
			});
			
descButton.addAncestorListener(new AncestorListener(){
				
				@Override
				public void ancestorAdded(AncestorEvent e)
				{
					
				}
				
				@Override
				public void ancestorRemoved(AncestorEvent e)
				{
					descButton.setText("Click to See");
				}
				
				@Override
				public void ancestorMoved(AncestorEvent e)
				{
					
				}
				
			});
			
			descFrame.addWindowListener(new WindowAdapter(){
				
				@Override
				public void windowClosing(WindowEvent e)
				{
					descButton.setText("Click to See");
				}
				
			});
			
			moveDescriptionsORAS.add(descFrame);
			
			currentMoveLine.add(new JLabel( currentMove.getEffectRate()+"" ) ) ;
			
			result.add(currentMoveLine) ;
			
			
			
		}
		
		return result ;
	}
	
	@Override
	public int compare(MoveLevelPair mlp1, MoveLevelPair mlp2)
	{
		return mlp1.getLevel() - mlp2.getLevel() ;
	}
	
	public void changeSpecies(Species spec)
	{
		//this.removeAll();
		
		aSpecies = spec ;
		initialize() ;
		//this.validate();
	}
	
}