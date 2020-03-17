package pokedex_ui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import pokedex.Pokedex;
import pokedex.Ability;

public class AbilityWindow extends JFrame {
	
	private Ability anAbility ;
	private AbilitySearchWindow anASW ;
	private Pokedex aPokedex ;
	
	public AbilityWindow(Ability ability)
	{
		anAbility = ability ;
		initialize() ;
	}
	
	public AbilityWindow(Ability ability, Image ii)
	{
		anAbility = ability ;
		setIconImage(ii);
		initialize() ;
	}
	
	public AbilityWindow(Pokedex pPokedex, AbilitySearchWindow asw, Ability ability)
	{
		anAbility = ability ;
		anASW = asw ;
		aPokedex = pPokedex ;
		initialize() ;
	}
	
	private void initialize()
	{
		GridLayout gridLayout = new GridLayout(5,1) ;
		
		gridLayout.setVgap(3);
		
		setLayout(gridLayout) ;
		
		JPanel p1 = new JPanel() ;
		JPanel p2 = new JPanel() ;
		JPanel p3 = new JPanel() ;
		JPanel p4 = new JPanel() ;
		JPanel p5 = new JPanel() ;
		
		p1.setLayout(new GridLayout(1,2));
		p2.setLayout(new GridLayout(1,2));
		p3.setLayout(new GridLayout(1,2));
		p4.setLayout(new GridLayout(1,2));
		
		p5.setLayout(new FlowLayout());
		
		JLabel nameL = new JLabel("Name") ;
		JLabel effectL = new JLabel("Effect") ;
		JLabel detailedEffectL = new JLabel("Detailed Effect") ;
		JLabel owEffectL = new JLabel("OverWorld Effect") ;
		
		p1.add(nameL) ;
		p2.add(effectL) ;
		p3.add(detailedEffectL); 
		p4.add(owEffectL) ;
		
		JTextPane name = new JTextPane() ;
		JTextPane effect = new JTextPane() ;
		JTextPane detailedEffect = new JTextPane() ;
		JTextPane owEffect = new JTextPane() ;
		
		name.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		effect.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		detailedEffect.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		owEffect.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
		name.setText(uppercaseFirst(anAbility.getName())) ;
		effect.setText(anAbility.getEffect()) ;
		detailedEffect.setText(anAbility.getDetailedEffect()) ;
		owEffect.setText(anAbility.getOverWorldEffect()) ;
		
		//name.setSize(10, 100);
		
		name.setEditable(false);
		effect.setEditable(false);
		detailedEffect.setEditable(false);
		owEffect.setEditable(false);
		
		p1.add(name) ;
		p2.add(effect) ;
		p3.add(detailedEffect) ;
		p4.add(owEffect) ;
		
		JButton searchAgain = new JButton("Search for Another Ability") ;
		JButton btmp = new JButton("Back to Main Dex") ;
		
		searchAgain.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(anASW != null)
				{
					setVisible(false) ;
					anASW.setVisible(true);
				}
			}
			
		});
		
		btmp.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(anASW != null)
				{
					setVisible(false) ;
					anASW.aDisplayer.setVisible(true);
				}
			}
			
		});
		
		JButton hide = new JButton("Hide") ;
		
		hide.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false) ;
			}
			
		});
		
		if(anASW != null)
		{
			p5.add(searchAgain) ;
			p5.add(btmp) ;
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE) ;
		}
		else
		{
			p5.add(hide) ;
			setDefaultCloseOperation(HIDE_ON_CLOSE) ;
		}
		
		
		
		
		add(p1) ;
		add(p2) ;
		add(p3) ;
		add(p4) ;
		add(p5) ;
		
		pack() ;
		
		
		setSize(600,600) ;
		setTitle(AbilityWindow.upperCaseEachWord(anAbility.getName())+" Ability") ;
		setResizable(false);
		
		
		if(anASW != null)
		{
			
			
			if(anASW.aDisplayer!=null)
			{
				try
			
				{
					Image test = ImageIO.read(new File(aPokedex.aBaseFolder+anASW.aDisplayer.getIconFile())) ;
					setIconImage(test) ;
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}	
			
		}
		
		
		
	}
	
	public static String uppercaseFirst(String s)
	{
		
		if(s.length()==1)
		{
			return s.toUpperCase() ;
		}
		
		String bigSuffix = s.substring(1, s.length()) ;
		String firstLetter = s.substring(0, 1).toUpperCase() ;
		
		return firstLetter+bigSuffix ;
 	}
	
	public static String upperCaseEachWord(String s)
	{
		String[] split = s.split(" ") ;
		String result = "" ;
		
		for(int i = 0 ; i<split.length; i++)
		{
			result = result+" "+uppercaseFirst(split[i])  ;
		}
		
		return result.trim() ;
	}
	
	
	
	
}
