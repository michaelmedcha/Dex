package pokedex_ui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import pokedex.Pokedex;
import pokedex.Species;

public class FavoritesWindow extends JFrame {

	protected Displayer aDisplayer ;
	private Pokedex aPokedex ;
	
	public FavoritesWindow(Pokedex px, Displayer d)
	{
		aDisplayer = d ;
		aPokedex = px ;
		initialize() ;
	}
	
	private void initialize()
	{
		setLayout(null) ;
		FavoritesWindow ial = this ;
		
		String[] names = toStringArray( aDisplayer.favorites.toArray() ) ;
		
		for(int i = 0 ; i<names.length; i++)
		{
			names[i] = i+":"+names[i] ;
		}
		
		JComboBox cb = new JComboBox(names) ;
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
					Species spec = aPokedex.getSpeciesFinder().bestGuess(selected.split(":")[1]) ;
					
					EventQueue.invokeLater(new Runnable(){
						
						@Override
						public void run(){
						
							if(aDisplayer!=null)
							{
								aDisplayer.history.add(spec.getName()) ;
							}
							
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
		add(atf) ;
		
		JLabel so = new JLabel("Swap Order :") ;
		so.setLocation(30, 90);
		so.setSize(100,20) ;
		add(so) ;
		
		JLabel rm = new JLabel("Remove :") ;
		rm.setLocation(30, 120);
		rm.setSize(100,20) ;
		add(rm) ;
		
		JTextPane messages = new JTextPane() ;
		messages.setLocation(30, 160);
		messages.setSize(220,150);
		messages.setText("Enter a Pokémon name to add it to your favorites.\nEnter the numbers on their left to swap 2 favorites.\n"
				+ "Enter the number on the left of a Pokémon to remove it from your favorites.");
		messages.setEditable(false);
		add(messages) ;
		
		JTextField atfTF = new JTextField(20) ;
		atfTF.setLocation(150, 60);
		atfTF.setSize(100, 20);
		add(atfTF) ;
		
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
						cb.addItem( (aDisplayer.favorites.size()-1) +":"+ added);
						messages.setText(added+ " was added to your favorites.");
					}
					else
					{
						messages.setText("The Pokémon is already in your favorites.") ;
					}
				
				}	
					
			}
			
		});
		
		JTextField s1 = new JTextField(20) ;
		s1.setLocation(150, 90);
		s1.setSize(100, 20);
		add(s1) ;
		
		JTextField s2 = new JTextField(20) ;
		s2.setLocation(280, 90);
		s2.setSize(100, 20);
		add(s2) ;
		
		s2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String numText2 = s2.getText() ;
				
				if(aDisplayer.favorites.size()<2)
				{
					messages.setText("There is nothing to swap.");
					return ;
				}
				
				if(!isIntegerParseable(numText2))
				{
					messages.setText(numText2+" is not a number. Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				int num2 = Integer.parseInt(numText2) ;
				
				if(num2<0 || num2>aDisplayer.favorites.size()-1)
				{
					messages.setText(num2+ " does not fall in the correct range."+" Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				
				String numText1 = s1.getText() ;
				
				if(!isIntegerParseable(numText1))
				{
					messages.setText(numText1+" is not a number. Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				int num1 = Integer.parseInt(numText1) ;
				
				if(num1<0 || num1>aDisplayer.favorites.size()-1)
				{
					messages.setText(num1+ " does not fall in the correct range."+" Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				String swap1 = aDisplayer.favorites.get(num1) ;
				String swap2 = aDisplayer.favorites.get(num2) ;
				swapFavorites(num1, num2) ;
				
				messages.setText(swap1+ " and "+swap2+" were swapped.\nThe changes will show the next time you access the favorites from the main menu.");
			}
			
		}) ;	
		
		s1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String numText2 = s2.getText() ;
				
				if(aDisplayer.favorites.size()<2)
				{
					messages.setText("There is nothing to swap.");
					return ;
				}
				
				if(!isIntegerParseable(numText2))
				{
					messages.setText(numText2+" is not a number. Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				int num2 = Integer.parseInt(numText2) ;
				
				if(num2<0 || num2>aDisplayer.favorites.size()-1)
				{
					messages.setText(num2+ " does not fall in the correct range."+" Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				
				String numText1 = s1.getText() ;
				
				if(!isIntegerParseable(numText1))
				{
					messages.setText(numText1+" is not a number. Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				int num1 = Integer.parseInt(numText1) ;
				
				if(num1<0 || num1>aDisplayer.favorites.size()-1)
				{
					messages.setText(num2+ " does not fall in the correct range."+" Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				String swap1 = aDisplayer.favorites.get(num1) ;
				String swap2 = aDisplayer.favorites.get(num2) ;
				swapFavorites(num1, num2) ;
				
				messages.setText(swap1+ " and "+swap2+" were swapped.\nThe changes will show the next time you access the favorites from the main menu.");
			}
			
		}) ;
		
		JTextField rTF = new JTextField(20) ;
		rTF.setLocation(150, 120);
		rTF.setSize(100, 20);
		
		rTF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String numText = rTF.getText() ;
				
				if(aDisplayer.favorites.size()==0)
				{
					messages.setText("There is nothing to remove.");
					return ;
				}
				
				if(!isIntegerParseable(numText))
				{
					messages.setText(numText+" is not a number. Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				int num = Integer.parseInt(numText) ;
				
				if(num<0 || num>aDisplayer.favorites.size()-1)
				{
					messages.setText(num+ " does not fall in the correct range."+" Please enter a number between 0 and "+(aDisplayer.favorites.size()-1)+".");
					return ;
				}
				
				String removed = aDisplayer.favorites.get(num) ;
				aDisplayer.favorites.remove(num) ;
				messages.setText(removed + " was removed from your favorites.\nChanges will show the next time you acces your favorites.");
				
				
			}
			
		});
		
		add(rTF) ;
		
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
		
		//pack() ;
		
		setTitle("Favorites") ;
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
	
	private String[] toStringArray(Object[] o)
	{
		String[] result = new String[o.length] ;
		
		for(int i = 0 ; i<o.length; i++)
		{
			result[i] = (String) o[i] ;
		}
		
		return result ;
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
	
	private void swapFavorites(int a, int b)
	{
		if(aDisplayer.favorites.size()<2)
		{
			return ;
		}
		
		String temp = aDisplayer.favorites.get(a) ;
		aDisplayer.favorites.set(a, aDisplayer.favorites.get(b))  ;
		aDisplayer.favorites.set(b, temp)  ;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run(){
				
				FavoritesWindow fw = new FavoritesWindow(null,null) ;
				fw.setVisible(true);
			}
		});
		
	}

}
