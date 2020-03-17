package pokedex_ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import pokedex.Pokedex;
import pokedex.Species;

public class SpeciesSearchWindow extends JFrame{

	private Pokedex aPokedex ;
	private SpeciesWindow aSpeciesWindow ;
	public Displayer aDisplayer ;
	
	public SpeciesSearchWindow(Pokedex px, Displayer d)
	{
		aPokedex = px ;
		aDisplayer = d ;
		aSpeciesWindow = new SpeciesWindow(aPokedex, this) ;
		initialize() ;
	}
	
	private final void initialize()
	{
		//setLayout(new GridLayout()) ;
		int cornerVal = 10 ;
		int textfieldsAbs = 150 ;
		
		setLayout(null) ;
		JLabel sbName = new JLabel("Search by Name:") ;
		//sbName.setLocation(10,10) ;
		sbName.setBounds(cornerVal, cornerVal, 120, 20);
		//sbName.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		add(sbName) ;
		
		JLabel sbNum = new JLabel("Search by Number:") ;
		//sbNum.setLocation(10,50) ;
		sbNum.setBounds(cornerVal, 50, 120, 20);
		//sbNum.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		add(sbNum) ;
		
		JTextPane messages = new JTextPane() ;
		
		messages.setSize(240,180);
		messages.setLocation(cornerVal, 100);
		messages.setEditable(false);
		messages.setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.gray));
		messages.setText("Messages will appear here.");
		add(messages);
		
		JTextField sbNameField = new JTextField(20) ;
		sbNameField.setSize(100, 20);
		sbNameField.setLocation(textfieldsAbs, cornerVal);
		//sbNameField.setText("Enter a Pokémon's name here.");
		
		SpeciesSearchWindow inActionListener = this ;
		
		sbNameField.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String query = sbNameField.getText() ;
				Species spec = aPokedex.find(query, true) ;
				
				if(spec == null)
				{
					ArrayList<Species> suggestions = aPokedex.getSpeciesFinder().guessesList(query, 9) ;
					
					String notFoundMessage = query+" could not be found.\nHere are some suggestions:" ;
					
					for(Species s : suggestions)
					{
						notFoundMessage = notFoundMessage+"\n"+s.getName()+": "+s.getPokédexNumber() ;
					}
					
					messages.setText(notFoundMessage);
					
				}
				else
				{
					
					//aSpeciesWindow.changeSpecies(spec);
					inActionListener.setVisible(false);
					EventQueue.invokeLater(new Runnable(){
						@Override
						public void run()
						{
							if(aDisplayer!=null)
							{
								aDisplayer.history.add(spec.getName()) ;
							}
							aSpeciesWindow = new SpeciesWindow(aPokedex, inActionListener, spec) ;
							aSpeciesWindow.setVisible(true) ;
						}
					}) ;
					
				}
			}
		});
		
		add(sbNameField) ;
		
		JTextField sbNumField = new JTextField(20) ;
		sbNumField.setSize(100, 20);
		sbNumField.setLocation(textfieldsAbs, 50);
		
		sbNumField.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String query = sbNumField.getText() ;
				
				if(isIntegerParseable(query))
				{
					int num = Integer.parseInt(query) ;
					if(num>0 && num <=721)
					{
						Species spec = aPokedex.findByNumber(num, true);
						inActionListener.setVisible(false);
						EventQueue.invokeLater(new Runnable(){
							@Override
							public void run()
							{
								if(aDisplayer!=null)
								{
									aDisplayer.history.add(spec.getName()) ;
								}
								
								aSpeciesWindow = new SpeciesWindow(aPokedex, inActionListener, spec) ;
								aSpeciesWindow.setVisible(true) ;
							}
						}) ;
					}
					else
					{
						messages.setText(num+" is inadequate. The input should be an integer in the range 1 to 721.");
					}
				}
				else
				{
					messages.setText(query+" is not a number. The input should be an integer in the range 1 to 721.") ;
				}
				
			}
		}) ;
		
		//sbNumField.setText("Enter a Pokémon's Pokédex number here.");
		
		add(sbNumField) ;
		
		JButton backToMainDex = new JButton("Main Dex") ;
		backToMainDex.setSize(90,54);
		backToMainDex.setLocation(375,290);
		
		backToMainDex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(aDisplayer!=null)
				{
					inActionListener.setVisible(false);
					aDisplayer.setVisible(true);
				}
			}
		});
		
		add(backToMainDex);
		//pack() ;
		
		setSize(500,400) ;
		
		if(aDisplayer == null)
		{
			setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		}
		else
		{
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
			
			try
			{
				Image test = ImageIO.read(new File(aPokedex.aBaseFolder+aDisplayer.getIconFile())) ;
				setIconImage(test) ;
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
		setTitle("Search") ;
		
		
	}
	
	private boolean isIntegerParseable(String s)
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
	
	public static void main(String[] args)
	{
		String baseFolder = "C:/Users/marie/Desktop/Data for Pokédex/Data_for_v2/" ;
		long start = System.currentTimeMillis() ;
		Pokedex px = new Pokedex(baseFolder,baseFolder+"all_moves.txt",baseFolder+"all_abilities.csv", baseFolder+"Type Matchup.csv",baseFolder+"PokéDataToText.txt", baseFolder+"Mega File.csv", baseFolder+"Formes2.csv", baseFolder+"BattleSwappers.csv", baseFolder+"flavor_texts_shield.txt", baseFolder+"evolution_method_and_egg_groups_shield.txt", baseFolder+"egg_group_evol_EVs_shield.txt") ;
		long end = System.currentTimeMillis() ;
		
		System.out.println(((double)(end-start))/1000.0) ;
	
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				
				SpeciesSearchWindow ssw = new SpeciesSearchWindow(px, null) ;
				ssw.setVisible(true);
				
			}
		});
		
	}
	
}
