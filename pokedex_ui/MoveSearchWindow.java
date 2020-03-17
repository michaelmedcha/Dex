package pokedex_ui;

import java.awt.Color;
import java.awt.EventQueue;
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

import pokedex.MoveBox.MoveNotFoundException;
import pokedex.Pokedex;
import pokedex.Move;

public class MoveSearchWindow extends JFrame {

	private Pokedex aPokedex ;
	private MoveWindow aMoveWindow ;
	public Displayer aDisplayer;
	
	public MoveSearchWindow(Pokedex px, Displayer d)
	{
		aPokedex = px ;
		aDisplayer = d ;
		//aSpeciesWindow = new AbilityWindow(aPokedex, this) ;
		initialize() ;
	}
	
	private final void initialize()
	{
		//setLayout(new GridLayout()) ;
		int cornerVal = 10 ;
		int textfieldsAbs = 105 ;
		
		setLayout(null) ;
		JLabel sbName = new JLabel("Search Query :") ;
		//sbName.setLocation(10,10) ;
		sbName.setBounds(cornerVal, cornerVal, 120, 20);
		//sbName.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		add(sbName) ;
		
		
		JTextPane messages = new JTextPane() ;
		
		messages.setSize(240,180);
		messages.setLocation(cornerVal, 100);
		messages.setEditable(false);
		messages.setBorder(BorderFactory.createEtchedBorder(Color.blue, Color.gray));
		messages.setText("Messages will appear here.");
		add(messages);
		
		JTextField sbNameField = new JTextField(20) ;
		sbNameField.setSize(145, 20);
		sbNameField.setLocation(textfieldsAbs, cornerVal);
		//sbNameField.setText("Enter a Pokémon's name here.");
		
		MoveSearchWindow inActionListener = this ;
		
		sbNameField.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String query = sbNameField.getText() ;
				Move spec = null;
				try {
					spec = aPokedex.getMove(query);
				} catch (MoveNotFoundException e1) {
					// TODO Auto-generated catch block
					spec = null ;
				}
				
				if(spec == null)
				{
					ArrayList<Move> suggestions = aPokedex.getMoveFinder().guessesList(query, 9) ;
					
					String notFoundMessage = query+" could not be found.\nHere are some suggestions:" ;
					
					for(Move s : suggestions)
					{
						notFoundMessage = notFoundMessage+"\n"+s.getName() ;
					}
					
					messages.setText(notFoundMessage);
					
				}
				else
				{
					Move spec2 = spec ;
					//aSpeciesWindow.changeSpecies(spec);
					inActionListener.setVisible(false);
					EventQueue.invokeLater(new Runnable(){
						@Override
						public void run()
						{
							
							aMoveWindow = new MoveWindow(aPokedex, inActionListener, spec2) ;
							aMoveWindow.setVisible(true) ;
						}
					}) ;
					
				}
			}
		});
		
		add(sbNameField) ;
		
		
		//sbNumField.setText("Enter a Pokémon's Pokédex number here.");
		
		
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
		
		setTitle("Move Search") ;
	}
}
