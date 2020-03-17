package pokedex_ui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import pokedex.Pokedex;
import pokedex.Species;

public class HistoryWindow extends JFrame {

	protected Displayer aDisplayer ;
	private Pokedex aPokedex ;
	
	public HistoryWindow(Pokedex px, Displayer d)
	{
		aDisplayer = d ;
		aPokedex = px ;
		initialize() ;
	}
	
	private String[] reverse(Object[] s)
	{
		String[] result = new String[s.length] ;
		
		for(int i = 0 ; i<s.length; i++)
		{
			result[i] = (String) s[s.length-1-i] ;
		}
		
		return result ;
	}
	
	private void initialize()
	{
		setLayout(null) ;
		HistoryWindow ial = this ;
		
		String[] names = {"Rayquaza", "Feraligatr", "Dragonite"} ;
		
		JComboBox cb = new JComboBox(reverse( aDisplayer.history.toArray() ) ) ;
		cb.setLocation(30, 30);
		cb.setSize(95, 20);
		
		
		
		cb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JComboBox favBox = (JComboBox) e.getSource() ;
				String selected = (String) favBox.getSelectedItem() ;
				//System.out.println(selected) ;
				
				if(aPokedex != null)
				{
					Species spec = aPokedex.getSpeciesFinder().bestGuess(selected) ;
					
					EventQueue.invokeLater(new Runnable(){
						
						@Override
						public void run(){
						
							SpeciesWindow sw = new SpeciesWindow(aPokedex, ial, spec) ;
							ial.setVisible(false) ;			
							sw.setVisible(true) ;
						}
						
					});
					
				}
				
			}
			
			class myFavoriteChoice extends JFrame {
				
			}
			
		});
		
		
		
		add(cb) ;
		
		JLabel atf = new JLabel("Add to Favorites :") ;
		atf.setLocation(30, 60);
		atf.setSize(100,20) ;
		//add(atf) ;
		
		JLabel so = new JLabel("Swap Order :") ;
		so.setLocation(30, 90);
		so.setSize(100,20) ;
		//add(so) ;
		
		JTextPane messages = new JTextPane() ;
		messages.setLocation(30, 125);
		messages.setSize(220,210);
		messages.setEditable(false);
		add(messages) ;
		
		JTextField atfTF = new JTextField(20) ;
		atfTF.setLocation(150, 60);
		atfTF.setSize(100, 20);
		//add(atfTF) ;
		
		/*
		atfTF.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(aDisplayer != null)
				{	
					String toAdd = atfTF.getText() ;
				
					String added = aDisplayer.addToFavorites(toAdd) ;
					
					if( added != null )
					{
						cb.addItem(added);
						messages.setText(added+ " was added to your favorites.");
					}
					else
					{
						messages.setText("The Pokémon is already in your favorites.") ;
					}
				
				}	
					
			}
			
		});*/
		
		JTextField s1 = new JTextField(20) ;
		s1.setLocation(150, 90);
		s1.setSize(100, 20);
		//add(s1) ;
		
		JTextField s2 = new JTextField(20) ;
		s2.setLocation(280, 90);
		s2.setSize(100, 20);
		//add(s2) ;
		
		
		
		JButton btmd = new JButton("Back to Main Dex") ;
		btmd.setLocation(315,285);
		btmd.setSize(135, 50);
		
		
		
		btmd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(aDisplayer != null)
				{
					ial.setVisible(false);
					aDisplayer.setVisible(true);
				}
			}
		}) ;
		
		add(btmd) ;
		
		JButton ch = new JButton("Clear History") ;
		
		ch.setLocation(315,210);
		ch.setSize(135, 50);
		
		ch.addActionListener(new ActionListener(){
			
			  @Override
			  public void actionPerformed(ActionEvent e)
			  {
				  if(aDisplayer != null)
				  {
						
					  EventQueue.invokeLater(new Runnable(){
						  
						  @Override
						  public void run()
						  {
							  
							  aDisplayer.history = new ArrayList<>() ;
							  messages.setText("Your history will have been cleared the next time you access it (Got anything to hide?).");
						  }
						  
					  });
					  	
				  }  
			  }
			
		});
		
		add(ch) ;
		//pack() ;
		
		setTitle("History") ;
		setSize(500,400) ;
		setLocationRelativeTo(null) ;
		
		if(aDisplayer!=null)
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
		else
		{
			setDefaultCloseOperation(EXIT_ON_CLOSE) ;
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run(){
				
				HistoryWindow fw = new HistoryWindow(null,null) ;
				fw.setVisible(true);
			}
		});
		
	}

}
